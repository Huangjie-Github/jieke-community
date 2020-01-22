package cn.jiekemaike.jiekecommunity.mapper;

import cn.jiekemaike.jiekecommunity.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into User(name,account_id,token,gmt_create,gmt_modified) value(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
