package com.ocdsoft.bacta.swg.shared.object.template;


import com.ocdsoft.bacta.swg.shared.object.crc.PersistentCrcString;

/**
 * Created by crush on 8/15/2014.
 */
public abstract class DataResource<T> {
    private final PersistentCrcString name;

    public DataResource(PersistentCrcString name) {
        this.name = name;
    }

    public final int getCrcName() {
        return name.getCrc();
    }

    public final String getName() {
        return name.getString();
    }
}
