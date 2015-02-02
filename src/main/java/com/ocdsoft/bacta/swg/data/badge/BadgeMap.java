package com.ocdsoft.bacta.swg.data.badge;

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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by crush on 3/28/14.
 */
@Singleton
public final class BadgeMap implements SharedFileLoader {

    private final TIntObjectMap<BadgeInfo> badges = new TIntObjectHashMap<>();
    private final TreeFile treeFile;

    @Inject
    public BadgeMap(TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }

    private void load() {
        final IffReader<DataTable> dataTableReader = new DataTableIffReader();
        final DataTable dataTable = dataTableReader.read(
                new ChunkReader("datatables/badge/badge_map.iff", treeFile.open("datatables/badge/badge_map.iff")));

        for (DataTableRow row : dataTable.getRows()) {
            BadgeInfo badge = new BadgeInfo(row);
            badges.put(badge.index, badge);
        }
    }

    @Override
    public void reload() {
        synchronized (this) {
            badges.clear();
            load();
        }
    }

    /**
     * Gets the badge corresponding to the index in the map.
     *
     * @param index Index of the corresponding badge.
     * @return Badge corresponding to index if it exists. Otherwise, null.
     */
    public BadgeInfo getBadge(int index) {
        return badges.get(index);
    }


    /**
     * Gets the string key associated with a badge index. Use this method if all you need is the key.
     *
     * @param index The index of the associated badge.
     * @return String key if the index exists. Otherwise null.
     */
    public String getBadgeKeyByIndex(int index) {
        final BadgeInfo badge = badges.get(index);
        return badge == null ? null : badge.getKey();
    }

    /**
     * Gets a badge based on its string key. For example, "count_5".
     *
     * @param key The string key for the badge.
     * @return Badge if the string key exists. Otherwise null.
     */
    public BadgeInfo getBadge(String key) {
        final TIntObjectIterator<BadgeInfo> iterator = badges.iterator();

        while (iterator.hasNext()) {
            final BadgeInfo badge = iterator.value();

            if (badge.getKey().equals(key))
                return badge;
        }

        return null;
    }

    /**
     * Returns all badges in the map that match a specified category.
     *
     * @param category A category number.
     * @return Collection of badges matching the category. Collection is an {@link java.util.ArrayList}.
     * If no badges matched the category, then an empty list is returned.
     */
    public Collection<BadgeInfo> getBadgesByCategory(int category) {
        final TIntObjectIterator<BadgeInfo> iterator = badges.iterator();
        final Collection<BadgeInfo> collection = new ArrayList<BadgeInfo>();

        while (iterator.hasNext()) {
            final BadgeInfo badge = iterator.value();

            if (badge.getCategory() == category)
                collection.add(badge);
        }

        return collection;
    }

    public final class BadgeInfo {
        @Getter
        private final int index;
        @Getter
        private final String key;
        @Getter
        private final String music;
        @Getter
        private final int category;
        @Getter
        private final int show;
        @Getter
        private final String type;

        public BadgeInfo(DataTableRow row) {
            this.index = row.get(0).getInt();
            this.key = row.get(1).getString();
            this.music = row.get(2).getString();
            this.category = row.get(3).getInt();
            this.show = row.get(4).getInt();
            this.type = row.get(5).getString();
        }
    }
}
