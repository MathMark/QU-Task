package com.qualityunit.test.task.model;

import com.qualityunit.test.task.annotation.Metric;

public abstract class Input {
    @Metric(number = 1)
    String serviceId;

    @Metric(number = 2)
    String questionTypeId;

    @Metric(number = 3)
    String responseType;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
}
