package com.ocdsoft.bacta.swg.message;

public final class ErrorMessage extends SwgMessage {

    public ErrorMessage(final String errorName, final String description, boolean fatal) {
        super(0x03, 0xB5ABF91A);

        writeAscii(errorName);
        writeAscii(description);
        writeBoolean(fatal);
    }

}
