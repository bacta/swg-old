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
public final class AttributeLimits implements SharedFileLoader {
    private final Map<String, AttributeLimitInfo> attributeLimits = new HashMap<>();
    private final TreeFile treeFile;

    @Inject
    public AttributeLimits(TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }
    /**
     * Gets an {@link AttributeLimitInfo} object for
     * the specified player template. <i>The player template should only be the file name, and not the fullpath.</i>
     * <p/>
     * i.e. bothan_female.
     *
     * @param playerTemplate The player template to use as a key. This should be the filename, not the full path. i.e. bothan_female
     * @return Returns an {@link AttributeLimitInfo} object
     * if a record corresponding to the key is found. Otherwise, null.
     */
    public AttributeLimitInfo getAttributeLimits(final String playerTemplate) {
        return attributeLimits.get(playerTemplate);
    }

    private void load() {
        final IffReader<DataTable> dataTableReader = new DataTableIffReader();
        final DataTable dataTable = dataTableReader.read(new ChunkReader("datatables/creation/attribute_limits.iff", treeFile.open("datatables/creation/attribute_limits.iff")));

        for (DataTableRow row : dataTable.getRows()) {
            AttributeLimitInfo limitInfo = new AttributeLimitInfo(row);
            attributeLimits.put(limitInfo.maleTemplate, limitInfo);
            attributeLimits.put(limitInfo.femaleTemplate, limitInfo);
        }
    }

    @Override
    public void reload() {
        synchronized (this) {
            attributeLimits.clear();
            load();
        }
    }

    public static final class AttributeLimitInfo {
        @Getter
        private final String maleTemplate;
        @Getter
        private final String femaleTemplate;
        @Getter
        private final int minHealth;
        @Getter
        private final int maxHealth;
        @Getter
        private final int minStrength;
        @Getter
        private final int maxStrength;
        @Getter
        private final int minConstitution;
        @Getter
        private final int maxConstitution;
        @Getter
        private final int minAction;
        @Getter
        private final int maxAction;
        @Getter
        private final int minQuickness;
        @Getter
        private final int maxQuickness;
        @Getter
        private final int minStamina;
        @Getter
        private final int maxStamina;
        @Getter
        private final int minMind;
        @Getter
        private final int maxMind;
        @Getter
        private final int minFocus;
        @Getter
        private final int maxFocus;
        @Getter
        private final int minWillpower;
        @Getter
        private final int maxWillpower;
        @Getter
        private final int total;

        public AttributeLimitInfo(DataTableRow row) {
            this.maleTemplate = row.get(0).getString();
            this.femaleTemplate = row.get(1).getString();
            this.minHealth = row.get(2).getInt();
            this.maxHealth = row.get(3).getInt();
            this.minStrength = row.get(4).getInt();
            this.maxStrength = row.get(5).getInt();
            this.minConstitution = row.get(6).getInt();
            this.maxConstitution = row.get(7).getInt();
            this.minAction = row.get(8).getInt();
            this.maxAction = row.get(9).getInt();
            this.minQuickness = row.get(10).getInt();
            this.maxQuickness = row.get(11).getInt();
            this.minStamina = row.get(12).getInt();
            this.maxStamina = row.get(13).getInt();
            this.minMind = row.get(14).getInt();
            this.maxMind = row.get(15).getInt();
            this.minFocus = row.get(16).getInt();
            this.maxFocus = row.get(17).getInt();
            this.minWillpower = row.get(18).getInt();
            this.maxWillpower = row.get(19).getInt();
            this.total = row.get(20).getInt();
        }
    }
}
