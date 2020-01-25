package cn.jiekemaike.jiekecommunity.provider;

import cn.jiekemaike.jiekecommunity.dto.AccessTokenDTO;
import cn.jiekemaike.jiekecommunity.dto.GitHubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {


    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        try {
            MediaType mediaType = MediaType.get("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            String tokenStr = string.split("&")[0];
            String accessToken = tokenStr.split("=")[1] ;
            System.out.println("测试1："+string);
            return accessToken;
        } catch (Exception e) {
        }
        return null;
    }

    public GitHubUser getUser(String accessToken) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return gitHubUser;
        } catch (Exception e) {
        }
        return null;
    }
}
