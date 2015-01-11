package com.ocdsoft.bacta.swg.shared.iff.datatable;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkWriter;

public abstract class DataTableCell {
    public int getInt() {
        throw new UnsupportedOperationException();
    }

    public float getFloat() {
        throw new UnsupportedOperationException();
    }

    public String getString() {
        throw new UnsupportedOperationException();
    }

    public abstract void setValue(Object value);

    public abstract void write(ChunkWriter writer);

    public abstract void read(ChunkReader reader);
}
