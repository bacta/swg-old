package com.ocdsoft.bacta.swg.shared.object.crc;

import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

/**
 * Created by crush on 8/15/2014.
 */
public class PersistentCrcString extends CrcString {
    @Getter
    private final String string;

    public PersistentCrcString(final String string) {
        super(SOECRC32.hashCode(string));

        this.string = string;
    }
}
