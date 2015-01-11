package com.ocdsoft.bacta.swg.shared.container;

import com.ocdsoft.bacta.swg.server.game.object.SceneObject;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by crush on 8/26/2014.
 */
public class VolumeContainer extends Container {
    private final List<SceneObject> contents;
    @Getter
    private int totalVolume;

    public VolumeContainer(SceneObject owner, int totalVolume) {
        super(owner);

        this.contents = new ArrayList<>(totalVolume);
        this.totalVolume = totalVolume;
    }

    public final void add(SceneObject item) {
        contents.add(item);
    }

    @Override
    public final boolean isVolumeContainer() { return true; }

    @Override
    public Iterator<SceneObject> iterator() {
        return Collections.unmodifiableList(contents).iterator();
    }

    @Override
    public boolean contains(SceneObject item) {
        return contents.contains(item);
    }

    @Override
    public void remove(SceneObject item) {
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
