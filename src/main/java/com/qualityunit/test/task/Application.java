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
    private static final String INPUT_FILE_NAME = "input2.txt";
    private static final String OUTPUT_FILE_NAME = "output.txt";

    public static void main(String[] args) {
        Validator validator = new ValidatorImpl();
        Mapper<Record> recordMapper = new ReflectionMapper<>();
        Mapper<Query> queryMapper = new ReflectionMapper<>();
        Database database = new ListDatabase();
        Service service = new ServiceImpl(validator, recordMapper, queryMapper, database);

        try(BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_NAME)); Console console = new Console(OUTPUT_FILE_NAME)) {

            int lineCount = Integer.parseInt(reader.readLine());
            List<String> lines = reader.lines().limit(lineCount).collect(Collectors.toList());

            if((lineCount < 0) || (lineCount > Constants.MAX_LINES)) {
                logger.severe("Incorrect number of lines. A file should contain from 1 until " + Constants.MAX_LINES + " lines.");
            } else {
                if (lineCount != lines.size()){
                    logger.info("The number of rows does not coincide with the actual number of rows in input files.");
                }
                logger.info(lineCount + " lines have been read.");
                service.analyze(lines, console::writeLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
