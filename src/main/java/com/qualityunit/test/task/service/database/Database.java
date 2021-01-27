package com.qualityunit.test.task.service.database;

import com.qualityunit.test.task.model.Query;
import com.qualityunit.test.task.model.Record;

public interface Database {
    void recordValues(Record record);
    int getAvgWaitingTime(Query query);
}
