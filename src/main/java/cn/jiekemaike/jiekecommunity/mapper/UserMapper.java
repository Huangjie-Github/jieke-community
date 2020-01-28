package cn.jiekemaike.jiekecommunity.mapper;

import cn.jiekemaike.jiekecommunity.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Repository  存储层
 *      此处作用：解决@Mapper之后虽然可以使用，但是@Autowired无法识别会爆红，加上这个标签就可以识别了
 */
@Repository
@Mapper
public interface UserMapper {

    @Insert("insert into User(name,account_id,token,gmt_create,gmt_modified,avatar_url) value(#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{avatar_url})")
    void insert(User user);

    @Select("select * from User where token = #{token}")
    User findByToken(@Param("token")String token);
}
