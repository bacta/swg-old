package com.ocdsoft.bacta.swg.foundation;

import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;

/**
 * Created by crush on 11/22/2015.
 */
public class PersistentCrcString extends CrcString {

    private String string;

    public PersistentCrcString() {
    }

    public PersistentCrcString(final String string, boolean applyNormalize) {
        set(string, applyNormalize);
    }

    public PersistentCrcString(final String string, int crc) {
        set(string, crc);
    }

    @Override
    public String getString() {
        return string;
    }

    @Override
    public void clear() {
        crc = Crc.Null;
    }

    @Override
    public void set(final String string, boolean applyNormalize) {
        Preconditions.checkNotNull(string);
        internalSet(string, applyNormalize);
        calculateCrc();
    }

    @Override
    public void set(final String string, int crc) {
        Preconditions.checkNotNull(string);
        internalSet(string, false);
        this.crc = crc;
    }

    private void internalSet(final String string, boolean applyNormalize) {
        this.string = string;
        //TODO: normalize?
        //TODO: Check system filename max length?
    }
}
