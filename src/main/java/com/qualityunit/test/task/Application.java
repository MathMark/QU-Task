package com.qualityunit.test.task;

import com.qualityunit.test.task.model.Constants;
import com.qualityunit.test.task.model.Query;
import com.qualityunit.test.task.model.Record;
import com.qualityunit.test.task.service.Service;
import com.qualityunit.test.task.service.ServiceImpl;
import com.qualityunit.test.task.service.console.Console;
import com.qualityunit.test.task.service.mapper.Mapper;
import com.qualityunit.test.task.service.database.Database;
import com.qualityunit.test.task.service.database.ListDatabase;
import com.qualityunit.test.task.service.mapper.ReflectionMapper;
import com.qualityunit.test.task.service.validator.Validator;
import com.qualityunit.test.task.service.validator.ValidatorImpl;
import java.io.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String OUTPUT_FILE_NAME = "output.txt";

    public static void main(String[] args) {
        Validator validator = new ValidatorImpl();
        Mapper<Record> recordMapper = new ReflectionMapper<>();
        Mapper<Query> queryMapper = new ReflectionMapper<>();
        Database database = new ListDatabase();
        Service service = new ServiceImpl(validator, recordMapper, queryMapper, database);

        try(BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_NAME));
            Console console = new Console(OUTPUT_FILE_NAME)) {
            int lineCount = Integer.parseInt(reader.readLine());
            List<String> lines = reader.lines().limit(lineCount).collect(Collectors.toList());
            if((lineCount < 0) || (lineCount > Constants.MAX_LINES)) {
                logger.severe("Incorrect number of lines. A file should contain from 1 until " + Constants.MAX_LINES + " lines.");
            } else {
                logger.info(lineCount + " lines have been read.");

                boolean isRecorded, isQueried;
                int lineCounter = 1;

                for(String line : lines) {
                    if(line.startsWith(Constants.RECORD)) {
                        isRecorded = service.recordValues(line);
                        if(!isRecorded) {
                            logger.info("Line " + lineCounter + " is not valid. Record was not added to the database.");
                        }
                    } else if(line.startsWith(Constants.QUERY)) {
                        isQueried = service.getAvgWaitingTime(line, console::writeLine);
                        if(!isQueried) {
                            logger.info("Unable to execute query at line " + lineCounter + " as it is not valid.");
                        }
                    }
                    lineCounter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
