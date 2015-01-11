package com.ocdsoft.bacta.swg.shared.object.crc;

/**
 * Created by crush on 8/21/2014.
 */
public class CrcLowerString extends PersistentCrcString {
    public CrcLowerString(String string) {
        super(string.toLowerCase());
    }
}
