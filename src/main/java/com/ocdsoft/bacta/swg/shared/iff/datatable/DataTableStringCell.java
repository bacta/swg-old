package com.ocdsoft.bacta.swg.shared.iff.datatable;


import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkWriter;

public class DataTableStringCell extends DataTableCell {
    private String value;

    public DataTableStringCell() {
        this("");
    }

    public DataTableStringCell(String value) {
        this.value = value;
    }

    @Override
    public String getString() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (String) value;
    }

    @Override
    public void write(ChunkWriter writer) {
        writer.writeNullTerminatedAscii(value);
    }

    @Override
    public void read(ChunkReader reader) {
        value = reader.readNullTerminatedAscii();
    }
}
