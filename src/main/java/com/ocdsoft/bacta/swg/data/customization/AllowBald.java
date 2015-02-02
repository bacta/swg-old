package com.ocdsoft.bacta.swg.data.customization;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.data.SharedFileLoader;
import com.ocdsoft.bacta.swg.shared.iff.IffReader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTableIffReader;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTableRow;
import com.ocdsoft.bacta.tre.TreeFile;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 3/30/14.
 */
@Singleton
public class AllowBald implements SharedFileLoader {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TObjectIntMap<String> allowBald = new TObjectIntHashMap<>();

    private final TreeFile treeFile;

    @Inject
    public AllowBald(TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }

    /**
     * Can a specified player template be bald or not. Takes a filename, not a filepath. i.e. moncal_female.
     *
     * @param playerTemplate Filename, not filepath. i.e. moncal_female
     * @return Returns true if they are allowed to be bald, otherwise false.
     */
    public boolean isAllowedBald(final String playerTemplate) {
        if (!allowBald.containsKey(playerTemplate))
            return false;

        return allowBald.get(playerTemplate) == 1;
    }

    private void load() {
        logger.trace("Loading allow bald player template settings.");

        final IffReader<DataTable> dataTableReader = new DataTableIffReader();
        final DataTable dataTable = dataTableReader.read(
                new ChunkReader("datatables/customization/allow_bald.iff", treeFile.open("datatables/customization/allow_bald.iff")));

        for (DataTableRow row : dataTable.getRows()) {
            allowBald.put(
                    row.get(0).getString(), //Key
                    row.get(1).getInt());   //Value
        }

        logger.debug(String.format("Loaded %d allow bald player template settings.", allowBald.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            allowBald.clear();
            load();
        }
    }
}
