package com.ocdsoft.bacta.swg.shared.localization;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.Getter;

import java.nio.ByteBuffer;


/**
 * The StringId class represents an entry in an STF localization file that is loaded in the game client. STF localization
 * files contain a key/value pair that link a key to a string value in the locale of choice. STF files are generally found
 * in the client by following the path <code>string/{locale}/</code> where <code>{locale}</code> would be the language code specifying the language
 * that should be loaded. Known locale are 'en' (English) and 'jp' (Japanese), but it is easy to extend this to any language
 * desired by providing the proper localization files.
 * <p>
 * An example entry of a StringId in a localization file might look like the following:
 * <pre>
 * File: string/en/guild.stf
 * -----------------------------------------------------------------
 * | key            | value                                        |
 * -----------------------------------------------------------------
 * | disband_prompt | Are you sure you wish to disband your guild? |
 * -----------------------------------------------------------------
 * </pre>
 * </p>
 * <p>The StringId would be represented by the file name without the extension, followed by the key. It will often take on the following format:
 * <pre>
 *    Format:  {@literal @}file:key
 *    Example: {@literal @}guild:disband_prompt
 * </pre>
 * Sometimes, a STF file is in a sub directory, within its locale's directory. An example of such a file is <code>string/en/celebrity/c3po.stf</code>.
 * Referencing this StringId is similar to above, except the file name should include the directory as well.
 * <pre>
 *    Format:  {@literal @}dir/file:key
 *    Example: {@literal @}celebrity/c3po:npc_1
 * </pre>
 * </p>
 */
public final class StringId implements ByteBufferWritable {
    public static final StringId empty = new StringId();
    public static final StringId defaultStringId = new StringId("string_id_table", 0);

    @Getter private final String table;
    @Getter private final String text;
    @Getter private final int textIndex;

    private StringId() {
        this.table = "";
        this.text = "";
        this.textIndex = 0;
    }

    /**
     * Creates a StringId from the entry's STF file and corresponding key.
     *
     * @param table The STF file within which this entry is stored. This should be everything after the <code>string/{locale}/</code> and before the extension <code>.stf</code>.
     * @param text  The key within the file corresponding to the entry.
     */
    public StringId(final String table, final String text) {
        this.table = table;
        this.textIndex = 0;
        this.text = text;
    }

    public StringId(final String table, final int textIndex) {
        this.table = table;
        this.textIndex = textIndex;
        this.text = "";
    }

    /**
     * Creates a StringId from the packed format of a StringId. The packed format is <code>@dir/file:key</code>.
     *
     * @param packedFormat The StringId in packed format as a String.
     * @throws Exception If the incoming string is not in a valid packed format.
     * @see StringId
     */
    public StringId(String packedFormat) throws Exception {
        this.textIndex = 0;

        //Discard the leading @ if it exists.
        if (packedFormat.charAt(0) == '@')
            packedFormat = packedFormat.substring(1);

        String[] parts = packedFormat.split(":");

        //TODO Throw an exception here.
        if (parts.length < 2)
            throw new Exception("Not enough parts to generate packed format.");

        this.table = parts[0];
        this.text = parts[1];
    }

    public StringId(ByteBuffer buffer) {
        this.table = BufferUtil.getAscii(buffer);
        this.textIndex = buffer.getInt();
        this.text = BufferUtil.getAscii(buffer);
    }

    /**
     * Formats this StringId into its packed format of <code>@dir/file:key</code>.
     *
     * @return Packed format string version of StringId.
     */
    public String toPackedFormat() {
        return "@" + table + ":" + text;
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, table);
        buffer.putInt(textIndex);
        BufferUtil.putAscii(buffer, text);
    }

    /*
    public String localize(UnicodeString unicodeString, boolean bool) {
        return "";
    }

    public String getCanonicalRepresentation() {

    }

    public void decodeString(UnicodeString string) {

    }

    public void decodeStringId(UnicodeString string) {

    }*/
}
