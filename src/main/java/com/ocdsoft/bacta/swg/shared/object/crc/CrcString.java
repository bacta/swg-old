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

    /**
     * SOE normalizes strings by replacing any backslashes with forward slashes, and lower-casing the part
     * after the period - which is probably meant for file extensions.
     * @param input The string to be normalized.
     * @return A string that has been normalized.
     */
    public static String normalize(final String input) {
        final StringBuilder stringBuilder = new StringBuilder(input.length());
        boolean beyondPeriod = false;

        for (char c : input.toCharArray()) {
            if (c == '.')
                beyondPeriod = true;

            if (c == '\\') {
                stringBuilder.append('/');
            } else {
                stringBuilder.append(beyondPeriod ? Character.toLowerCase(c) : c);
            }
        }

        return stringBuilder.toString();
    }
}
