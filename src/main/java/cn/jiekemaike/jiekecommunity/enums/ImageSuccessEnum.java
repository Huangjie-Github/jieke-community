package cn.jiekemaike.jiekecommunity.enums;

public enum ImageSuccessEnum {
    UOPLOAD_ERROR(0,"上传失败"),UPLOAD_OK(1,"上传成功");
    private Integer success;
    private String name;

    public Integer getSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }

    ImageSuccessEnum(Integer success, String name) {
        this.success = success;
        this.name = name;
    }
}
