package com.ocdsoft.bacta.swg.shared.container;

import com.ocdsoft.bacta.swg.shared.object.GameObject;
import gnu.trove.list.TIntList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;

import java.util.List;

/**
 * Created by crush on 8/26/2014.
 */
public class SlottedContainer extends Container {
    public static int getClassPropertyId() {
        return 0x7ED71F2E;
    }

    /**
     * This is the map of slot ids to contents. It is initialized with -1 values.
     * Anything greater than or equal to 0 indicates a position in the contents list.
     */
    private final TIntIntMap slotMap = new TIntIntHashMap();

    public SlottedContainer(final GameObject owner, final TIntList validSlots) {
        super(getClassPropertyId(), owner);

        //Initialize the slot map with "empty" values.
        for (int i = 0, size = validSlots.size(); i < size; ++i)
            slotMap.put(i, -1);
    }

    @Override
    public boolean isContentItemObservedWith(final GameObject item) {
        //-- Rule 1: if base container claims that the item is visible with the container,
        //   we stick with that.  This prevents us from changing any existing behavior at
        //   the time this code is written.
        final boolean observedWithBaseContainer = super.isContentItemObservedWith(item);

        if (observedWithBaseContainer)
            return true;

        //-- Rule 2: if the item is in this container, check if any of the current arrangement's
        //   slots have the observeWithParent attribute set.  If so, return true, if not, return false.
        final ContainedByProperty containedByProperty = item.getContainedByProperty();

        if (containedByProperty == null)
            return false;

        final SlottedContainmentProperty slottedContainmentProperty = item.getProperty(SlottedContainmentProperty.getClassPropertyId());

        if (slottedContainmentProperty == null)
            return false;

        int arrangementIndex = slottedContainmentProperty.getCurrentArrangement();

        // Note: when checking if item is in container, we must also check
        // that contained item's arrangement is set to a valid arrangement.
        // This function can be called during container transfers prior to
        // the arrangementIndex being set.  When this occurs, handle this
        // case as if the item is not in the container because it's not really
        // there in its entirety yet.

        final GameObject containedByObject = containedByProperty.getContainedBy();
        final boolean isInThisContainer = (containedByObject == getOwner()) && (arrangementIndex >= 0);

        if (isInThisContainer) {
            final TIntList slots = slottedContainmentProperty.getSlotArrangement(arrangementIndex);

            for (int i = 0, size = slots.size(); i < size; ++i) {
                final int slotId = slots.get(i);

                //TODO: How can we get slotidmanager here?!?
                //final boolean observeWithParent = slotIdManager.getSlotObserveWithParent(slotId);
                final boolean observeWithParent = false;

                if (observeWithParent)
                    return true;
            }

            return false;
        }

        //-- Rule 3: if the item is not in this container, determine which arrangement it would
        //   use if it went in this slot.  If no arrangement is valid, return false.  If an arrangement
        //   is valid, check each slot in the arrangement.  If any slot has observeWithParent set true,
        //   return true; otherwise, return false.

        arrangementIndex = -1;
        final boolean result = getFirstUnoccupiedArrangement(item, arrangementIndex);

        if (!result)
            return false;

        final TIntList slots = slottedContainmentProperty.getSlotArrangement(arrangementIndex);

        for (int i = 0, size = slots.size(); i < size; ++i) {
            final int slotId = slots.get(i);

            //TODO: How can we get slotidmanager here?!?
            //final boolean observeWithParent = slotIdManager.getSlotObserveWithParent(slotId);
            final boolean observeWithParent = false;

            if (observeWithParent)
                return true;
        }

        return false;
    }

    @Override
    public boolean isContentItemExposedWith(final GameObject item) {
        return false;
    }

    @Override
    public boolean canContentsBeObservedWith() {
        return false;
    }

    public boolean add(final GameObject item, final int arrangementIndex) throws ContainerException {
        return false;
    }

    public boolean addBySlotId(final GameObject item, final int slotId) {
        return false;
    }

    public GameObject getObjectInSlot(final int slotId) throws ContainerException {
        return null;
    }

    public List<GameObject> getObjectsForCombatBone(final int bone) {
        return null;
    }

    public boolean getFirstUnoccupiedArrangement(final GameObject item, final int arrangementIndex) throws ContainerException {
        //TODO: How to return arrangement index and boolean!?
        return false;
    }

    public TIntList getValidArrangements(final GameObject item, boolean returnOnFirst, boolean unoccupiedArrangementsOnly)
            throws ContainerException {
        return null;
    }

    public boolean hasSlot(final int slotId) {
        return false;
    }

    public boolean isSlotEmpty(final int slotId) throws ContainerException {
        return false;
    }

    @Override
    public boolean mayAdd(final GameObject item) throws ContainerException {
        return false;
    }

    public boolean mayAdd(final GameObject item, final int arrangementIndex) throws ContainerException {
        return false;
    }

    public boolean mayAddBySlotId(final GameObject item, final int slotId) throws ContainerException {
        return false;
    }

    @Override
    public boolean remove(final GameObject item) throws ContainerException {
        return false;
    }

    @Override
    public boolean remove(final int position) throws ContainerException {
        return false;
    }

    public void removeItemFromSlotOnly(final GameObject item) {

    }

    public int findFirstSlotIdForObject(final long id) {
        return 0;
    }

    @Override
    public int depersistContents(final GameObject item) {
        return 0;
    }

    public void depersistSlotContents(final GameObject item, int arrangement) {

    }

    @Override
    public boolean internalItemRemoved(final GameObject item) {
        return false;
    }

    public void updateArrangement(final GameObject item, final int oldArrangement, final int newArrangement) {

    }

    private int find(final int slotId) {
        return 0;
    }

    private boolean internalCheckSlottedAdd(final GameObject item, final int arrangementIndex) throws ContainerException {
        return false;
    }

    private boolean internalDoSlottedAdd(final GameObject item, final int position, final int arrangementIndex) {
        return false;
    }

    private boolean internalRemove(final GameObject item) {
        return internalRemove(item, -1);
    }

    private boolean internalRemove(final GameObject item, final int overrideArrangement) {
        return false;
    }
}
