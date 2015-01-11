package com.ocdsoft.bacta.swg.shared.iff;

/**
 * Created by crush on 2/22/14.
 */
public class UnregisteredIffReaderException extends RuntimeException {
    public UnregisteredIffReaderException(final String formType) {
        super("Attempted to read IFF file with form type of '" + formType + "', but no reader was registered.");
    }
}
