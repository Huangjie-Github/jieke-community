package cn.jiekemaike.jiekecommunity.controller;

import cn.jiekemaike.jiekecommunity.dto.CommentDTO;
import cn.jiekemaike.jiekecommunity.dto.QuestionDTO;
import cn.jiekemaike.jiekecommunity.enums.CommentTypeEnum;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.service.CommentService;
import cn.jiekemaike.jiekecommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping(path = "/question/{id}")
    public String questions(@PathVariable(name = "id")Long id,
                            Model model,
                            HttpServletRequest request){
        QuestionDTO questionDTO = questionService.findById(id);
        questionService.updateView(id);
        model.addAttribute("questionDTO", questionDTO);
        List<CommentDTO> commentDTOS =  commentService.listByQuestionId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("commentDTOS",commentDTOS);
        String account_id = questionDTO.getAccountId();
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            model.addAttribute("editText",account_id.equals(user.getAccountId()));
        }else {
            model.addAttribute("editText",false);
        }
        return "question";
    }
}
