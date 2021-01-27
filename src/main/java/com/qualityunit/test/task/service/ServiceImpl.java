package com.qualityunit.test.task.service;

import com.qualityunit.test.task.model.Query;
import com.qualityunit.test.task.model.Record;
import com.qualityunit.test.task.service.database.Database;
import com.qualityunit.test.task.service.mapper.Mapper;
import com.qualityunit.test.task.service.validator.Validator;
import java.util.function.Consumer;

public class ServiceImpl implements Service {

    private static final String SEPARATOR = " ";
    private static final String NOT_FOUND = "-";

    private Validator validator;
    private Mapper<Record> recordMapper;
    private Mapper<Query> queryMapper;
    private Database database;

    public ServiceImpl(Validator validator, Mapper<Record> recordMapper, Mapper<Query> queryMapper, Database database) {
        this.validator = validator;
        this.recordMapper = recordMapper;
        this.queryMapper = queryMapper;
        this.database = database;
    }

    @Override
    public boolean recordValues(String inputLine) {
        if(!validator.isRecordValid(inputLine)) {
            return false;
        }
        String[] values = getSeparatedValues(inputLine);
        Record record = recordMapper.map(values, Record.class);
        database.recordValues(record);
        return true;
    }

    @Override
    public boolean getAvgWaitingTime(String inputQuery, Consumer<String> consumer) {
        if(!validator.isQueryValid(inputQuery)) {
            return false;
        }
        String[] values = getSeparatedValues(inputQuery);
        Query query = queryMapper.map(values, Query.class);
        int result = database.getAvgWaitingTime(query);

        if(result != -1) {
            consumer.accept(Integer.toString(result));
        } else {
            consumer.accept(NOT_FOUND);
        }
        return true;
    }

    private String[] getSeparatedValues(String inputLine) {
        return inputLine.split(SEPARATOR);
    }
}
