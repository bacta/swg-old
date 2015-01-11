package com.ocdsoft.bacta.swg.shared.container;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by crush on 8/26/2014.
 */
public class VolumeContainer<T> extends Container<T> {
    private final List<T> contents;
    @Getter
    private int totalVolume;

    public VolumeContainer(T owner, int totalVolume) {
        super(owner);

        this.contents = new ArrayList<>(totalVolume);
        this.totalVolume = totalVolume;
    }

    public final void add(T item) {
        contents.add(item);
    }

    @Override
    public final boolean isVolumeContainer() { return true; }

    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableList(contents).iterator();
    }

    @Override
    public boolean contains(T item) {
        return contents.contains(item);
    }

    @Override
    public void remove(T item) {
        contents.remove(item);
    }

    public boolean checkVolume(int additionalVolume) {
        if (totalVolume == -1)
            return true;

        if (contents.size() + additionalVolume <= totalVolume) {
            //TOOD: Check recursively.
            return true;
        }

        return false;
    }
}
