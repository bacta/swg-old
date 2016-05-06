package com.ocdsoft.bacta.swg.shared.lot;

import com.ocdsoft.bacta.swg.shared.math.Rectangle2d;
import com.ocdsoft.bacta.swg.shared.math.Sphere;
import com.ocdsoft.bacta.swg.shared.math.Vector;
import com.ocdsoft.bacta.swg.shared.object.GameObject;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by crush on 4/23/2016.
 * <p>
 * Manages if a structure can be placed in a given location.
 */
public class LotManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(LotManager.class);

    private final float mapWidthInMeters;
    private final float chunkWidthInMeters;
    private final int width;
    private final int center;

    private final Map<GameObject, NoBuildEntry> noBuildEntryMap = new ConcurrentHashMap<>();
    private final Map<GameObject, StructureFootprintEntry> structureFootprintEntryMap = new ConcurrentHashMap<>();
    //Replace with a reference to Steerio?
    //private final SphereTree<NoBuildEntry, NoBuildEntrySphereExtentAccessor> sphereTree;

    public LotManager(float mapWidthInMeters, float chunkWidthInMeters) {
        this.mapWidthInMeters = mapWidthInMeters;
        this.chunkWidthInMeters = chunkWidthInMeters;

        this.width = (int) (mapWidthInMeters / chunkWidthInMeters);
        this.center = width / 2;
    }

    public LotType getLotType(final int x, final int z) {
        return LotType.NOTHING;
    }

    public void addNoBuildEntry(final GameObject object, final float noBuildRadius) {
        final Vector objectPosition = object.getPositionInWorld();
        final Vector noBuildPosition = new Vector(objectPosition.x, 0.f, objectPosition.z);
        final NoBuildEntry entry = new NoBuildEntry(noBuildPosition, noBuildRadius);

        //TODO: Set a reference to the quad tree, and ass this nobuild area to it.

        noBuildEntryMap.put(object, entry);

        final String objectTemplateName = object.getObjectTemplateName();
        LOGGER.debug("Added no build entry for object {} [{}] at <{}, {}>.",
                object.getNetworkId(),
                objectTemplateName != null ? object.getObjectTemplateName() : "null",
                noBuildPosition.x,
                noBuildPosition.y);
    }

    public void removeNoBuildEntry(final GameObject object) {
        noBuildEntryMap.remove(object);
    }

    public int getNumberOfNoBuildEntries() {
        return noBuildEntryMap.size();
    }

    public void addStructureFootprintEntry(final GameObject object,
                                           final StructureFootprint structureFootprint,
                                           final int x,
                                           final int z,
                                           final RotationType rotation) {

    }

    public void removeStructureFootprintEntry(final GameObject object) {
        structureFootprintEntryMap.remove(object);
    }

    public int getNumberOfStructureFootprintEntries() {
        return structureFootprintEntryMap.size();
    }

    public boolean canPlace(final StructureFootprint structureFootprint,
                            final int x,
                            final int z,
                            final RotationType rotation,
                            final float height,
                            final float forceChunkCreation /* = true */) {
        //TODO: fill out this logic.
        return false;
    }

    public boolean canPlace(final Rectangle2d rectangle) {
        //TODO: fill out this logic.
        return false;
    }

    private final static class NoBuildEntry {
        private final Vector worldPosition;
        private final float radius;

        //private final SpatialSubdivisionHandle spatialSubdivisionHandle;

        public NoBuildEntry(final Vector worldPosition, final float radius) {
            this.worldPosition = worldPosition;
            this.radius = radius;
        }

        public Sphere getSphere() {
            return null;
        }
    }

    private final static class StructureFootprintEntry {
        @Getter
        private final StructureFootprint structureFootprint;
        @Getter
        private final int x;
        @Getter
        private final int z;
        @Getter
        private final RotationType rotation;

        public StructureFootprintEntry(final StructureFootprint structureFootprint,
                                       final int x,
                                       final int z,
                                       final RotationType rotation) {
            this.structureFootprint = structureFootprint;
            this.x = x;
            this.z = z;
            this.rotation = rotation;
        }
    }
}
