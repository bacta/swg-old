package com.ocdsoft.bacta.swg.datatable;

/**
 * Created by crush on 2/8/15.
 */
public final class DataTableIntCell extends DataTableCell {
    private int value;

    public DataTableIntCell(int value) {
        super(CellType.Int);

        this.value = value;
    }

    @Override
    public final int getIntValue() {
        return this.value;
    }
}
