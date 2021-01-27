package com.qualityunit.test.task.model;

import com.qualityunit.test.task.annotation.Metric;
import java.util.Date;

public class Record extends Input {
    @Metric(number = 4)
    private Date date;

    @Metric(number = 5)
    private Integer time;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Record{" +
                "date=" + date +
                ", time=" + time +
                ", serviceId='" + serviceId + '\'' +
                ", questionTypeId='" + questionTypeId + '\'' +
                ", responseType='" + responseType + '\'' +
                '}';
    }
}
