package com.ocdsoft.bacta.swg.shared.slot;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import lombok.Getter;

/**
 * Created by crush on 8/21/2014.
 */
public class SlotDescriptor {
    @Getter
    private final String name;

    protected final TIntList slots = new TIntArrayList(); //Actually List<SlotId> but SlotId is simply an int...

    public SlotDescriptor(final String name) {
        this.name = name;
    }

    public TIntList getSlots() { return slots; }
}
