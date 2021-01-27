package com.qualityunit.test.task.model;

import com.qualityunit.test.task.annotation.Metric;

public class Query extends Input {
    @Metric(number = 4)
    private String dateFromTo;

    public String getDateFromTo() {
        return dateFromTo;
    }

    public void setDateFromTo(String dateFromTo) {
        this.dateFromTo = dateFromTo;
    }

    @Override
    public String toString() {
        return "Query{" +
                "dateFromTo='" + dateFromTo + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", questionTypeId='" + questionTypeId + '\'' +
                ", responseType='" + responseType + '\'' +
                '}';
    }
}
