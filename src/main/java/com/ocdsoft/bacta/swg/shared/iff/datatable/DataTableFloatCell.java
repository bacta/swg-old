package com.ocdsoft.bacta.swg.shared.iff.datatable;


import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkWriter;

public class DataTableFloatCell extends DataTableCell {
    private float value;

    public DataTableFloatCell() {
        this(0.0f);
    }

    public DataTableFloatCell(float value) {
        this.value = value;
    }

    @Override
    public float getFloat() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (float) value;
    }

    @Override
    public void write(ChunkWriter writer) {
        writer.writeFloat(value);
    }

    @Override
    public void read(ChunkReader reader) {
        value = reader.readFloat();
    }
}
