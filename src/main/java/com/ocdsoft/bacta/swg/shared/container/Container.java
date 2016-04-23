package com.ocdsoft.bacta.swg.shared.container;

import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.engine.object.NetworkObject;
import com.ocdsoft.bacta.swg.shared.object.GameObject;
import com.ocdsoft.bacta.swg.shared.property.Property;
import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by crush on 8/26/2014.
 */
public abstract class Container extends Property {
    protected static final Logger LOGGER = LoggerFactory.getLogger(Container.class);

    private static final int CONTAINER_MAX_DEPTH = 9; //TODO: This is supposed to be some kind of configuration value.
    private static final boolean CONTAINER_LOOP_CHECKING = true;

    public static int getClassPropertyId() {
        return 0x55DC5726;
    }

    private final List<GameObject> contents = new ArrayList<>();
    private boolean changed;

    public Container(final int propertyId, final GameObject owner) {
        super(propertyId, owner);
    }

    @Override
    public void addToWorld() {
        contents.stream()
                .filter(obj -> obj != null && isContentItemExposedWith(obj))
                .forEach(GameObject::addToWorld);
    }

    @Override
    public void removeFromWorld() {
        contents.stream()
                .filter(obj -> obj != null && isContentItemExposedWith(obj))
                .forEach(GameObject::removeFromWorld);
    }

    @Override
    public void conclude() {

    }

    /**
     * DO NOT CALL DIRECTLY. Public out of necessity.
     */
    public int depersistContents(final GameObject item) {
        return insertNewItem(item);
    }

    /**
     * DO NOT CALL DIRECTLY. Public out of necessity.
     */
    public boolean internalItemRemoved(final GameObject item) {
        return internalRemoveItem(item);
    }

    public Iterator<GameObject> iterator() {
        return contents.iterator();
    }

    public int getNumberOfItems() {
        return contents.size();
    }

    public boolean mayAdd(final GameObject item) throws ContainerException {
        if (item == getOwner())
            throw new ContainerException(ContainerErrorCode.ADD_SELF);

        if (CONTAINER_LOOP_CHECKING) {
            //Check that we don't introduce an infinite loop.
            //First check for container property. Only containers can introduce a loop.
            final Container containerToCheck = item.getContainerProperty();

            if (containerToCheck != null) {
                ContainedByProperty iteratedContainedBy = getOwner().getContainedByProperty();

                if (iteratedContainedBy != null) {
                    long iteratedNetworkId = iteratedContainedBy.getContainedByNetworkId();

                    if (iteratedNetworkId != NetworkObject.INVALID) {
                        final TLongList checkList = new TLongArrayList(10);
                        checkList.add(getOwner().getNetworkId());

                        for (int containerDepth = 0; iteratedNetworkId != NetworkObject.INVALID; ++containerDepth) {
                            if (containerDepth > CONTAINER_MAX_DEPTH) {
                                LOGGER.warn("Too deep a container hierarchy.");
                                throw new ContainerException(ContainerErrorCode.TOO_DEEP);
                            }

                            checkList.add(iteratedNetworkId);

                            final GameObject obj = iteratedContainedBy.getContainedBy();
                            iteratedNetworkId = NetworkObject.INVALID;

                            if (obj != null) {
                                iteratedContainedBy = obj.getContainedByProperty();

                                if (iteratedContainedBy != null)
                                    iteratedNetworkId = iteratedContainedBy.getContainedByNetworkId();
                            }
                        }

                        if (checkList.contains(item.getNetworkId())) {
                            LOGGER.warn("Adding item {} to {} would have introduced a container loop.",
                                    item.getNetworkId(), getOwner().getNetworkId());
                            throw new ContainerException(ContainerErrorCode.ALREADY_IN);
                        }
                    }
                }
            }
        }

        if (checkDepth(this) > CONTAINER_MAX_DEPTH)
            throw new ContainerException(ContainerErrorCode.TOO_DEEP);

        //Why are we doing this again?
        if (item == getOwner())
            throw new ContainerException(ContainerErrorCode.ADD_SELF);

        return true;
    }

