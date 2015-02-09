package com.ocdsoft.bacta.swg.datatable;

/**
 * Created by crush on 2/8/15.
 */
public class InvalidDataTypeValueException extends Exception {
    public InvalidDataTypeValueException(final String value, final DataTableColumnType.DataType type) {
        super(String.format("Invalid value [%s] specified for data type [%s].",
                value,
                type.toString()));
    }
}
