package com.ocdsoft.bacta.swg.shared.container;

import com.ocdsoft.bacta.swg.shared.object.GameObject;
import lombok.Getter;

import java.util.Iterator;

/**
 * Created by crush on 8/26/2014.
 * <p>
 * A volume container is a container that holds items, limited to a maximum volume. It represents things like chests,
 * bags, etc.
 */
public class VolumeContainer extends Container {
    public static final int NO_VOLUME_LIMIT = -1;

    public static int getClassPropertyId() {
        return 0xA5193F23;
    }

    @Getter
    private int currentVolume;
    @Getter
    private int totalVolume;

    public VolumeContainer(final GameObject owner, int totalVolume) {
        super(getClassPropertyId(), owner);

        this.totalVolume = totalVolume;
    }

    public boolean add(final GameObject item) throws ContainerException {
        return add(item, false);
    }

    public boolean add(final GameObject item, boolean allowOverloaded) throws ContainerException {
        final int oldVolume = currentVolume;

        final VolumeContainmentProperty property = item.getProperty(getClassPropertyId());

        if (property == null) {
            LOGGER.warn("Cannot add an item to a volume container without a containment property.");
            throw new ContainerException(ContainerErrorCode.UNKNOWN);
        }

        if (!allowOverloaded && !checkVolume(property))
            throw new ContainerException(ContainerErrorCode.FULL);

        if (addToContents(item) == -1)
            return false;

        insertNewItem(item, property);

        if (currentVolume != oldVolume) {
            final VolumeContainer parent = getVolumeContainerParent(this);

            if (parent != null)
                parent.childVolumeChanged(currentVolume - oldVolume, true);
        }

        return true;
    }

    public boolean checkVolume(int addedVolume) {
        if (totalVolume == NO_VOLUME_LIMIT)
            return true;

        boolean returnValue = currentVolume + addedVolume <= totalVolume;

        if (returnValue) {
            final VolumeContainer volumeContainer = getVolumeContainerParent(this);

            if (volumeContainer != null)
                returnValue = volumeContainer.checkVolume(addedVolume);
        }

        return returnValue;
    }

    private boolean checkVolume(final VolumeContainmentProperty item) {
        return checkVolume(item.getVolume());
    }

    public int getTotalVolumeLimitedByParents() {
        //Container is unlimited
        if (totalVolume <= 0)
            return totalVolume;

        int remainingVolume = totalVolume - currentVolume;

        GameObject parent = getOwner();

        while (parent != null) {
            final ContainedByProperty containedByProperty = parent.getContainedByProperty();
            parent = containedByProperty.getContainedBy();

            if (parent != null) {
                final VolumeContainer volumeContainer = parent.getVolumeContainerProperty();

                if (volumeContainer == null)
                    break;

                final int parentTotalVolume = volumeContainer.getTotalVolume();
                final int parentCurrentVolume = volumeContainer.getCurrentVolume();

                if (parentTotalVolume <= 0)
                    break;

                final int remainingInParent = Math.max(0, parentTotalVolume - parentCurrentVolume);
                remainingVolume = Math.min(remainingVolume, remainingInParent);
            }
        }

        return currentVolume + remainingVolume;
    }

    @Override
    public boolean mayAdd(final GameObject item) throws ContainerException {
        if (item == getOwner())
            throw new ContainerException(ContainerErrorCode.ADD_SELF);

        final VolumeContainmentProperty property = item.getProperty(getClassPropertyId());

        if (property == null)
            throw new ContainerException(ContainerErrorCode.UNKNOWN);

        final VolumeContainer volumeContainer = item.getVolumeContainerProperty();

        if (volumeContainer != null) {
            if (totalVolume != NO_VOLUME_LIMIT && volumeContainer.getTotalVolume() >= getTotalVolume())
                throw new ContainerException(ContainerErrorCode.TOO_LARGE);
        }

        if (!checkVolume(property))
            throw new ContainerException(ContainerErrorCode.FULL);

        return super.mayAdd(item);
    }

    public int recalculateVolume() {
        int volume = 0;

        final Iterator<GameObject> iterator = iterator();

        while (iterator.hasNext()) {
            final GameObject obj = iterator.next();

            if (obj == null) {
                LOGGER.warn("Container with non-existant object {}", obj.getNetworkId());
            } else {
                final VolumeContainmentProperty property = obj.getProperty(getClassPropertyId());

                if (property == null) {
                    LOGGER.warn("We have an item in a volume container with no property {}", obj.getNetworkId());
                } else if (totalVolume != NO_VOLUME_LIMIT) {
                    volume += property.getVolume();
                }
            }
        }

        this.currentVolume = volume;

        final VolumeContainer volumeContainer = getVolumeContainerParent(this);

        if (volumeContainer != null)
            volumeContainer.recalculateVolume();

        if (totalVolume > 0 && currentVolume > totalVolume)
            LOGGER.warn("Recalculate Volume ended up being greater than our capacity.");

        return currentVolume;
    }