    public boolean remove(final GameObject item) throws ContainerException {
        boolean returnValue;

        ContainedByProperty property = item.getContainedByProperty();
        Preconditions.checkNotNull(property, "Cannot remove an item that has no containedby property.");

        if (property.getContainedBy() != getOwner()) {
            LOGGER.warn("Cannot remove an item {} from container {} whose containedBy says it isn't in this container.",
                    item.getNetworkId(), getOwner().getNetworkId());
            throw new ContainerException(ContainerErrorCode.NOT_FOUND);
        }

        returnValue = internalRemoveItem(item);

        if (returnValue)
            property.setContainedBy(null);

        return returnValue;
    }

    public boolean remove(final int position) throws ContainerException {
        final GameObject obj = contents.get(position);

        if (obj == null)
            throw new ContainerException(ContainerErrorCode.UNKNOWN);

        ContainedByProperty property = obj.getContainedByProperty();
        Preconditions.checkNotNull(property, "Cannot remove an itemt hat has no containedby property.");

        if (property.getContainedBy() != getOwner()) {
            LOGGER.warn("Cannot remove an item {} from container {} whose containedBy says it isn't in this container.",
                    obj.getNetworkId(), getOwner().getNetworkId());
            throw new ContainerException(ContainerErrorCode.NOT_FOUND);
        }

        return internalRemoveItem(obj);
    }

    /**
     * Returns true if the item should be in the world if its container is
     * in the world.
     * <p>
     * This function can be called on an item in the container or an item
     * that the caller already knows it will be putting in the container.
     * <p>
     * If this function returns true, it indicates that the contained item
     * should be added to the world whenever the container is added to the world.
     * <p>
     * Derived container class note: derived containers should chain up to
     * this function first.  If it returns true, you're done because that
     * implies the container blanket-exposes all contents.  If the function
     * returns false, then do any container-specific checks that might
     * allow this particular object to be exposed.  Slotted containers work
     * this way to allow slot-by-slot specification of exposing
     * characteristics.
     *
     * @param item the item that is contained or will soon be contained by
     *             the container.
     * @return true if the item should be exposed with the same observation
     * and world characterstics as the container.
     */
    public boolean isContentItemExposedWith(final GameObject item) {
        return false;
    }

    /**
     * Returns true if the item is or would be observed with its container.
     * <p>
     * This function can be called on an item in the container or an item
     * that the caller already knows it will be putting in the container.
     * <p>
     * If this function returns true, it indicates that the contained item
     * should be added to the world and should be observed whenever the same
     * happens to the container. In other words, the item within the container
     * has the same visibility as the container itself. Mounts make use of this
     * to expose the rider whenever the mount is exposed.
     * <p>
     * Derived container class note: derived containers should chain up to
     * this function first. If it returns true, you're done because that
     * implies the container blanket-exposes all contents. If the function
     * returns false, then do any container-specific checks that might
     * allow this particular object to be exposed. Slotted containers work
     * this way to allow slot-by-slot specification of observation
     * characteristics.
     *
     * @param item the item that is contained or will soon be contained by
     *             the container.
     * @return true if the item should be exposed with the same observation
     * and world characteristics as the container.
     */
    public boolean isContentItemObservedWith(final GameObject item) {
        return false;
    }

    public boolean canContentsBeObservedWith() {
        return false;
    }

    public boolean hasChanged() {
        return changed;
    }


