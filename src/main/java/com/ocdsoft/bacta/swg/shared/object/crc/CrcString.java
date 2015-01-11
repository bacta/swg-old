package com.ocdsoft.bacta.swg.shared.object.crc;

import lombok.Getter;

/**
 * Created by crush on 8/18/2014.
 */
public abstract class CrcString {
    @Getter
    private final int crc;

    public CrcString(final int crc) {
        this.crc = crc;
    }
}
