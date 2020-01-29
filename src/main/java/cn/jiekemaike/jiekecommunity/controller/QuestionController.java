package cn.jiekemaike.jiekecommunity.controller;

import cn.jiekemaike.jiekecommunity.dto.QuestionDTO;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping(path = "/question/{id}")
    public String questions(@PathVariable(name = "id")Integer id,
                            Model model,
                            HttpServletRequest request){
        QuestionDTO questionDTO = questionService.findById(id);
        model.addAttribute("questionDTO", questionDTO);
        String account_id = questionDTO.getAccount_id();
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            model.addAttribute("editText",account_id.equals(user.getAccount_id()));
        }else {
            model.addAttribute("editText",false);
        }
        return "question";
    }
}
