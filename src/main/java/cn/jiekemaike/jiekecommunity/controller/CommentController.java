package cn.jiekemaike.jiekecommunity.controller;

import cn.jiekemaike.jiekecommunity.dto.CommentCreateDTO;
import cn.jiekemaike.jiekecommunity.dto.CommentDTO;
import cn.jiekemaike.jiekecommunity.dto.ResultDTO;
import cn.jiekemaike.jiekecommunity.enums.CommentTypeEnum;
import cn.jiekemaike.jiekecommunity.exception.CustomizeErrorCode;
import cn.jiekemaike.jiekecommunity.model.Comment;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){

        if (StringUtils.isBlank(commentCreateDTO.getContent())||commentCreateDTO==null)
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_CONTENT_NOT_NULL);

        User user = (User) request.getSession().getAttribute("user");
        if (user==null)
            return ResultDTO.errorOf(CustomizeErrorCode.WEI_DENG_LU);

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }


    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id")Long id){
        List<CommentDTO> commentDTOS = commentService.listByQuestionId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
