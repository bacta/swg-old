package com.ocdsoft.bacta.swg.shared.object.crc;

import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

/**
 * Created by crush on 8/18/2014.
 */
public class ConstCharCrcString extends CrcString {
    @Getter
    private String string;

    public ConstCharCrcString(final String string) {
        this(string, SOECRC32.hashCode(string));
    }

    public ConstCharCrcString(final String string, int crc) {
        super(crc);

        this.string = string;
    }
}
