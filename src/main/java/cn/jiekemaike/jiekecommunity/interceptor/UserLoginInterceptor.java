package cn.jiekemaike.jiekecommunity.interceptor;

import cn.jiekemaike.jiekecommunity.mapper.UserMapper;
import cn.jiekemaike.jiekecommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null)
            for (Cookie cookie : cookies){
                if ("token".equals(cookie.getName())){
                    user = userMapper.findByToken(cookie.getValue());
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                        return true;
                    }
                    break;
                }
            }
        response.getWriter().println("请登陆以后进行相关操作");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
