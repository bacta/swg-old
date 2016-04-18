package com.ocdsoft.bacta.swg.shared.slot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.tre.TreeFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by crush on 8/25/2014.
 */
@Singleton
public class SlotIdManager {
    public static final int InvalidSlot = -1;

    private static final Comparator<Slot> lessSlotNameComparator = new Comparator<Slot>() {
        @Override
        public int compare(Slot o1, Slot o2) {
            return o1.slotName.compareTo(o2.slotName);
        }
    };

    private final static Logger LOGGER = LoggerFactory.getLogger(SlotIdManager.class);

    private final List<Slot> slots = new ArrayList<>();
    private final List<String> slotsThatHoldAnything = new ArrayList<>();
    //private final TIntObjectMap<Slot> slotsSortedByBone; //TODO: the value should be a pair of int, Slot

    private final TreeFile treeFile;

    @Inject
    public SlotIdManager(TreeFile treeFile) {
        this.treeFile = treeFile;

        load("abstract/slot/slot_definition/slot_definitions.iff");
    }

    public static final boolean isInvalidSlot(int slotId) { return slotId == InvalidSlot; }

    public void load(final String definitionsFile) {
        LOGGER.trace("Loading slots from slot definitions file <{}>.", definitionsFile);

        if (!treeFile.exists(definitionsFile)) {
            LOGGER.warn("Cannot load slot definitions because the file <{}> could not be found.", definitionsFile);
        } else {
//            ChunkReader chunkReader = new ChunkReader(definitionsFile, treeFile.open(definitionsFile));
//            chunkReader.nextChunk(); //0006
//            chunkReader.nextChunk(); //DATA
//
//            while (chunkReader.hasMoreChunks()) {
//                Slot slot = new Slot();
//                slot.slotName = chunkReader.readNullTerminatedAscii();
//
//                logger.trace("Loading slot description for slot {}.", slot.slotName);
//
//                boolean isSlotThatHoldsAnything = chunkReader.readBoolean();
//
//                slot.isPlayerModifiable = chunkReader.readBoolean();
//                slot.isAppearanceRelated = chunkReader.readBoolean();
//                slot.hardpointName = chunkReader.readNullTerminatedAscii();
//                slot.combatBone = chunkReader.readShort();
//                slot.observeWithParent = chunkReader.readBoolean();
//                slot.exposeWithParent = chunkReader.readBoolean();
//
//                slots.add(slot);
//
//                if (isSlotThatHoldsAnything)
//                    slotsThatHoldAnything.add(slot.slotName);
//            }
//
//            chunkReader.closeChunk();
//            chunkReader.closeChunk();

            Collections.sort(slots, lessSlotNameComparator);
        }
    }

    public int findSlotId(final String slotName) {
        return Collections.binarySearch(slots, slotName);
    }

    public int[] findSlotIdsForCombatBone(int combatBone) {
        return null; //TODO: bonesssss....
    }

    public int getSlotCount() {
        return slots.size();
    }

    public int getSlotIdByIndex(int index) {
        return index; //This is dumb. In the client they return SlotId object...but SlotId object is just an int...
    }

    public String getSlotName(int slotId) {
        Slot slot = slots.get(slotId);
        return slot != null ? slot.slotName : "";
    }

    public List<String> getSlotsThatHoldAnything() {
        return slotsThatHoldAnything;
    }

    public boolean isSlotAppearanceRelated(int slotId) {
        Slot slot = slots.get(slotId);
        return slot != null ? slot.isAppearanceRelated : false;
    }

    public boolean isSlotPlayerModifiable(int slotId) {
        Slot slot = slots.get(slotId);
        return slot != null ? slot.isPlayerModifiable : false;
    }

    private final static class Slot implements Comparable<String> {
        private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

        private String slotName;
        private boolean isPlayerModifiable;
        private boolean isAppearanceRelated;
        private String hardpointName;
        private short combatBone;
        private boolean observeWithParent;
        private boolean exposeWithParent;

        @Override
        public int compareTo(String o) {
            return slotName.compareTo(o);
        }
    }
}
