package cn.jiekemaike.jiekecommunity.controller;

import cn.jiekemaike.jiekecommunity.dto.ImageFileDTO;
import cn.jiekemaike.jiekecommunity.enums.ImageSuccessEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.OnError;

@Controller
public class ImageFileController {

    @RequestMapping(value = "/imagefile/upload", method = RequestMethod.POST)
    @ResponseBody
    public ImageFileDTO upload(){
        ImageFileDTO imageFileDTO = new ImageFileDTO();
        imageFileDTO.setSuccess(ImageSuccessEnum.UPLOAD_OK.getSuccess());
        imageFileDTO.setMessage(ImageSuccessEnum.UPLOAD_OK.getName());
        imageFileDTO.setUrl("/image/log.jpg");
        return imageFileDTO;
    }

}
