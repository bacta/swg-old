package com.ocdsoft.bacta.swg.foundation;

/**
 * Created by crush on 11/22/2015.
 * <p>
 * It seems that this class is meant to reference count a resource, but we aren't going to use it for that ... yet.
 */
public abstract class DataResource {
    private final PersistentCrcString name;

    public DataResource(final String fileName) {
        this.name = new PersistentCrcString(fileName, true);
    }

    public String getName() {
        return name.getString();
    }

    public CrcString getCrcName() {
        return name;
    }
}
