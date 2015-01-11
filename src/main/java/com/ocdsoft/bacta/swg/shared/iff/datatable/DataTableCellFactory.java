package com.ocdsoft.bacta.swg.shared.iff.datatable;

import gnu.trove.map.TCharObjectMap;
import gnu.trove.map.hash.TCharObjectHashMap;

/**
 * Created by crush on 2/12/14.
 */
public class DataTableCellFactory {
    private static final TCharObjectMap<DataTableCellFactoryMethod> cellMap = new TCharObjectHashMap<DataTableCellFactoryMethod>();

    static {
        cellMap.put('s', new DataTableCellFactoryMethod() {
            @Override
            public DataTableCell create() {
                return new DataTableStringCell();
            }
        });

        DataTableCellFactoryMethod intFactoryMethod = new DataTableCellFactoryMethod() {
            @Override
            public DataTableCell create() {
                return new DataTableIntCell();
            }
        };
        cellMap.put('i', intFactoryMethod);
        cellMap.put('b', intFactoryMethod); //Acts like a boolean value...seems kind of stupid.
        cellMap.put('I', intFactoryMethod);

        cellMap.put('f', new DataTableCellFactoryMethod() {
            @Override
            public DataTableCell create() {
                return new DataTableFloatCell();
            }
        });
    }

    public DataTableCell create(DataTableColumn column) {
        DataTableCellFactoryMethod method = cellMap.get(column.getType());

        if (method == null)
            return null;

        return method.create();
    }

    private interface DataTableCellFactoryMethod {
        DataTableCell create();
    }
}
