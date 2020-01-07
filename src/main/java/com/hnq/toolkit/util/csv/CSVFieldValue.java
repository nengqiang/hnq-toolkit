package com.hnq.toolkit.util.csv;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author hanif
 * @date 2019/08/07
 */
@Data
public class CSVFieldValue {
    CSVField csvField;

    Field field;

    public CSVFieldValue(CSVField csvField, Field field) {
        this.csvField = csvField;
        this.field = field;
    }
}
