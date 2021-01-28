package com.qualityunit.test.task.service;

import com.qualityunit.test.task.model.Constants;
import com.qualityunit.test.task.model.Query;
import com.qualityunit.test.task.model.Record;
import com.qualityunit.test.task.service.database.Database;
import com.qualityunit.test.task.service.mapper.Mapper;
import com.qualityunit.test.task.service.validator.Validator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ServiceImpl implements Service {

    private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());
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
    public void analyze(List<String> input, Consumer<String> consumer) {
        String command; int lineCounter = 1;
        for (String data : input) {
            command = String.valueOf(data.charAt(0));
            switch (command) {
                case Constants.RECORD:
                    if(!recordValues(data)) {
                        logger.info("Record at line " + lineCounter + " is not valid. Record was not added to the database.");
                    }
                    break;
                case Constants.QUERY:
                    if(!getAvgWaitingTime(data, consumer)) {
                        logger.info("Unable to execute query at line " + lineCounter + " as it is not valid.");
                    }
                    break;
                default:
                    logger.info("Line " + lineCounter + " was ignored because it does not contain a command.");
            }
            lineCounter++;
        }
    }

    private boolean recordValues(String inputLine) {
        if (!validator.isRecordValid(inputLine)) {
            return false;
        }
        String[] values = getSeparatedValues(inputLine);
        Record record = recordMapper.map(values, Record.class);
        database.recordValues(record);
        return true;
    }

    private boolean getAvgWaitingTime(String inputQuery, Consumer<String> consumer) {
        if (!validator.isQueryValid(inputQuery)) {
            return false;
        }
        String[] values = getSeparatedValues(inputQuery);
        Query query = queryMapper.map(values, Query.class);
        int result = database.getAvgWaitingTime(query);

        if (result != -1) {
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
