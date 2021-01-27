package com.qualityunit.test.task.service.validator;

import java.util.regex.Pattern;

public class ValidatorImpl implements Validator {

    private static final String RECORD_REGEX_FILTER = "C\\s(([1-9]|10)(.[1-3])?)\\s(([1-9]|10)(.([1-9]|(1\\d)|20))?(.[1-5])?)\\s(P|N)" +
            "\\s((3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2}))\\s[\\d]+";
    private static final String QUERY_REGEX_FILTER = "D\\s((([1-9]|10)(.[1-3])?)|\\*)\\s((([1-9]|10)(.([1-9]|(1\\d)|20))?(.[1-5])?)|\\*)\\s(P|N)" +
            "\\s(((3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2}))(-((3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})))?)";


    @Override
    public boolean isRecordValid(String record) {
        Pattern pattern = Pattern.compile(RECORD_REGEX_FILTER);
        return pattern.matcher(record).matches();
    }

    @Override
    public boolean isQueryValid(String query) {
        Pattern pattern = Pattern.compile(QUERY_REGEX_FILTER);
        return pattern.matcher(query).matches();
    }
}
