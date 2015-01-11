package com.ocdsoft.bacta.swg.shared.slot;

import gnu.trove.list.TIntList;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crush on 8/21/2014.
 */
public class ArrangementDescriptor {
    @Getter
    private final String name;
    protected final List<TIntList> arrangements = new ArrayList<>();

    public ArrangementDescriptor(final String name) {
        this.name = name;
    }

    public final void addArrangement(TIntList arrangement) {
        arrangements.add(arrangement);
    }

    public final int getArrangementCount() { return arrangements.size(); }

    public final TIntList getArrangement(int index) { return arrangements.get(index); }
}
