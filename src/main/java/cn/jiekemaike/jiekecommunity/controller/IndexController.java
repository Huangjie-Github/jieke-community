package cn.jiekemaike.jiekecommunity.controller;

import cn.jiekemaike.jiekecommunity.dto.PaginationDTO;
import cn.jiekemaike.jiekecommunity.dto.QuestionDTO;
import cn.jiekemaike.jiekecommunity.mapper.UserMapper;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.model.UserExample;
import cn.jiekemaike.jiekecommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                              HttpServletResponse response,
                              Model model,
                              @RequestParam(name = "page",defaultValue = "1")Integer page,
                              @RequestParam(value = "size",defaultValue = "${index.problem.pageSize}")Integer size){
        PaginationDTO<QuestionDTO> paginationDTO = questionService.listPage(page, size);
        model.addAttribute("pages",paginationDTO);
        return "index";
    }

}
