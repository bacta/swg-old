package com.ocdsoft.bacta.swg.shared.iff.datatable;

import lombok.Getter;
import lombok.Setter;

public class DataTableColumn {
    @Getter
    @Setter
    protected String name;

    @Getter
    @Setter
    protected char type;

    @Getter
    @Setter
    protected String defaultValue;

    public DataTableColumn(final String name) {
        this.name = name;
        this.type = 'i';
    }

    public DataTableColumn(final String name, final char type) {
        this.name = name;
        this.type = type;
    }

    public DataTableColumn(final String name, final char type, final String defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public boolean hasDefaultValue() {
        return defaultValue != null && !defaultValue.equals("");
    }
}
