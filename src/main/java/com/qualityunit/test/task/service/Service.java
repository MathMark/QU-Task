package com.qualityunit.test.task.service;

import java.util.function.Consumer;

public interface Service {
    boolean recordValues(String inputLine);
    boolean getAvgWaitingTime(String inputQuery, Consumer<String> consumer);
}
