package cn.jiekemaike.jiekecommunity.service;

import cn.jiekemaike.jiekecommunity.dto.GitHubUser;
import cn.jiekemaike.jiekecommunity.mapper.UserMapper;
import cn.jiekemaike.jiekecommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User updateUser(GitHubUser gitHubUser){
        User user = userMapper.findByAccountId(gitHubUser.getId());
        String token = UUID.randomUUID().toString();
        if (user==null){
            user = new User();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccount_id(String.valueOf(gitHubUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            user.setAvatar_url(gitHubUser.getAvatar_url());
        }else {
            user.setGmt_modified(System.currentTimeMillis());
            user.setName(gitHubUser.getName());
            user.setAvatar_url(gitHubUser.getAvatar_url());
            user.setToken(token);
            userMapper.updateUSer(user);
        }
        return user;
    }
}
