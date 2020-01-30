package cn.jiekemaike.jiekecommunity.mapper;

import cn.jiekemaike.jiekecommunity.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @Repository  存储层
 *      此处作用：解决@Mapper之后虽然可以使用，但是@Autowired无法识别会爆红，加上这个标签就可以识别了
 */
@Repository
@Mapper
public interface UserMapper {

    @Insert("insert into User(name,account_id,token,gmt_create,gmt_modified,avatar_url) value(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from User where token = #{token}")
    User findByToken(@Param("token")String token);

    @Select("select * from User where account_id = #{accountId}")
    User findByAccountId(@Param("accountId")Long accountId);

    @Update("update User set name=#{user.name},token=#{user.token},avatar_url=#{user.avatarUrl} where accountId=#{user.accountId}")
    Boolean updateUSer(@Param("user") User user);
}
