package cn.jiekemaike.jiekecommunity.mapper;

import cn.jiekemaike.jiekecommunity.dto.QuestionDTO;
import cn.jiekemaike.jiekecommunity.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag,account_id) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag},#{accountId})")
    void create(Question question);

    @Select("select count(1) from question")
    Integer listSize();
    @Select("select * from question left join user on question.account_id=user.account_id limit #{page},#{size}")
    ArrayList<QuestionDTO> listPage(@Param(value = "page") Integer page, @Param(value = "size") Integer size);


    @Select("select count(*) from user right join question on question.account_id=user.account_id where user.id = #{id}")
    Integer proFileListPageSize(@Param(value = "id")Long id);
    @Select("select * from user right join question on question.account_id=user.account_id where user.id = #{id} limit #{page},#{size}")
    ArrayList<QuestionDTO> proFileListPage(@Param(value = "page") Integer page, @Param(value = "size") Integer size,@Param(value = "id")Long id);

    @Select("select * from question left join user on question.account_id=user.account_id where question.id=#{id}")
    QuestionDTO findById(@Param(value ="id") Integer id);

    @Update("update Question set title=#{question.title},description=#{question.description},gmt_modified=#{question.gmtModified},tag=#{question.tag} where id=#{question.id}")
    Boolean updateQuestion(@Param(value = "question") Question question);

    @Update("update Question set view_count = view_count+1 where id = #{id}")
    Boolean updateView(@Param("id") Integer id);

    @Select("select * from question where id = #{id}")
    Question selectById(@Param("id") Long id);

    @Update("update question set comment_count = comment_count+1 where id = #{id}")
    Boolean updateCommentCount(@Param("id")Long id);

}
