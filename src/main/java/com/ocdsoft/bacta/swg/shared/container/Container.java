package com.ocdsoft.bacta.swg.shared.container;

import com.ocdsoft.bacta.swg.server.game.object.SceneObject;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Created by crush on 8/26/2014.
 */
public abstract class Container implements Iterable<SceneObject> {
    protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Getter
    protected final SceneObject owner;

    public Container(SceneObject owner) {
        this.owner = owner;
    }

    public boolean isSlottedContainer() { return false; }
    public boolean isVolumeContainer() { return false; }

    public abstract Iterator<SceneObject> iterator();
    public abstract boolean contains(SceneObject item);
    public abstract void remove(SceneObject item);
}
