package com.ocdsoft.bacta.swg.datatable;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;

import java.nio.ByteBuffer;

/**
 * Created by crush on 2/8/15.
 */
public abstract class DataTableCell {
    private final CellType cellType;

    public DataTableCell(final CellType cellType) {
        this.cellType = cellType;
    }

    public final CellType getType() {
        return cellType;
    }

    public String getStringValue() {
        throw new UnsupportedOperationException("Attempted to get a string value from a non string cell type.");
    }

    public int getStringValueCrc() {
        throw new UnsupportedOperationException("Attempted to get a string value from a non string cell type.");
    }

    public int getIntValue() {
        throw new UnsupportedOperationException("Attempted to get a int value from a non int cell type.");
    }

    public float getFloatValue() {
        throw new UnsupportedOperationException("Attempted to get a float value from a non float cell type.");
    }

    public static enum CellType {
        String(0),
        Int(1),
        Float(2);

        private final int value;

        private CellType(int value) {
            this.value = value;
        }

        public final int getValue() {
            return value;
        }
    }
}