    protected int addToContents(final GameObject item) throws ContainerException {
        if (!mayAdd(item))
            return -1;

        final ContainedByProperty containedItem = item.getContainedByProperty();

        Preconditions.checkState(containedItem != null, "Cannot add an item with no containedByProperty to a container.");

        final GameObject containedBy = containedItem.getContainedBy();

        if (containedBy != null && containedBy != getOwner()) {
            LOGGER.warn("Cannot add an item [{}] to a container [{}] with it is already contained!",
                    item.getNetworkId(), getOwner().getNetworkId());
            throw new ContainerException(ContainerErrorCode.ALREADY_IN);
        }

        if (CONTAINER_LOOP_CHECKING) {
            //Check that we don't introduce an infinite loop.
            //First check for container property. Only containers can introduce a loop.
            final Container containerToCheck = item.getContainerProperty();

            if (containerToCheck != null) {
                ContainedByProperty iteratedContainedBy = getOwner().getContainedByProperty();

                if (iteratedContainedBy != null) {
                    long iteratedNetworkId = iteratedContainedBy.getContainedByNetworkId();

                    if (iteratedNetworkId != NetworkObject.INVALID) {
                        final TLongList checkList = new TLongArrayList(10);
                        checkList.add(getOwner().getNetworkId());

                        for (int containerDepth = 0; iteratedNetworkId != NetworkObject.INVALID; ++containerDepth) {
                            if (containerDepth > CONTAINER_MAX_DEPTH) {
                                LOGGER.warn("Too deep a container hierarchy.");
                                throw new ContainerException(ContainerErrorCode.TOO_DEEP);
                            }

                            checkList.add(iteratedNetworkId);

                            final GameObject obj = iteratedContainedBy.getContainedBy();
                            iteratedNetworkId = NetworkObject.INVALID;

                            if (obj != null) {
                                iteratedContainedBy = obj.getContainedByProperty();

                                if (iteratedContainedBy != null)
                                    iteratedNetworkId = iteratedContainedBy.getContainedByNetworkId();
                            }
                        }

                        if (checkList.contains(item.getNetworkId())) {
                            LOGGER.warn("Adding item {} to {} would have introduced a container loop.",
                                    item.getNetworkId(), getOwner().getNetworkId());
                            throw new ContainerException(ContainerErrorCode.ALREADY_IN);
                        }
                    }
                }
            }
        }

        if (checkDepth(this) > CONTAINER_MAX_DEPTH)
            throw new ContainerException(ContainerErrorCode.TOO_DEEP);

        if (isItemContained(item.getNetworkId())) {
            LOGGER.warn("Cannot add an item {} to a container {} when it is already in it! This shouldn't happen because the item's contained by property says it is not in this container, but it is in the container's internal list.",
                    item.getNetworkId(), getOwner().getNetworkId());
            throw new ContainerException(ContainerErrorCode.ALREADY_IN);
        }

        containedItem.setContainedBy(getOwner());

        final int returnValue = insertNewItem(item);

        if (returnValue == -1)
            throw new ContainerException(ContainerErrorCode.UNKNOWN);

        return returnValue;
    }

    protected void clearChanged() {
        changed = false;
    }

    protected int find(final long item) throws ContainerException {
        for (int i = 0, size = contents.size(); i < size; ++i) {
            final GameObject obj = contents.get(i);

            if (obj != null && obj.getNetworkId() == item)
                return i;
        }

        throw new ContainerException(ContainerErrorCode.NOT_FOUND);
    }

    protected GameObject getContents(final int position) {
        return contents.get(position);
    }

    private boolean internalRemoveItem(final GameObject item) {
        return contents.remove(item);
    }

    private int insertNewItem(final GameObject item) {
        if (isItemContained(item.getNetworkId())) {
            LOGGER.warn("Cannot add item [{}] to container [{}] when it is already in it!",
                    item.getNetworkId(), getOwner().getNetworkId());
            throw new ContainerException(ContainerErrorCode.ALREADY_IN);
        }

        if (item == getOwner()) {
            LOGGER.warn("Tried to add an item [{}] to itself!", item.getNetworkId());
            throw new ContainerException(ContainerErrorCode.ADD_SELF);
        }

        contents.add(item);

        return contents.size();
    }

    private boolean isItemContained(final long item) throws ContainerException {
        return find(item) != -1;
    }

    public static int checkDepth(final Container container) {
        int count = 0;
        ContainedByProperty parentContainedByProperty = container.getOwner().getContainedByProperty();

        if (parentContainedByProperty == null)
            return count;

        GameObject parentObject = parentContainedByProperty.getContainedBy();

        if (parentObject == null)
            return count;

        while (parentObject != null) {
            ++count;
            parentContainedByProperty = parentObject.getContainedByProperty();

            if (parentContainedByProperty == null)
                return count;

            parentObject = parentContainedByProperty.getContainedBy();
        }

        return count;
    }
}
