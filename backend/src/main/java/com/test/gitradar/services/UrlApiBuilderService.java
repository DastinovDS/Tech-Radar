package com.test.gitradar.services;

import com.test.gitradar.exceptions.UrlFormatException;
import com.test.gitradar.models.RepositoryModel;
import com.test.gitradar.models.UrlModel;
import com.test.gitradar.models.UserModel;
import org.springframework.stereotype.Service;

@Service
public class UrlApiBuilderService {

    public static final String GITHUB_API_URL = "https://api.github.com/";
    private boolean checkPublicUrl(UrlModel url) {
        String[] splittedUrl = url.getSplittedUrl();

        return (splittedUrl.length >= 5)
                && ("https:".equals(splittedUrl[0]))
                && ("".equals(splittedUrl[1]))
                && "github.com".equals(splittedUrl[2])
                && (splittedUrl[3] != null)
                && (splittedUrl[4] != null);
    }

    public String buildPublicUrl(UrlModel url) {
        if (checkPublicUrl(url)) {
            return GITHUB_API_URL + "repos/" + url.getSplittedUrl()[3] + "/" + url.getSplittedUrl()[4];
        }
        else throw new UrlFormatException();
    }

    public String buildRepositoryUrl(UserModel user, RepositoryModel repository) {
        return GITHUB_API_URL + "repos/" + user.getLogin() + "/" + repository.getName();
    }

    public String buildAuthRepoUrl(UserModel user) {
        return GITHUB_API_URL + "users/" + user.getLogin() + "/repos?per_page=100";
    }
}
