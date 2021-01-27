package com.qualityunit.test.task.service.mapper;

import com.qualityunit.test.task.annotation.Metric;
import com.qualityunit.test.task.model.Input;
import com.qualityunit.test.task.model.Constants;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ReflectionMapper<T extends Input> implements Mapper<T> {

    public T map(String[] values, Class<? extends T> clazz){
        T record;
        try{
            record = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
        List<Field> fields = getFields(record);
        String value;
        Metric metric;
        for(Field field : fields) {
            if((metric = field.getAnnotation(Metric.class)) != null) {
                value = values[metric.number()];
                try {
                    record(field, record, value);
                } catch (IntrospectionException | InvocationTargetException |
                        IllegalAccessException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return record;
    }

    private List<Field> getFields(T t) {
        List<Field> fields = new ArrayList<>();
        Class clazz = t.getClass();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private void record(Field field, T record, String value) throws IntrospectionException, InvocationTargetException,
            IllegalAccessException, ParseException {
        Class fieldType = field.getType();
        Object localValue = value;
        if (Integer.class.equals(fieldType)) {
            localValue = Integer.parseInt(value);
        } else if(Date.class.equals(fieldType)) {
            localValue = new SimpleDateFormat(Constants.DATE_FORMAT).parse(value);
        }
        new PropertyDescriptor(field.getName(), record.getClass())
                .getWriteMethod()
                .invoke(record, localValue);
    }
}
