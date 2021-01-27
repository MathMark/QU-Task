package com.qualityunit.test.task.service.mapper;

public interface Mapper<T> {
    /**
     * Maps array of <code>String</code> values to an instance of the specified class. Note that
     * class must be inherited from abstract class <code>Input</code> and marked by <code>Metric</code> annotations
     * containing positions of corresponding values in input line.
     * @param values an array that contain values from input line.
     * @param clazz a class into which returned object should be converted. clazz must be
     *              a child of Input class.
     * @return mapped instance of the specified class, collected from array of values.
     */
    T map(String[] values, Class<? extends T> clazz);
}
