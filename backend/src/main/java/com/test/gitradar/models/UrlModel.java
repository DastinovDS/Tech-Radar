package com.test.gitradar.models;

public class UrlModel {

    private String url;

    public String[] getSplittedUrl(){
        return url.split("/");
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
