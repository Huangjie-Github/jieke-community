package cn.jiekemaike.jiekecommunity.dto;

import cn.jiekemaike.jiekecommunity.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private User notifier;
    private String outerTitle;
    private String type;

}
