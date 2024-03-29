package com.ocdsoft.bacta.swg.datatable;

import com.ocdsoft.bacta.soe.util.SOECRC32;

/**
 * Created by crush on 2/8/15.
 */
public final class DataTableCell {
    private final CellType type;

    private int intValue;
    private float floatValue;
    private String stringValue;
    private int stringValueCrc;

    public DataTableCell(int value) {
        this.type = CellType.Int;
        this.intValue = value;
    }

    public DataTableCell(float value) {
        this.type = CellType.Float;
        this.floatValue = value;
    }

    public DataTableCell(final String value) {
        this.type = CellType.String;
        this.stringValue = value;
        this.stringValueCrc = SOECRC32.hashCode(value);
    }

    public CellType getType() {
        return type;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public int getStringValueCrc() {
        return this.stringValueCrc;
    }

    public enum CellType {
        String(0),
        Int(1),
        Float(2);

        private final int value;

        CellType(int value) {
            this.value = value;
        }

        public final int getValue() {
            return value;
        }
    }
}
