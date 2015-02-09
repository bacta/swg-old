package com.ocdsoft.bacta.swg.datatable;

/**
 * Created by crush on 2/8/15.
 */
public final class DataTableFloatCell extends DataTableCell {
    private float value;

    public DataTableFloatCell(float value) {
        super(CellType.Float);

        this.value = value;
    }

    @Override
    public final float getFloatValue() {
        return value;
    }
}
