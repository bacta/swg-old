package com.ocdsoft.bacta.swg.datatable;

import com.ocdsoft.bacta.soe.util.SOECRC32;

/**
 * Created by crush on 2/8/15.
 */
public final class DataTableStringCell extends DataTableCell {
    private String value;
    private int crc;

    public DataTableStringCell(final String value) {
        super(CellType.String);

        this.value = value;
        this.crc = SOECRC32.hashCode(value);
    }

    @Override
    public final String getStringValue() {
        return value;
    }

    @Override
    public final int getStringValueCrc() {
        return crc;
    }
}
