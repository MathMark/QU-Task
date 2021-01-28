package com.qualityunit.test.task.service;

import java.util.List;
import java.util.function.Consumer;

public interface Service {
    void analyze(List<String> input, Consumer<String> consumer);
}
