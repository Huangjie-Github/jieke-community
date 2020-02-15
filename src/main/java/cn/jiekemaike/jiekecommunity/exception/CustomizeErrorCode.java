package cn.jiekemaike.jiekecommunity.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {

    YE_MIAN_BU_CUN_ZAI(2001, "页面不存在"),
    WEI_DENG_LU(2002, "未登录账户，不能进行相关操作"),
    TARGET_PARAM_NOT_FOUND(2003, "未选中任何问题或评论进行回复"),
    SYS_EXCEPTION(2004, "ControllerAdvice->未知的异常"),
    TYPE_PARAM_WROHG(2005, "评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(2006, "您所回复的评论不存在了"),
    QUESTION_NOT_FOUND(2007, "您所回复的问题不存在了"),
    COMMENT_CONTENT_NOT_NULL(2008,"评论内容不能为空"),
    READ_NOTIFICATION_FAIL(2009,"兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2010,"这个消息不存在了！！！");


    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    @Override
    public Integer getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }

}
