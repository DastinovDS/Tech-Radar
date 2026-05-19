package com.test.gitradar.utils;

import com.test.gitradar.exceptions.StringToLocalDateTimeException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GithubTimeParserUtil {

    public LocalDateTime getLocalDateTimeFromString(String githubResponseString){

        if (githubResponseString != null && !githubResponseString.isEmpty()) {
            java.time.Instant instant = java.time.Instant.parse(githubResponseString);


            return LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault());
        }
        else throw new StringToLocalDateTimeException();
    }
}
