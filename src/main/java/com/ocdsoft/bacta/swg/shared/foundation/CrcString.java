package com.ocdsoft.bacta.swg.shared.foundation;

/**
 * Created by crush on 11/22/2015.
 */
public abstract class CrcString {
    /**
     * Normalize a string.
     * <p>
     * This function will clean up a string.  All alpha characters will be
     * changed to lower case, all backslashes will be converted to forward
     * slashes, all dots following a slash will be removed, and consecutive
     * slashes will be converted into a single slash.
     */
    public static String normalize(final String input) {
        return "";
    }

    protected int crc;

    public boolean isEmpty() {
        final String str = getString();
        return str == null && str.isEmpty();
    }

    public int getCrc() {
        return crc;
    }

    public abstract String getString();

    public abstract void clear();

    public abstract void set(final String string, boolean applyNormalize);

    public abstract void set(final String string, int crc);

    protected CrcString() {
    }

    protected CrcString(int crc) {
    }

    protected void calculateCrc() {
    }

}
