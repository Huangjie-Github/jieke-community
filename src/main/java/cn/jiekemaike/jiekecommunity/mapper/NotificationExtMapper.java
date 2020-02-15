package cn.jiekemaike.jiekecommunity.mapper;

import cn.jiekemaike.jiekecommunity.dto.NotificationDTO;
import cn.jiekemaike.jiekecommunity.model.Notification;
import cn.jiekemaike.jiekecommunity.model.NotificationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationExtMapper {
    List<Notification> proFileListPage(Long id, Integer offSet, Integer size);
}