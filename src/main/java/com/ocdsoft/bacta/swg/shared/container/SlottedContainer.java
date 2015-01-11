package com.ocdsoft.bacta.swg.shared.container;

import com.ocdsoft.bacta.swg.server.game.object.SceneObject;
import gnu.trove.list.TIntList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Collections;
import java.util.Iterator;

/**
 * Created by crush on 8/26/2014.
 */
public class SlottedContainer extends Container {
    private final TIntObjectMap<SceneObject> slotMap;

    public SlottedContainer(SceneObject owner, TIntList validSlots) {
        super(owner);

        slotMap = new TIntObjectHashMap<>(validSlots.size());

        for (int i = 0; i < validSlots.size(); i++)
            slotMap.put(validSlots.get(i), null); //Fill the map with all the slots.
    }

    @Override
    public final boolean isSlottedContainer() {
        return true;
    }

    @Override
    public Iterator<SceneObject> iterator() {
        return Collections.unmodifiableCollection(slotMap.valueCollection()).iterator();
    }

    /**
     * Checks if a specific slot does not have an object currently slotted to it.
     * @param slotId The slot to check.
     * @return Returns false if the slotted container does not have the specified slot, or if the slot is occupied.
     * Otherwise, true.
     */
    public boolean isSlotEmpty(int slotId) {
        return slotMap.containsKey(slotId) && slotMap.get(slotId) == null;
    }

    /**
     * Checks if the specific slot exists on this slotted object.
     * @param slotId The slot to check.
     * @return Returns false if the slotted container does not have the specified slot. Otherwise, true.
     */
    public boolean containsSlot(int slotId) {
        return slotMap.containsKey(slotId);
    }

    public SceneObject getObjectInSlot(int slotId) {
        return slotMap.get(slotId);
    }

    /**
     * Searches the SlottedContainer for the item, and returns the slot id of the first slot encountered
     * which contains this item. It is possible for an item to occupy multiple slots.
     *
     * @param item The item for which to search.
     * @return
     */
    public int findFirstSlotIdForObject(SceneObject item) {
        for (int slotId : slotMap.keys()) {
            SceneObject object = slotMap.get(slotId);

            if (object == item)
                return slotId;
        }

        return -1;
    }

    /**
     * Places an item into the SlottedContainer at the specific slot id. It is possible for the same item to
     * occupy multiple slots.
     *
     * @param slotId The SlotId, determined by {@link com.ocdsoft.bacta.swg.shared.slot.SlotIdManager}, for the slot.
     *               An items available slots comes from it's {@link com.ocdsoft.bacta.swg.shared.slot.SlotDescriptor}.
     * @param item   The item which is being added to the container.
     * @return Returns false if the slotId is not a slot on this container. Otherwise, true.
     */
    public boolean add(int slotId, SceneObject item) {
        if (!slotMap.containsKey(slotId))
            return false;

        slotMap.put(slotId, item);

        return true;
    }

    @Override
    public void remove(SceneObject item) {
        for (int slotId : slotMap.keys()) {
            SceneObject obj = slotMap.get(slotId);

            if (obj == item)
                slotMap.remove(slotId);
        }
    }

    @Override
    public boolean contains(SceneObject item) {
        return slotMap.containsValue(item);
    }
}
