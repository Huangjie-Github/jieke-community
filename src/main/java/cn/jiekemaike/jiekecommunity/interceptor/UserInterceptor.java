package cn.jiekemaike.jiekecommunity.interceptor;

import cn.jiekemaike.jiekecommunity.mapper.UserMapper;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.model.UserExample;
import cn.jiekemaike.jiekecommunity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null)
            for (Cookie cookie : cookies){
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size()>0){
                        request.getSession().setAttribute("user",users.get(0));
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        request.setAttribute("unreadCount",unreadCount);
                    }else {
                        Cookie cookie1 = new Cookie("token", null);
                        cookie1.setMaxAge(0);
                        response.addCookie(cookie1);
                        request.getSession().removeAttribute("user");
                    }
                    break;
                }
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
