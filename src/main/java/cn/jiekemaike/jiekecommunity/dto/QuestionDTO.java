package cn.jiekemaike.jiekecommunity.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;//自增长
    private String title;//标题
    private String description;//内容
    private Long gmt_create;//创建时间
    private Long gmt_modified;//
    private Integer creator;//用户登陆自增长id
    private Integer comment_count;//评论人数
    private Integer view_count;//阅读人数
    private Integer like_count;//喜欢人数
    private String tag;//标签
    private String avatar_url;//头像
    private String name;//用户名称
    private String account_id;//用户的唯一id
}
