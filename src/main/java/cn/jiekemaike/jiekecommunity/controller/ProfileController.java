package cn.jiekemaike.jiekecommunity.controller;

import cn.jiekemaike.jiekecommunity.dto.PaginationDTO;
import cn.jiekemaike.jiekecommunity.mapper.UserMapper;
import cn.jiekemaike.jiekecommunity.model.ProFileRightTabButton;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.ArrayList;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "size",defaultValue = "${index.problem.pageSize}")Integer size,
                          HttpServletRequest request,Model model){

//        User user = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies!=null)
//            for (Cookie cookie:cookies){
//                if ("token".equals(cookie.getName())){
//                    user = userMapper.findByToken(cookie.getValue());
//                    if (user!=null)
//                        request.getSession().setAttribute("user",user);
//                    break;
//                }
//            }
//        if (user==null)
//            return "redirect:/";

        User user = (User) request.getSession().getAttribute("user");

        ArrayList<ProFileRightTabButton> list = new ArrayList<>();
        list.add(new ProFileRightTabButton("我的问题","questions"));
        list.add(new ProFileRightTabButton("最新回复","replies"));
        model.addAttribute("rightDisPlayText",list);

        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
            PaginationDTO paginationDTO = questionService.proFilePage(page,size,user.getId());
            model.addAttribute("pages",paginationDTO);
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        System.out.println("dbahsdba");
        return "profile";
    }
}