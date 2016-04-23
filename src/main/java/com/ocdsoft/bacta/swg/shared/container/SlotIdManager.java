package com.ocdsoft.bacta.swg.shared.container;

import bacta.iff.Iff;
import com.ocdsoft.bacta.swg.shared.foundation.CrcString;
import gnu.trove.list.TIntList;

import java.util.List;

/**
 * Created by crush on 4/22/2016.
 * <p>
 * The SlotIdManager class maps valid slot names to SlotId objects.
 * For clients, it can also provide the Appearance-related hardpoint names
 * associated with a given slot.
 */
public class SlotIdManager {

    public int findSlotId(final CrcString slotName) {
        return 0;
    }

    public TIntList findSlotIdsForCombatBone(final int bone) {
        return null;
    }

    public CrcString getSlotName(final int slotId) {
        return null;
    }

    public boolean isSlotPlayerModifiable(final int slotId) {
        return false;
    }

    public boolean isSlotAppearanceRelated(final int slotId) {
        return false;
    }

    public CrcString getSlotHardpointName(final int slotId) {
        return null;
    }

    public boolean getSlotObserveWithParent(final int slotId) {
        return false;
    }

    public boolean getSlotExposeWithParent(final int slotId) {
        return false;
    }

    public List<String> getSlotsThatHoldAnything() {
        return null;
    }

    private void load0006(final Iff iff, boolean loadHardpoitnNameData) {

    }
}
