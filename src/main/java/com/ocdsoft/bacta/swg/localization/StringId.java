package com.ocdsoft.bacta.swg.localization;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 11/21/2015.
 */
public final class StringId implements ByteBufferSerializable{
    public static final StringId Invalid = new StringId();

    @Getter
    private String table;
    @Getter
    private String text;
    @Getter
    private int textIndex;

    public StringId() {
        this.table = "";
        this.text = "";
        this.textIndex = 0;
    }

    public StringId(final String table, final String text) {
        this.table = table;
        this.text = text;
        this.textIndex = 0;
    }

    public StringId(final String table, final int textIndex) {
        this.table = table;
        this.text = "";
        this.textIndex = textIndex;
    }

    public StringId(final String canonicalRepresentation) {
        final int colonPosition = canonicalRepresentation.indexOf(':');

        if (colonPosition != -1) {
            this.table = canonicalRepresentation.substring(
                    canonicalRepresentation.charAt(0) == '@' ? 1 : 0, colonPosition);

            final String second = canonicalRepresentation.substring(colonPosition + 1);

            if (!second.isEmpty()) {
                if (Character.isDigit(second.charAt(0))) {
                    this.textIndex = Integer.parseInt(second);
                    this.text = "";
                } else {
                    this.textIndex = 0;
                    this.text = second;
                }
            } else {
                this.textIndex = 0;
                this.text = "";
            }
        } else {
            this.table = "";
            this.text = canonicalRepresentation;
            this.textIndex = 0;
        }
    }

    public boolean isInvalid() {
        return this.table.isEmpty() || (this.text.isEmpty() && this.textIndex == 0);
    }

    public boolean isValid() {
        return !this.table.isEmpty() && (!this.text.isEmpty() || this.textIndex != 0);
    }

    public void clear() {
        this.table = "";
        this.text = "";
        this.textIndex = 0;
    }

    public String getDebugString() {
        return this.table + ':' + this.text;
    }

    public String getCanonicalRepresentation() {
        return this.table + ':' + this.text;
    }

    public void setTable(final String table) {
        this.table = table;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setTextIndex(final int textIndex) {
        this.textIndex = textIndex;
    }

    public String localize(final LocalizationManager localizationManager) {
        return localizationManager.getLocalizedStringValue(this);
    }

    public static String decodeString(final String string) {
        //-- an encoded string can be a series of tokens, seperated by nulls
        //-- each token can be an encoded stringid ("@table:name") or a literal string

        //TODO: Figure out how to decode such a thing...

        return "";
    }

    public static StringId decodeStringId(final String string) {
        return new StringId(string);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        this.table = BufferUtil.getAscii(buffer);
        this.text = BufferUtil.getAscii(buffer);
        this.textIndex = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, table);
        BufferUtil.putAscii(buffer, text);
        buffer.putInt(textIndex);
    }
}
