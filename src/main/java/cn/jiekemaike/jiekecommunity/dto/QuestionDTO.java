package cn.jiekemaike.jiekecommunity.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;//自增长
    private String title;//标题
    private String description;//内容
    private Long gmtCreate;//创建时间
    private Long gmtModified;//
    private Integer creator;//用户登陆自增长id
    private Integer commentCount;//评论人数
    private Integer viewCount;//阅读人数
    private Integer likeCount;//喜欢人数
    private String tag;//标签
    private String avatarUrl;//头像
    private String name;//用户名称
    private String accountId;//用户的唯一id
}
