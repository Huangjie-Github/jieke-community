package cn.jiekemaike.jiekecommunity.dto;

import cn.jiekemaike.jiekecommunity.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;//创建时间
    private Integer status;//处理状态
    private Long notifier;//发起通知用户的ID
    private String notifierName;//发起通知用户的名称
    private String outerTitle;//回复的标题
    private String typeName;//回复类型的文字描述
    private Long outerid;//回复问题或者评论的ID
    private Integer type;//回复类型

}
