package cn.jiekemaike.jiekecommunity.mapper;

import cn.jiekemaike.jiekecommunity.model.Comment;
import cn.jiekemaike.jiekecommunity.model.CommentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}