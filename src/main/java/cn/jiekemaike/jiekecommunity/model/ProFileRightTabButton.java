package cn.jiekemaike.jiekecommunity.model;

import lombok.Data;

@Data
public class ProFileRightTabButton {
    private String name;
    private String url;

    public ProFileRightTabButton(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
