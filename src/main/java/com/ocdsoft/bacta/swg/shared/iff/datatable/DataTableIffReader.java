package com.ocdsoft.bacta.swg.shared.iff.datatable;

import com.ocdsoft.bacta.swg.shared.iff.IffReader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;

import java.util.List;

/**
 * Created by crush on 2/12/14.
 */
public class DataTableIffReader implements IffReader<DataTable> {
    @Override
    public DataTable read(final ChunkReader buffer) {
        final DataTable dataTable = new DataTable(new DataTableCellFactory());

        buffer.openForm(DataTable.DTII);
        {
            buffer.openForm(DataTable.V001);
            {
                readColumnNames(dataTable, buffer);
                readColumnTypes(dataTable, buffer);
                readRows(dataTable, buffer);
            }
            buffer.closeChunk();
        }
        buffer.closeChunk();

        return dataTable;
    }

    protected void readColumnNames(DataTable dataTable, ChunkReader buffer) {
        buffer.openChunk(DataTable.COLS);

        int cols = buffer.readInt();

        List<DataTableColumn> columns = dataTable.getColumns();

        for (int i = 0; i < cols; i++)
            columns.add(new DataTableColumn(buffer.readNullTerminatedAscii()));

        buffer.closeChunk();
    }

    protected void readColumnTypes(DataTable dataTable, ChunkReader buffer) {
        buffer.openChunk(DataTable.TYPE);

        for (DataTableColumn column : dataTable.getColumns()) {
            String type = buffer.readNullTerminatedAscii();
            column.setType(type.charAt(0));

            if (type.length() > 1 && type.charAt(1) == '[')
                column.setDefaultValue(type.substring(type.indexOf('['), type.lastIndexOf(']')));
        }

        buffer.closeChunk();
    }

    protected void readRows(DataTable dataTable, ChunkReader buffer) {
        buffer.openChunk(DataTable.ROWS);

        int rows = buffer.readInt();

        for (int i = 0; i < rows; i++) {
            DataTableRow row = dataTable.createEmptyRow();

            for (DataTableCell cell : row.getCells()) {
                cell.read(buffer);
            }

            dataTable.addRow(row);
        }

        buffer.closeChunk();
    }
}
