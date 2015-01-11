package com.ocdsoft.bacta.swg.shared.iff;

/**
 * Created by crush on 2/12/14.
 */
public interface IffWriter<T> {
    /**
     * Writes the object into an array of bytes representing the IFF format.
     *
     * @param obj The object that will be written into IFF format.
     * @return
     */
    byte[] write(T obj);
}
