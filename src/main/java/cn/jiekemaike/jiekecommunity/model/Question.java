package cn.jiekemaike.jiekecommunity.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String account_id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;

}
