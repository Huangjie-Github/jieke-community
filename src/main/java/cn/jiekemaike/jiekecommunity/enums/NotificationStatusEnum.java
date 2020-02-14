package cn.jiekemaike.jiekecommunity.enums;

public enum NotificationStatusEnum {
    UNREAD(0,"未处理"),READ(1,"已处理");
    private int status;
    private String name;

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    NotificationStatusEnum(int status,String name) {
        this.status = status;
        this.name = name;
    }
}
