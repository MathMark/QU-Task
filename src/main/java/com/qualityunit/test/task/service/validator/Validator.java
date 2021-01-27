package com.qualityunit.test.task.service.validator;


public interface Validator {
    boolean isRecordValid(String record);
    boolean isQueryValid(String query);
}
