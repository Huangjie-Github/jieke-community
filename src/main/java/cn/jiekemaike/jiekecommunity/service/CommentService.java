package cn.jiekemaike.jiekecommunity.service;

import cn.jiekemaike.jiekecommunity.dto.CommentDTO;
import cn.jiekemaike.jiekecommunity.enums.CommentTypeEnum;
import cn.jiekemaike.jiekecommunity.enums.NotificationStatusEnum;
import cn.jiekemaike.jiekecommunity.enums.NotificationTypeEnum;
import cn.jiekemaike.jiekecommunity.exception.CustomizeErrorCode;
import cn.jiekemaike.jiekecommunity.exception.CustomizeException;
import cn.jiekemaike.jiekecommunity.mapper.*;
import cn.jiekemaike.jiekecommunity.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(@RequestBody Comment comment,User user) {
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WROHG);
        }

        if (comment.getType() == CommentTypeEnum.QUESTION.getType()){
            //回复问题
            Question question = questionMapper.selectById(comment.getParentId());
            if (question==null)
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            commentMapper.insert(comment);
            questionMapper.updateCommentCount(comment.getParentId());
            //创建通知
            createNotify(comment,question.getCreator(),user.getName(),question.getTitle(),NotificationTypeEnum.REPLY_QUESTION.getType(),question.getId());
        }else {
            //回复评论
            Comment commentdb = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (commentdb ==null)
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);

            Question question = questionMapper.selectById(commentdb.getParentId());
            if (question==null)
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);

            commentMapper.insert(comment);
            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
            //创建通知
            createNotify(comment, commentdb.getCommentator(),user.getName(),question.getTitle(),NotificationTypeEnum.REPLY_COMMENT.getType(),question.getId());
        }
    }

    private void createNotify(@RequestBody Comment comment, Long receiver, String notifierName, String outerTitle, int type, Long outerId) {
        Notification notification= new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(type);
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    @Transactional
    public List<CommentDTO> listByQuestionId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size()==0) return new ArrayList<>();
//      获取评论人以及ID
        Set<Long> collect = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(new ArrayList<>(collect));
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
//      获取评论的DTO对象 转化：comment -> commentDTO对象
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
