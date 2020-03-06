package cn.jiekemaike.jiekecommunity.service;

import cn.jiekemaike.jiekecommunity.dto.NotificationDTO;
import cn.jiekemaike.jiekecommunity.dto.PaginationDTO;
import cn.jiekemaike.jiekecommunity.enums.NotificationStatusEnum;
import cn.jiekemaike.jiekecommunity.enums.NotificationTypeEnum;
import cn.jiekemaike.jiekecommunity.exception.CustomizeErrorCode;
import cn.jiekemaike.jiekecommunity.exception.CustomizeException;
import cn.jiekemaike.jiekecommunity.mapper.NotificationExtMapper;
import cn.jiekemaike.jiekecommunity.mapper.NotificationMapper;
import cn.jiekemaike.jiekecommunity.mapper.UserMapper;
import cn.jiekemaike.jiekecommunity.model.Notification;
import cn.jiekemaike.jiekecommunity.model.NotificationExample;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Value("${index.problem.pageButtonSize}")
    private Integer pageButtonSize;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private NotificationExtMapper notificationExtMapper;


    private void pageLabel(PaginationDTO<NotificationDTO> paginationDTO, Integer pageSize, Integer page) {
        if (page < 1)
            page = 1;
        if (page > pageSize)
            page = pageSize;
        if (pageSize<1)
            pageSize=1;

        if (pageSize <= pageButtonSize) {//总页面数不足规定数目，显示全部的
            for (int i = 1; i <= pageSize; i++) {
                paginationDTO.getPageSize().add(i);
            }
        } else if (page <= Math.ceil(pageButtonSize / 2d)) {//显示开始的页面按钮的长度
            for (int i = 1; i <= 5; i++) {
                paginationDTO.getPageSize().add(i);
            }
        } else if (page >= pageSize - (pageButtonSize / 2)) {//显示最后的页面按钮组
            for (int i = pageSize - pageButtonSize + 1; i <= pageSize; i++) {
                paginationDTO.getPageSize().add(i);
            }
        } else if (page < pageSize - (pageButtonSize / 2)) {//显示中间部位的按钮
            for (int i = page - (pageButtonSize / 2); i <= page + (pageButtonSize / 2); i++) {
                paginationDTO.getPageSize().add(i);
            }
        }
        if (pageSize==1){
            paginationDTO.setShowPrevious(false);//上一行
            paginationDTO.setShowNext(false);
            paginationDTO.setShowFirstPage(false);
            paginationDTO.setShowEndPage(false);
        }else if (page == 1) {
            paginationDTO.setShowPrevious(false);//上一行
            paginationDTO.setShowNext(true);//下一行
        } else if (page > 1 && page < pageSize) {
            paginationDTO.setShowPrevious(true);
            paginationDTO.setShowNext(true);
        } else {
            paginationDTO.setShowPrevious(true);
            paginationDTO.setShowNext(false);
        }
//        是否显示返回首页
        if (paginationDTO.getPageSize().contains(1)) {
            paginationDTO.setShowFirstPage(false);
        } else {
            paginationDTO.setShowFirstPage(true);
        }
//        是否显示返回首页尾页
        if (paginationDTO.getPageSize().contains(pageSize)) {
            paginationDTO.setShowEndPage(false);
        } else {
            paginationDTO.setShowEndPage(true);
        }
    }

    @Transactional
    public PaginationDTO<NotificationDTO> proFilePage(Integer page, Integer size, Long userId) {
        //需要提供当前页数，总页数，以及，当下页显示的item内容
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExample(notificationExample);
        Integer listSize = notifications.size();//总条数

        Integer pageSize = (int) Math.ceil(listSize / (double) size);//总页数
        paginationDTO.setPage(page);
        paginationDTO.setPages(pageSize);
        List<Notification> notificationsPage = notificationExtMapper.proFileListPage(userId, (page - 1) * size, size);

        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notificationsPage) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setDate(notificationDTOS);
        pageLabel(paginationDTO,pageSize,page);
        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        long unreadCount = notificationMapper.countByExample(notificationExample);
        return unreadCount;
    }
    @Transactional
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);

        if (notification==null)
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        if (!Objects.equals(notification.getReceiver(),user.getId()))
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
