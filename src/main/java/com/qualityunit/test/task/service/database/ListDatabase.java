package com.qualityunit.test.task.service.database;

import com.qualityunit.test.task.model.Query;
import com.qualityunit.test.task.model.Constants;
import com.qualityunit.test.task.model.Record;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ListDatabase implements Database {

    private final List<Record> records = new ArrayList<>();

    @Override
    public void recordValues(Record record) {
        records.add(record);
    }

    private boolean containsTwoDates(String dateFromTo) {
        return dateFromTo.contains("-");
    }

    private Date getDate(String dateValue) {
        try {
            return new SimpleDateFormat(Constants.DATE_FORMAT).parse(dateValue);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getAvgWaitingTime(Query query) {
        String[] dates = query.getDateFromTo().split("-");
        Date dateTo = null;
        Date dateFrom = getDate(dates[0]);
        if(containsTwoDates(query.getDateFromTo())) {
            dateTo = getDate(dates[1]);
        }
        final Date finalDateTo = dateTo;
        List<Record> records = this.records.stream().filter(r -> (query.getServiceId().equals("*") || r.getServiceId().startsWith(query.getServiceId()))
                && (query.getQuestionTypeId().equals("*") || r.getQuestionTypeId().startsWith(query.getQuestionTypeId()))
                && r.getResponseType().equals(query.getResponseType())
                && ((finalDateTo != null) ? r.getDate().after(dateFrom) && r.getDate().before(finalDateTo) : r.getDate().before(dateFrom)))
                .collect(Collectors.toList());

        if(records.isEmpty()) {
            return -1;
        } else {
            int sum = records.stream().mapToInt(Record::getTime).sum();
            return sum / records.size();
        }
    }
}
