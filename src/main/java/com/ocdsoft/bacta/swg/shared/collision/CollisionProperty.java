package com.ocdsoft.bacta.swg.shared.collision;

import com.ocdsoft.bacta.swg.shared.object.GameObject;
import com.ocdsoft.bacta.swg.shared.property.Property;
import com.ocdsoft.bacta.swg.shared.utility.Transform;

public class CollisionProperty extends Property {
    public static int getClassPropertyId() {
        return 0x0;
    }

    protected Transform lastTransform_w;
    protected Transform lastTransform_p;
    //protected CellWatcher lastCellObject;

    protected float stepHeight;
    protected float defaultRadius;
    protected float offsetX;
    protected float offsetZ;

    //Make this stuff thread-safe without volatile.
    protected volatile boolean extentsDirty;

    //protected volatile BaseExtent extent_l;
    //protected volatile BaseExtent extent_p;

    //protected volatile Sphere sphere_l;
    //protected volatile Sphere sphere_w;

    protected volatile float scale;

    //protected SpatialSubdivisionHandle spatialSubdivisionHandler;

    //protected Floor floor;
    //protected Footprint footprint;
    //protected int idleCounter;
    //protected CollisionProperty next;
    //protected CollisionProperty prev;

    //protected boolean disableCollisionWorldAddRemove;
    protected short flags;

    public CollisionProperty(final GameObject owner) {
        super(getClassPropertyId(), owner);
    }

    //public CollisionProperty(final GameObject owner, SharedObjectTemplate objectTemplate) {}


    public enum Flags {
        MOBILE(0x0001),
        FLORA(0x0002),
        PLAYER(0x0004),
        MOUNT(0x0008),
        SHIP(0x0010),
        PLAYER_CONTROLLED(0x0020),
        COLLIDABLE(0x0040),
        SERVER_SIDE(0x0080),
        IN_COMBAT(0x0100),
        IN_COLLISION_WORLD(0x0200),
        IDLE(0x0400),
        DISABLE_COLLISION_WORLD_ADD_REMOVE(0x0800);

        public final int value;

        Flags(final int value) {
            this.value = value;
        }
    }
}
