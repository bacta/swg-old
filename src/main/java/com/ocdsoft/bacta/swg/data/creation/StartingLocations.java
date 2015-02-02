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
public class StartingLocations implements SharedFileLoader {
    private final Map<String, StartingLocationInfo> startingLocations = new HashMap<>();

    private final TreeFile treeFile;

    @Inject
    public StartingLocations(TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }

    /**
     * Gets a {@link StartingLocationInfo} object
     * specified by a string key representing the location.
     *
     * @param location The key that represents the location. i.e. mos_eisley
     * @return {@link StartingLocationInfo} object
     * if key exists. Otherwise, null.
     */
    public StartingLocationInfo getStartingLocationInfo(final String location) {
        return startingLocations.get(location);
    }

    private void load() {
        final IffReader<DataTable> dataTableReader = new DataTableIffReader();
        final DataTable dataTable = dataTableReader.read(
                new ChunkReader("datatables/creation/starting_locations.iff", treeFile.open("datatables/creation/starting_locations.iff")));

        for (DataTableRow row : dataTable.getRows()) {
            StartingLocationInfo locationInfo = new StartingLocationInfo(row);
            startingLocations.put(locationInfo.location, locationInfo);
        }
    }

    public void reload() {
        synchronized (this) {
            startingLocations.clear();
            load();
        }
    }

    public static final class StartingLocationInfo {
        @Getter
        private final String location;
        @Getter
        private final String planet;
        @Getter
        private final float x;
        @Getter
        private final float y;
        @Getter
        private final float z;
        @Getter
        private final String cellId;
        @Getter
        private final String image;
        @Getter
        private final String description;
        @Getter
        private final float radius;
        @Getter
        private final float heading;

        public StartingLocationInfo(DataTableRow row) {
            location = row.get(0).getString();
            planet = row.get(1).getString();
            x = row.get(2).getFloat();
            y = row.get(3).getFloat();
            z = row.get(4).getFloat();
            cellId = row.get(5).getString();
            image = row.get(6).getString();
            description = row.get(7).getString();
            radius = row.get(8).getFloat();
            heading = row.get(9).getFloat();
        }
    }
}
