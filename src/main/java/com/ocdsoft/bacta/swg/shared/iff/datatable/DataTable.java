package com.ocdsoft.bacta.swg.shared.iff.datatable;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBuffer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DataTable {
    public static final int DTII = ChunkBuffer.createChunkId("DTII");
    public static final int V001 = ChunkBuffer.createChunkId("0001");
    public static final int COLS = ChunkBuffer.createChunkId("COLS");
    public static final int TYPE = ChunkBuffer.createChunkId("TYPE");
    public static final int ROWS = ChunkBuffer.createChunkId("ROWS");

    protected DataTableCellFactory cellFactory;

    @Getter
    private final List<DataTableColumn> columns = new ArrayList<DataTableColumn>();
    @Getter
    private final List<DataTableRow> rows = new ArrayList<DataTableRow>();

    public DataTable(DataTableCellFactory cellFactory) {
        this.cellFactory = cellFactory;
    }

    public DataTableRow createEmptyRow() {
        DataTableRow row = new DataTableRow(this);

        for (int i = 0; i < columns.size(); i++) {
            row.add(cellFactory.create(columns.get(i)));
        }

        return row;
    }

    public DataTableColumn getColumn(int index) {
        return columns.get(index);
    }

    public DataTableRow getRow(int index) {
        return rows.get(index);
    }

    public void addColumn(DataTableColumn column) {
        columns.add(column);
    }

    public void insertColumn(int index, DataTableColumn column) {
        columns.add(index, column);
    }


    public DataTableCell getCell(int columnIndex, int rowIndex) {
        return rows.get(rowIndex).get(columnIndex);
    }

    public void insertRow(int index, DataTableRow row) {
        rows.add(index, row);
    }

    public void addRow(DataTableRow row) {
        rows.add(row);
    }

    public boolean removeRow(DataTableRow row) {
        return rows.remove(row);
    }

    public DataTableRow removeRow(int index) {
        return rows.remove(index);
    }
}

