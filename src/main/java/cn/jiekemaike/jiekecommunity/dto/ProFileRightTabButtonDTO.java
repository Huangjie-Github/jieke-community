package cn.jiekemaike.jiekecommunity.dto;

import lombok.Data;

@Data
public class ProFileRightTabButtonDTO {
    private String name;
    private String url;

    public ProFileRightTabButtonDTO(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
