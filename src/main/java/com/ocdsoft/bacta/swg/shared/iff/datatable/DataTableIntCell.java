package com.ocdsoft.bacta.swg.shared.iff.datatable;


import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkWriter;

public class DataTableIntCell extends DataTableCell {
    private int value;

    public DataTableIntCell() {
        this(0);
    }

    public DataTableIntCell(int value) {
        this.value = value;
    }

    @Override
    public int getInt() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (int) value;
    }

    @Override
    public void write(ChunkWriter writer) {
        writer.writeInt(value);
    }

    @Override
    public void read(ChunkReader reader) {
        value = reader.readInt();
    }
}
