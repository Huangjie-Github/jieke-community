package cn.jiekemaike.jiekecommunity.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {

    YE_MIAN_BU_CUN_ZAI("页面不存在"),
    WEI_DENG_LU("未登录");


    CustomizeErrorCode(String message) {
        this.message = message;
    }

    private String message;
    @Override
    public String getMessage() {
        return message;
    }


}
