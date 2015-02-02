package com.ocdsoft.bacta.swg.data.faction;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.data.SharedFileLoader;
import com.ocdsoft.bacta.swg.shared.iff.IffReader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTableIffReader;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTableRow;
import com.ocdsoft.bacta.tre.TreeFile;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 3/30/14.
 */
@Singleton
public final class FactionRank implements SharedFileLoader {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TIntObjectMap<FactionRankInfo> factionRanks = new TIntObjectHashMap<>();

    private final TreeFile treeFile;

    @Inject
    public FactionRank(TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }

    /**
     * Gets a {@link FactionRankInfo} by index.
     *
     * @param index The index to retrieve.
     * @return Returns null if index not found.
     */
    public final FactionRankInfo getFactionRankInfo(int index) {
        return factionRanks.get(index);
    }

    /**
     * Gets a {@link FactionRankInfo} by name.
     * Names are like: lance_corporal, staff_sergeant, etc.
     *
     * @param name The name to search for.
     * @return {@link FactionRankInfo} if a name corresponds
     * in the map. Otherwise, null.
     */
    public final FactionRankInfo getFactionRankInfoByName(final String name) {
        TIntObjectIterator<FactionRankInfo> iterator = factionRanks.iterator();

        while (iterator.hasNext()) {
            final FactionRankInfo info = iterator.value();

            if (info.name.equals(name))
                return info;
        }

        return null;
    }

    private void load() {
        logger.trace("Loading faction ranks.");

        final IffReader<DataTable> dataTableReader = new DataTableIffReader();
        final DataTable dataTable = dataTableReader.read(
                new ChunkReader("datatables/faction/rank.iff", treeFile.open("datatables/faction/rank.iff")));

        for (DataTableRow row : dataTable.getRows()) {
            FactionRankInfo factionRankInfo = new FactionRankInfo(row);
            factionRanks.put(factionRankInfo.getIndex(), factionRankInfo);
        }

        logger.debug(String.format("Loaded %d faction ranks.", factionRanks.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            factionRanks.clear();
            load();
        }
    }

    public final class FactionRankInfo {
        @Getter
        private final int index;
        @Getter
        private final String name;
        @Getter
        private final int cost;
        @Getter
        private final int delegateRatioFrom;
        @Getter
        private final int delegateRatioTo;

        public FactionRankInfo(final DataTableRow row) {
            this.index = row.get(0).getInt();
            this.name = row.get(1).getString();
            this.cost = row.get(2).getInt();
            this.delegateRatioFrom = row.get(3).getInt();
            this.delegateRatioTo = row.get(4).getInt();
        }
    }
}
