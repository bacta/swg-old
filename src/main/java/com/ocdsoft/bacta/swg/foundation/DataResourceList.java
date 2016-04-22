package com.ocdsoft.bacta.swg.foundation;

import bacta.iff.Iff;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Created by crush on 11/22/2015.
 */
public class DataResourceList<DataType extends DataResource> {
    private final TIntObjectMap<Consumer<DataType>> createDataResourceMap;
    private final Map<CrcString, DataType> loadedDataResourceMap;

    public DataResourceList() {
        createDataResourceMap = new TIntObjectHashMap<>();
        loadedDataResourceMap = new ConcurrentHashMap<>();
    }

    public void registerTemplate(final int id, Consumer<DataType> createFunc) {

    }

    /**
     * Checks the reference count of a resource. If it is 0, deletes it.
     *
     * @param dataResource The data resource to release.
     */
    public void release(final DataType dataResource) {
        if (loadedDataResourceMap.containsKey(dataResource.getCrcName())) {

        }
    }

    public <T extends DataType> T fetch(final int id) {
        return null;
    }

    public <T extends DataType> T fetch(final Iff iff) {
        return null;
    }

    public <T extends DataType> T fetch(final CrcString filename) {
        return null;
    }

    public <T extends DataType> T fetch(final String filename) {
        return null;
    }

    public boolean isLoaded(final String filename) {
        return false;
    }

    public DataType reload(final Iff iff) {
        return null;
    }

    public Consumer<DataType> assignBinding(int id, Consumer<DataType> createFunc) {
        return null;
    }

    public Consumer<DataType> removeBinding(int id) {
        return null;
    }


}
