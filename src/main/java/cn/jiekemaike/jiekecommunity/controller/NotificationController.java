package cn.jiekemaike.jiekecommunity.controller;

import cn.jiekemaike.jiekecommunity.dto.NotificationDTO;
import cn.jiekemaike.jiekecommunity.enums.NotificationTypeEnum;
import cn.jiekemaike.jiekecommunity.model.Notification;
import cn.jiekemaike.jiekecommunity.model.User;
import cn.jiekemaike.jiekecommunity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profilt(@PathVariable(name = "id")Long id,
                          HttpSession session){
        User user = (User) session.getAttribute("user");
        NotificationDTO notificationDTO = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()
            ||NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }
        return "redirect:/";
    }
}
