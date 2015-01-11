package com.ocdsoft.bacta.swg.shared.iff.datatable;

import com.ocdsoft.bacta.swg.shared.iff.IffWriter;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkWriter;

import java.util.List;

/**
 * Created by crush on 2/12/14.
 */
public class DataTableIffWriter implements IffWriter<DataTable> {
    @Override
    public byte[] write(final DataTable dataTable) {
        ChunkWriter buffer = new ChunkWriter();

        buffer.openForm(DataTable.DTII);
        {
            buffer.openForm(DataTable.V001);
            {
                writeColumnNames(dataTable, buffer);
                writeColumnTypes(dataTable, buffer);
                writeRows(dataTable, buffer);
            }
            buffer.closeChunk();
        }
        buffer.closeChunk();

        return buffer.toByteArray();
    }

    protected void writeColumnNames(final DataTable dataTable, final ChunkWriter buffer) {
        buffer.openChunk(DataTable.COLS);

        List<DataTableColumn> columns = dataTable.getColumns();

        buffer.writeInt(columns.size());

        for (DataTableColumn column : columns)
            buffer.writeNullTerminatedAscii(column.getName());

        buffer.closeChunk();
    }

    protected void writeColumnTypes(final DataTable dataTable, final ChunkWriter buffer) {
        buffer.openChunk(DataTable.TYPE);

        List<DataTableColumn> columns = dataTable.getColumns();

        for (DataTableColumn column : columns) {
            String type = String.valueOf(column.getType());

            if (column.hasDefaultValue())
                type += "[" + column.getDefaultValue() + "]";

            buffer.writeNullTerminatedAscii(type);
        }

        buffer.closeChunk();
    }

    protected void writeRows(final DataTable dataTable, final ChunkWriter buffer) {
        buffer.openChunk(DataTable.ROWS);

        List<DataTableRow> rows = dataTable.getRows();

        buffer.writeInt(rows.size());

        for (DataTableRow row : rows) {
            for (DataTableCell cell : row.getCells())
                cell.write(buffer);
        }

        buffer.closeChunk();
    }
}
