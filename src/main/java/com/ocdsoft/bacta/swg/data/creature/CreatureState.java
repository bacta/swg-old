package com.ocdsoft.bacta.swg.data.creature;

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
public class CreatureState implements SharedFileLoader {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TObjectIntMap<String> states = new TObjectIntHashMap<>();

    private final TreeFile treeFile;

    @Inject
    public CreatureState(TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }

    private void load() {
        logger.trace("Loading creature states datatable.");

        final IffReader<DataTable> dataTableReader = new DataTableIffReader();
        final DataTable dataTable = dataTableReader.read(
                new ChunkReader("datatables/include/state.iff", treeFile.open("datatables/include/state.iff")));

        for (DataTableRow row : dataTable.getRows()) {
            states.put(
                    row.get(0).getString(), //Key
                    row.get(1).getInt());   //Value
        }

        logger.debug(String.format("Loaded %d creature states.", states.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            states.clear();
            load();
        }
    }
}
