package cn.jiekemaike.jiekecommunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class OutLoginController {
    @GetMapping("/outLogin")
    public String outLogin(HttpServletRequest request,
                           HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if ("token".equals(cookie.getName())){
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
