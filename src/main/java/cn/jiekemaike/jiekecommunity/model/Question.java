package cn.jiekemaike.jiekecommunity.model;

import lombok.Data;

@Data
public class Question {
    private Long id;
    private String accountId;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
}
