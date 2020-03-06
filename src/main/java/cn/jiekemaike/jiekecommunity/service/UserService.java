package cn.jiekemaike.jiekecommunity.service;

import cn.jiekemaike.jiekecommunity.dto.GitHubUser;
import cn.jiekemaike.jiekecommunity.mapper.UserMapper;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public User updateUser(GitHubUser gitHubUser){
        User user = null;
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(String.valueOf(gitHubUser.getId()));
        List<User> users = userMapper.selectByExample(userExample);
        String token = UUID.randomUUID().toString();
        if (users.size()==0){
            user = new User();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            userMapper.insert(user);
        }else {
            user = users.get(0);
            user.setGmtModified(System.currentTimeMillis());
            user.setName(gitHubUser.getName());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            user.setToken(token);
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andAccountIdEqualTo(user.getAccountId());
            userMapper.updateByExample(user, userExample1);
        }
        return user;
    }
}
