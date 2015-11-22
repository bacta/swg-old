package com.ocdsoft.bacta.swg.datatable;

import java.util.List;

/**
 * Created by crush on 2/8/15.
 * Converts an XML document, that looks like possibly exported from Excel, into the SWG DataTable format.
 */
public final class DataTableWriter {
    public static final class NamedDataTable {
        //private final String name;
        //private Collection<Collection<DataTableCell>> rows;
        //private Collection<String> columns;
        //private Collection<DataTableColumnType> types;
    }

    public List<NamedDataTable> tables;
    public String path;
}