    @Override
    public boolean remove(final GameObject item) throws ContainerException {
        final int oldVolume = currentVolume;

        final VolumeContainmentProperty property = item.getProperty(getClassPropertyId());

        if (property == null) {
            LOGGER.warn("Cannot remove an item from a volume container without a containment property.");
            throw new ContainerException(ContainerErrorCode.UNKNOWN);
        }

        boolean returnValue = super.remove(item);

        if (!returnValue)
            return false;

        if (!internalRemove(item)) {
            LOGGER.warn("tried to remove item {} from volume container in internal routine but failed",
                    item.getNetworkId());
            throw new ContainerException(ContainerErrorCode.UNKNOWN);
        }

        if (currentVolume != oldVolume) {
            final VolumeContainer parent = getVolumeContainerParent(this);

            if (parent != null)
                parent.childVolumeChanged(currentVolume - oldVolume, true);
        }

        return true;
    }

    @Override
    public boolean remove(final int position) throws ContainerException {
        final GameObject obj = getContents(position);

        if (obj != null) {
            return remove(obj);
        } else {
            throw new ContainerException(ContainerErrorCode.UNKNOWN);
        }
    }

    /**
     * DO NOT CALL DIRECTLY. Public out of necessity.
     */
    @Override
    public int depersistContents(final GameObject item) {
        final int oldVolume = currentVolume;

        insertNewItem(item);

        if (currentVolume != oldVolume) {
            // if we are contained in another volume container, we
            // need up update the count in the parent container;
            // however, if we are here because of a baseline, we don't
            // need to update the parent container because when the
            // parent adds us, it will update the count at that time;
            // if we are here because of a delta, then we need to
            // update the parent; if we are here because of a baseline,
            // then we are not yet in the parent container, even though
            // we have a pointer to the parent container
            final VolumeContainer parent = getVolumeContainerParent(this);

            if (parent != null) {
                boolean containedInParent = false;
                final Iterator<GameObject> parentIterator = parent.iterator();

                while (parentIterator.hasNext()) {
                    final GameObject obj = parentIterator.next();

                    if (obj == getOwner()) {
                        containedInParent = true;
                        break;
                    }
                }

                if (containedInParent)
                    parent.childVolumeChanged(currentVolume - oldVolume, true);
            }
        }

        return super.depersistContents(item);
    }

    /**
     * DO NOT CALL DIRECTLY. Public out of necessity.
     */
    @Override
    public boolean internalItemRemoved(final GameObject item) {
        final int oldVolume = currentVolume;

        if (internalRemove(item)) {
            if (currentVolume != oldVolume) {
                final VolumeContainmentProperty property = item.getProperty(getClassPropertyId());

                if (property != null) {
                    final VolumeContainer parent = getVolumeContainerParent(this);

                    if (parent != null)
                        parent.childVolumeChanged(currentVolume - oldVolume, true);
                }
            }

            return super.internalItemRemoved(item);
        }

        return false;
    }

    private boolean internalRemove(final GameObject item) {
        return internalRemove(item, null);
    }

    private boolean internalRemove(final GameObject item, final VolumeContainmentProperty itemProperty) {
        final VolumeContainmentProperty property = itemProperty != null
                ? itemProperty : item.getProperty(getClassPropertyId());

        if (property == null) {
            LOGGER.warn("Item {} has no volume property.", item.getNetworkId());
            return false;
        }

        if (totalVolume != NO_VOLUME_LIMIT)
            currentVolume = currentVolume - property.getVolume();

        return true;

    }

    private void insertNewItem(final GameObject item) {
        insertNewItem(item, null);
    }

    private void insertNewItem(final GameObject item, final VolumeContainmentProperty itemProperty) {
        final VolumeContainmentProperty property = itemProperty != null
                ? itemProperty : item.getProperty(getClassPropertyId());

        if (property == null) {
            LOGGER.warn("Item {} has no volume property.", item.getNetworkId());
            return;
        }

        if (totalVolume != NO_VOLUME_LIMIT)
            currentVolume += property.getVolume();
    }

    private void childVolumeChanged(int volume, boolean updateParent) {
        if (totalVolume != NO_VOLUME_LIMIT)
            currentVolume += volume;

        if (updateParent) {
            final VolumeContainer parent = getVolumeContainerParent(this);

            if (parent != null)
                parent.childVolumeChanged(volume, true);
        }
    }


    private static VolumeContainer getVolumeContainerParent(final VolumeContainer self) {
        final GameObject owner = self.getOwner();
        final ContainedByProperty containedByProperty = owner.getContainedByProperty();

        if (containedByProperty != null) {
            final GameObject parent = containedByProperty.getContainedBy();

            if (parent != null)
                return parent.getVolumeContainerProperty();
        }

        return null;
    }
}
