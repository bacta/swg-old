package com.ocdsoft.bacta.swg.shared.container;

import bacta.iff.Iff;
import com.ocdsoft.bacta.swg.shared.foundation.CrcLowerString;
import gnu.trove.list.TIntList;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crush on 4/22/2016.
 */
public class ArrangementDescriptor {
    @Getter
    private final CrcLowerString name;
    private final List<TIntList> arrangements = new ArrayList<>();
    @Getter
    private volatile int referenceCount;

    public ArrangementDescriptor(final Iff iff, final CrcLowerString name) {
        this.name = name;
    }

    public int getArrangementCount() {
        return arrangements.size();
    }

    public TIntList getArrangement(final int index) {
        return arrangements.get(index);
    }

    public void fetch() {
        ++referenceCount;
    }

    public void release() {
        --referenceCount;
    }

    private void load0000(final Iff iff) {

    }
}
