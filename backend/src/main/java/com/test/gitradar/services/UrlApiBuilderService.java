package com.test.gitradar.services;

import com.test.gitradar.exceptions.UrlFormatException;
import com.test.gitradar.models.RepositoryModel;
import com.test.gitradar.models.UrlModel;
import com.test.gitradar.models.UserModel;
import org.springframework.stereotype.Service;

@Service
public final class UrlApiBuilderService {

    private UrlApiBuilderService(){}

    public static final String GITHUB_API_URL = "https://api.github.com/";
    public static boolean checkPublicUrl(UrlModel url) {
        String[] splitUrl = url.getSplittedUrl();

        return (splitUrl.length >= 5)
                && ("https:".equals(splitUrl[0]))
                && ("".equals(splitUrl[1]))
                && "github.com".equals(splitUrl[2])
                && (splitUrl[3] != null)
                && (splitUrl[4] != null);
    }

    public static String buildPublicUrl(UrlModel url) {
        if (checkPublicUrl(url)) {
            return GITHUB_API_URL + "repos/" + url.getSplittedUrl()[3] + "/" + url.getSplittedUrl()[4];
        }
        else throw new UrlFormatException();
    }

    public static String buildRepositoryUrl(UserModel user, RepositoryModel repository) {
        return GITHUB_API_URL + "repos/" + user.getLogin() + "/" + repository.getName();
    }

    public static String buildAuthRepoUrl(UserModel user) {
        return GITHUB_API_URL + "users/" + user.getLogin() + "/repos?per_page=100";
    }
}
