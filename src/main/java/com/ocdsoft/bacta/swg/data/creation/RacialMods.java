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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 3/28/14.
 */
@Singleton
public class RacialMods implements SharedFileLoader {
    private final Map<String, RacialModInfo> racialMods = new HashMap<>();

    private final TreeFile treeFile;

    @Inject
    public RacialMods(TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }

    /**
     * Gets a {@link RacialModInfo} object for the given
     * player template.
     *
     * @param playerTemplate Player template is a string. It's a file name, not a file path. i.e. bothan_male.
     * @return {@link RacialModInfo} if key exists. Otherwise, null.
     */
    public RacialModInfo getRacialModInfo(final String playerTemplate) {
        return racialMods.get(playerTemplate);
    }

    private void load() {
        final IffReader<DataTable> dataTableReader = new DataTableIffReader();
        final DataTable dataTable = dataTableReader.read(
                new ChunkReader("datatables/creation/racial_mods.iff", treeFile.open("datatables/creation/racial_mods.iff")));

        for (DataTableRow row : dataTable.getRows()) {
            RacialModInfo modInfo = new RacialModInfo(row);
            racialMods.put(modInfo.maleTemplate, modInfo);
            racialMods.put(modInfo.femaleTemplate, modInfo);
        }
    }

    @Override
    public void reload() {
        synchronized (this) {
            racialMods.clear();
            load();
        }
    }


    public static final class RacialModInfo {
        @Getter
        private final String maleTemplate;
        @Getter
        private final String femaleTemplate;
        @Getter
        private final int health;
        @Getter
        private final int strength;
        @Getter
        private final int constitution;
        @Getter
        private final int action;
        @Getter
        private final int quickness;
        @Getter
        private final int stamina;
        @Getter
        private final int mind;
        @Getter
        private final int focus;
        @Getter
        private final int willpower;

        public RacialModInfo(DataTableRow row) {
            this.maleTemplate = row.get(0).getString();
            this.femaleTemplate = row.get(1).getString();
            this.health = row.get(2).getInt();
            this.strength = row.get(3).getInt();
            this.constitution = row.get(4).getInt();
            this.action = row.get(5).getInt();
            this.quickness = row.get(6).getInt();
            this.stamina = row.get(7).getInt();
            this.mind = row.get(8).getInt();
            this.focus = row.get(9).getInt();
            this.willpower = row.get(10).getInt();
        }
    }
}
