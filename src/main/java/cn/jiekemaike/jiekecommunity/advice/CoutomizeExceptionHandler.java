package cn.jiekemaike.jiekecommunity.advice;

import cn.jiekemaike.jiekecommunity.dto.ResultDTO;
import cn.jiekemaike.jiekecommunity.exception.CustomizeErrorCode;
import cn.jiekemaike.jiekecommunity.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@ControllerAdvice
public class CoutomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, HttpServletResponse response, Model model) {
        String contentType = request.getContentType();
        System.out.println("测试："+contentType);
        if ("application/json".equals(contentType.split(";")[0])){
            ResultDTO resultDTO = null;
            if (ex instanceof CustomizeException){
                resultDTO =  ResultDTO.errorOf((CustomizeException) ex);
            }else {
                resultDTO =  ResultDTO.errorOf(CustomizeErrorCode.SYS_EXCEPTION);
            }
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            if (ex instanceof CustomizeException){
                model.addAttribute("message",ex.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_EXCEPTION.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
