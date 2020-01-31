package cn.jiekemaike.jiekecommunity.interceptor;

import cn.jiekemaike.jiekecommunity.exception.CustomizeErrorCode;
import cn.jiekemaike.jiekecommunity.exception.CustomizeException;
import cn.jiekemaike.jiekecommunity.mapper.UserMapper;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null)
            for (Cookie cookie : cookies){
                if ("token".equals(cookie.getName())){
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(cookie.getValue());
                    List<User> users = userMapper.selectByExample(userExample);
//                    user = userMapper.findByToken(cookie.getValue());
                    if (users.size()!=0){
                        request.getSession().setAttribute("user",users.get(0));
                        return true;
                    }
                    break;
                }
            }

        throw new CustomizeException(CustomizeErrorCode.WEI_DENG_LU);
//        response.getWriter().println("请登陆以后进行相关操作");
//        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
