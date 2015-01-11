package com.ocdsoft.bacta.swg.shared.iff.datatable;

import java.util.ArrayList;
import java.util.List;

public final class DataTableRow {
    private final List<DataTableCell> cells;

    private final DataTable dataTable;

    public DataTableRow(DataTable dataTable) {
        this.dataTable = dataTable;
        this.cells = new ArrayList<DataTableCell>(dataTable.getColumns().size());
    }

    public DataTableCell get(int index) {
        return cells.get(index);
    }

    public boolean add(DataTableCell cell) {
        return cells.add(cell);
    }

    public DataTableCell set(int index, DataTableCell cell) {
        return cells.set(index, cell);
    }

    public List<DataTableCell> getCells() {
        return cells;
    }
}
