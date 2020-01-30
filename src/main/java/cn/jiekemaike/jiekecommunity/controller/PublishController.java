package cn.jiekemaike.jiekecommunity.controller;

import cn.jiekemaike.jiekecommunity.dto.QuestionDTO;
import cn.jiekemaike.jiekecommunity.mapper.QuestionMapper;
import cn.jiekemaike.jiekecommunity.mapper.UserMapper;
import cn.jiekemaike.jiekecommunity.model.Question;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping(path = "/publish")
    public String publish(){
        return "publish";
    }
    @GetMapping(path = "/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                          Model model){
        QuestionDTO questionDTO = questionService.findById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("id",id);
        return "publish";
    }
    @PostMapping(path = "/publish")
    public String doPublish(@RequestParam(name = "title",required = false)String title,
                            @RequestParam(name = "description",required = false)String description,
                            @RequestParam(name = "tag",required = false)String tag,
                            @RequestParam(name = "id",required = false)Integer id,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title==null||title==""){
            model.addAttribute("msg","标题不能为空");
            return "publish";
        }
        if (description==null||description==""){
            model.addAttribute("msg","描述不能为空");
            return "publish";
        }
        if (tag==null||tag==""){
            model.addAttribute("msg","标签不能为空");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("msg","用户未登陆");
            return "publish";
        }else {
            Question question = new Question();
            question.setId(id);
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getId());
            question.setAccountId(user.getAccountId());
            questionService.createOrUpdate(question);
            return "redirect:/";
        }
    }
}
