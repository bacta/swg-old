package com.ocdsoft.bacta.swg.datatable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by crush on 2/8/15.
 */
public final class DataTable {
    public int numRows;
    public int numCols;

    public List<DataTableCell> cells;
    public List<String> columns;
    //public List<int> index;
    public List<DataTableColumnType> types;
    //public Map<String, int> columnIndexMap;

    public String name;

    public final String getColumnName(final int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columns.size())
            throw new ArrayIndexOutOfBoundsException(
                    String.format("Invalid column index [%d] in DataTable [%s]. Columns total = [%d].",
                            columnIndex,
                            name,
                            columns.size()));

        return columns.get(columnIndex);
    }

    public final boolean doesColumnExist(final String columnName) {
        for (String col : columns) {
            if (col.equals(columnName))
                return true;
        }

        return false;
    }

    public final int findColumnNumber(final String columnName) {
        for (int i = 0; i < columns.size(); i++) {
            final String col = columns.get(i);
            if (col.equals(columnName))
                return i;
        }

        return -1;
    }

    public final DataTableColumnType getDataType(final String type) {
        return null;
    }
}
