package com.test.gitradar.dto;

public class UrlDTO {

    private String url;

    public String[] getSplitUrl(){
        return url.split("/");
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
