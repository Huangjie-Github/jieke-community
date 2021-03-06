package cn.jiekemaike.jiekecommunity.dto;

import cn.jiekemaike.jiekecommunity.exception.CustomizeException;
import cn.jiekemaike.jiekecommunity.exception.ICustomizeErrorCode;
import lombok.Data;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO errorOf(CustomizeException e){
        return ResultDTO.errorOf(e.getCode(),e.getMessage());
    }
    public static ResultDTO errorOf(ICustomizeErrorCode errorCode){
        return ResultDTO.errorOf(errorCode.getCode(),errorCode.getMessage());
    }
    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
    public static <T> ResultDTO okOf(T data){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(data);
        return resultDTO;
    }
}
