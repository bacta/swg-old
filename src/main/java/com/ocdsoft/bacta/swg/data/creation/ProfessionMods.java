package com.ocdsoft.bacta.swg.data.creation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.data.SharedFileLoader;
import com.ocdsoft.bacta.swg.shared.iff.IffReader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTableIffReader;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTableRow;
import com.ocdsoft.bacta.tre.TreeFile;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 3/28/14.
 */
@Singleton
public class ProfessionMods implements SharedFileLoader {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, ProfessionModInfo> professionMods = new HashMap<>();
    private final TreeFile treeFile;

    @Inject
    public ProfessionMods(TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }

    /**
     * Gets a {@link ProfessionModInfo} object for the
     * specified profession.
     *
     * @param profession The profession is a string. i.e. combat_marksman, crafting_artisan, etc.
     * @return {@link ProfessionModInfo} if the key exists.
     * Otherwise, null.
     */
    public ProfessionModInfo getProfessionModInfo(final String profession) {
        return professionMods.get(profession);
    }

    private void load() {
        logger.trace("Loading profession mods.");

        final IffReader<DataTable> dataTableReader = new DataTableIffReader();
        final DataTable dataTable = dataTableReader.read(
                new ChunkReader("datatables/creation/profession_mods.iff", treeFile.open("datatables/creation/profession_mods.iff")));

        for (DataTableRow row : dataTable.getRows()) {
            ProfessionModInfo modInfo = new ProfessionModInfo(row);
            professionMods.put(modInfo.profession, modInfo);
        }

        logger.debug(String.format("Loaded %d profession mods.", professionMods.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            professionMods.clear();
            load();
        }
    }

    public static final class ProfessionModInfo {
        @Getter
        private final String profession;
        @Getter
        private final Collection<Integer> attributes = new ArrayList<>(9);

        public ProfessionModInfo(DataTableRow row) {
            profession = row.get(0).getString();
            attributes.add(row.get(1).getInt());
            attributes.add(row.get(2).getInt());
            attributes.add(row.get(3).getInt());
            attributes.add(row.get(4).getInt());
            attributes.add(row.get(5).getInt());
            attributes.add(row.get(6).getInt());
            attributes.add(row.get(7).getInt());
            attributes.add(row.get(8).getInt());
            attributes.add(row.get(9).getInt());
        }
    }
}
