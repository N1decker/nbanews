package ru.nidecker.nbanews.util.validation;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class URLValidator {

    public static final Pattern VALID_URL_REGEX =
            Pattern.compile("(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.\\S{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.\\S{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.\\S{2,}|www\\.[a-zA-Z0-9]+\\.\\S{2,})");

    public static boolean validate(String url) {
        Matcher matcher = VALID_URL_REGEX.matcher(url);
        return matcher.find();
    }
}
