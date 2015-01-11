package com.ocdsoft.bacta.swg.shared.container;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Created by crush on 8/26/2014.
 */
public abstract class Container<T> implements Iterable<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Getter
    protected final T owner;

    public Container(T owner) {
        this.owner = owner;
    }

    public boolean isSlottedContainer() { return false; }
    public boolean isVolumeContainer() { return false; }

    public abstract Iterator<T> iterator();
    public abstract boolean contains(T item);
    public abstract void remove(T item);
}
