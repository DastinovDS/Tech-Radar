package com.test.gitradar.services;

import com.test.gitradar.exceptions.UrlFormatException;
import com.test.gitradar.models.UrlModel;

public class UrlApiBuilderService {
    private boolean checkPublicUrl(UrlModel url) {
        String[] splittedUrl = url.getSplittedUrl();

        return (splittedUrl.length >= 5) && ("https:".equals(splittedUrl[0])) && ("".equals(splittedUrl[1])) && "github.com".equals(splittedUrl[2]) && (splittedUrl[3] != null) && (splittedUrl[4] != null);
    }

    public String buildPublicUrl(UrlModel url) {
        if (checkPublicUrl(url)) {
            return "https://api.github.com/repos/" + url.getSplittedUrl()[3] + "/" + url.getSplittedUrl()[4];
        }
        else throw new UrlFormatException();
    }

}
