package com.ocdsoft.bacta.swg.data.customization;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.data.SharedFileLoader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBuffer;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBufferContext;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.object.customization.BasicRangedIntCustomizationVariable;
import com.ocdsoft.bacta.swg.shared.object.customization.CustomizationVariable;
import com.ocdsoft.bacta.swg.shared.object.customization.PaletteColorCustomizationVariable;
import com.ocdsoft.bacta.tre.TreeFile;
import gnu.trove.map.TIntShortMap;
import gnu.trove.map.TShortIntMap;
import gnu.trove.map.hash.TIntShortHashMap;
import gnu.trove.map.hash.TShortIntHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 3/31/14.
 * <p/>
 * The AssetCustomizationManager links an appearance file with its various elements such as which
 * customization variables are available and reference to any palettes. The file format of the
 * AssetCustomizationManager is a bit crazy. It basically serves as a database.
 * <p/>
 * There are 12 FORMs in the IFF file.
 * <ul>
 * <li>NAME - A list of null terminated ASCII strings that will represent everything from palette
 * names to customization variable names. These strings are stored as a single large byte array, and
 * are referenced by their offset within that array. The offset can be found in either VNOF or PNOF,
 * which stands for VariableNameOffset and PaletteNameOffset respectively.</li>
 * <li>VNOF - VariableNameOffset. This is an array of shorts. The value is the offset of the variable
 * name within the NAME byte array. The index within the VNOF array is the index of the variable.</li>
 * <li>PNOF - PaletteNameOffset. This is an array of shorts. The value is the offset of the palette file
 * path within the NAME byte array. The index within the PNOF array is the index of the palette file path.</li>
 * <li>DEFV - DefaultValue. This is an array of ints. They stand for the default value of a ranged int
 * or palette customization variable. Their index comes from the third byte of the UCMP section.</li>
 * <li>IRNG - RangedIntCustomizationVariable. These are sets of integers that correspond to values
 * for the BasicRangedIntCustomizationVariables when RTYP is not a palette. The index for this comes
 * from the RTYP value.</li>
 * <li>RTYP - RangeType. This value serves to purposes. If the 15th bit is set (0x8000), then it is a
 * palette RangedIntCustomizationVariable. Otherwise, it is BasicRangedIntCustomizationVariable. The
 * other 14 bits (0x7FFF) make up the index. If it is a palette, then the index is used in PNOF. If it
 * is a BasicRangedIntCustomizationVariable, then the index is used in IRNG.</li>
 * <li>UCMP - </li>
 * <li>ULST - This is an array of shorts that serve as the index in the UCMP section. The index within
 * the ULST array corresponds to the index fetched from the UIDX.</li>
 * <li>UIDX - This is a map of short->short+byte. The short key corresponds to the value of the CIDX map.
 * The short in the value corresponds to the starting index offset in UCMP, while the byte specifies a
 * length. short = startingIndex; short+byte = finalIndex</li>
 * <li>LLST - Not sure exactly, but this acts similar to ULST</li>
 * <li>LIDX - Not sure exactly, but this acts similar to UIDX. Ends up recursing.</li>
 * <li>CIDX - This is a CRC to index map. The CRC corresponds to the CRC32 of the appearance file path.
 * The entire path is crc'd. i.e. appearance/piket_hue.sat</li>
 * </ul>
 */
@Singleton
public final class AssetCustomizationManager implements SharedFileLoader {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int ID_ACST = ChunkBuffer.createChunkId("ACST");
    private static final int ID_0000 = ChunkBuffer.createChunkId("0000");
    private static final int ID_NAME = ChunkBuffer.createChunkId("NAME");
    private static final int ID_PNOF = ChunkBuffer.createChunkId("PNOF");
    private static final int ID_VNOF = ChunkBuffer.createChunkId("VNOF");
    private static final int ID_DEFV = ChunkBuffer.createChunkId("DEFV");
    private static final int ID_IRNG = ChunkBuffer.createChunkId("IRNG");
    private static final int ID_RTYP = ChunkBuffer.createChunkId("RTYP");
    private static final int ID_UCMP = ChunkBuffer.createChunkId("UCMP");
    private static final int ID_ULST = ChunkBuffer.createChunkId("ULST");
    private static final int ID_UIDX = ChunkBuffer.createChunkId("UIDX");
    private static final int ID_LLST = ChunkBuffer.createChunkId("LLST");
    private static final int ID_LIDX = ChunkBuffer.createChunkId("LIDX");
    private static final int ID_CIDX = ChunkBuffer.createChunkId("CIDX");

    private byte[] name;
    private short[] pnof;
    private short[] vnof;
    private int[] defv;
    private short[] rtyp;
    private long[] irng;
    private short[] ulst; //array of shorts. index for ucmp.
    private int[] ucmp; //3 bytes: 1 name index, 2 rtyp index, 3 defv index.
    private short[] llst; //array of shorts just like ulst?

    private TIntShortMap cidx = new TIntShortHashMap(); //crc->index
    private TShortIntMap uidx = new TShortIntHashMap(); //key->offset + length
    private TShortIntMap lidx = new TShortIntHashMap(); //key->offset + length?

    private final TreeFile treeFile;

    @Inject
    public AssetCustomizationManager(TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }

    private void load() {
        logger.trace("Starting to load.");

        final ChunkReader reader = new ChunkReader("customization/asset_customization_manager.iff", treeFile.open("customization/asset_customization_manager.iff"));

        ChunkBufferContext root = reader.nextChunk();

        if (root.isFormType(ID_ACST)) {
            root = reader.nextChunk();

            if (root.isFormType(ID_0000)) {
                parseNAME(reader);
                parsePNOF(reader);
                parseVNOF(reader);
                parseDEFV(reader);
                parseIRNG(reader);
                parseRTYP(reader);
                parseUCMP(reader);
                parseULST(reader);
                parseUIDX(reader);
                parseLLST(reader);
                parseLIDX(reader);
                parseCIDX(reader);
            } else {
                logger.error("Invalid version for asset customization manager file.");
            }
        } else {
            logger.error("Invalid asset customization manager file.");
        }

        logger.debug("Finished loading.");
    }

    @Override
    public void reload() {
        synchronized (this) {
            name = null;
            pnof = null;
            vnof = null;
            defv = null;
            rtyp = null;
            irng = null;
            ulst = null;
            ucmp = null;
            llst = null;

            cidx.clear();
            uidx.clear();
            lidx.clear();

            load();
        }
    }

    private String getNameAtOffset(short offset) {
        final StringBuilder stringBuilder = new StringBuilder();

        byte b;
        while ((b = name[offset++]) != '\0')
            stringBuilder.append((char) b);

        return stringBuilder.toString();
    }

    /**
     * Gets the name of this variable's palette file name.
     *
     * @param index Corresponds to UIDX value. This index is the index of the ULST array. Then retrieves the UCMP
     *              value. The first byte of the UCMP value is the index in the PNOF array. The value in the PNOF array
     *              is the index in the NAME array.
     * @return Returns the palette variable's file name.
     * @throws ArrayIndexOutOfBoundsException If the index isn't found in the ULST array.
     */
    private String getPaletteFileName(int index) {
        return getNameAtOffset(pnof[getRangeTypeValueIndex(index) - 1]);
    }

    private int getNameIndex(int index) {
        return ucmp[ulst[index] - 1] & 0xFF;
    }

    private int getRtypIndex(int index) {
        return ucmp[ulst[index] - 1] >> 8 & 0xFF;
    }

    private int getDefvIndex(int index) {
        return ucmp[ulst[index] - 1] >> 16 & 0xFF;
    }

    /**
     * Gets the name of this customization variable.
     *
     * @param index Corresponds to UIDX value. This index is the index of the ULST array. Then retrieves the UCMP
     *              value. The first byte of the UCMP value is the index in the VNOF array. The value in the VNOF array
     *              is the index in the NAME array.
     * @return Returns the customization variable's name.
     * @throws ArrayIndexOutOfBoundsException If the index isn't found in the ULST array.
     */
    private String getCustomizationVariableName(int index) {
        return getNameAtOffset(vnof[getNameIndex(index) - 1]);
    }

    private int getRangeTypeValueIndex(int index) {
        return (rtyp[getRtypIndex(index) - 1] & 0x7FFF);
    }

    private boolean isPaletteCustomizationVariable(int index) {
        return (rtyp[getRtypIndex(index) - 1] & 0x8000) != 0;
    }

    private int getDefaultValue(int index) {
        return getDefvIndex(index) == 0 ? 0 : defv[getDefvIndex(index) - 1];
    }

    private int getRangedIntMinValue(int index) {
        return (int) (irng[getRangeTypeValueIndex(index) - 1] & 0xFFFFFFFF);
    }

    private int getRangedIntMaxValue(int index) {
        return (int) (irng[getRangeTypeValueIndex(index) - 1] >> 32);
    }

    /**
     * Gets a map of customization variables for the appearance template specified. The String key is
     * the name of the customization variable, while the value is the {@link com.ocdsoft.bacta.swg.shared.object.customization.CustomizationVariable}
     * itself. This variation defaults to skipping variables that start with "/shared_owner/".
     *
     * @param key The path to the appearance template for which to fetch customization variables.
     * @return Returns an empty map if no customization variables are found for the entry, otherwise filled.
     */
    public Map<String, CustomizationVariable> getCustomizationVariables(final String key) {
        return getCustomizationVariables(SOECRC32.hashCode(key));
    }

    /**
     * Gets a map of customization variables for the appearance template specified. The String key is
     * the name of the customization variable, while the value is the {@link com.ocdsoft.bacta.swg.shared.object.customization.CustomizationVariable}
     * itself. This variation defaults to skipping variables that start with "/shared_owner/".
     *
     * @param crc The crc32 of the path to the appearance template for which to fetch customization variables.
     * @return Returns an empty map if no customization variables are found for the entry, otherwise filled.
     */
    public Map<String, CustomizationVariable> getCustomizationVariables(final int crc) {
        return getCustomizationVariables(crc, true);
    }

    /**
     * Gets a map of customization variables for the appearance template specified. The String key is
     * the name of the customization variable, while the value is the {@link com.ocdsoft.bacta.swg.shared.object.customization.CustomizationVariable}
     * itself.
     *
     * @param key             The path to the appearance template for which to fetch customization variables.
     * @param skipSharedOwner Should the "/shared_owner/" customization variables be skipped.
     * @return Returns an empty map if no customization variables are found for the entry, otherwise filled.
     */
    public Map<String, CustomizationVariable> getCustomizationVariables(final String key, final boolean skipSharedOwner) {
        return getCustomizationVariables(SOECRC32.hashCode(key), skipSharedOwner);
    }

    /**
     * Gets a map of customization variables for the appearance template specified. The String key is
     * the name of the customization variable, while the value is the {@link com.ocdsoft.bacta.swg.shared.object.customization.CustomizationVariable}
     * itself.
     *
     * @param crc             The crc of the path to the appearance template for which to fetch customization variables.
     * @param skipSharedOwner Should the "/shared_owner/" customization variables be skipped.
     * @return Returns an empty map if no customization variables are found for the entry, otherwise filled.
     */
    public Map<String, CustomizationVariable> getCustomizationVariables(final int crc, final boolean skipSharedOwner) {
        final Map<String, CustomizationVariable> map = new HashMap<>();

        if (cidx.containsKey(crc)) {
            final short index = cidx.get(crc);
            fillCustomizationVariables(index, map, skipSharedOwner);
        }

        return map;
    }

    private void fillCustomizationVariables(short index, Map<String, CustomizationVariable> map, boolean skipSharedOwner) {
        if (uidx.containsKey(index)) {
            int idx = uidx.get(index);

            int currentIndex = idx & 0xFFFF;
            int finalIndex = currentIndex + (idx >> 16 & 0xFF);

            for (; currentIndex < finalIndex; currentIndex++) {
                final String variableName = getCustomizationVariableName(currentIndex);

                if (skipSharedOwner && variableName.startsWith("/shared_owner/"))
                    continue;

                if (!isPaletteCustomizationVariable(currentIndex)) {
                    map.put(variableName, new BasicRangedIntCustomizationVariable(
                            getRangedIntMinValue(currentIndex),
                            getRangedIntMaxValue(currentIndex),
                            getDefaultValue(currentIndex)
                    ));
                } else {
                    //open palette file and get min/max
                    map.put(variableName, new PaletteColorCustomizationVariable(
                            getPaletteFileName(currentIndex),
                            getDefaultValue(currentIndex)
                    ));
                }
            }
        }

        if (lidx.containsKey(index)) {
            int idx = lidx.get(index);

            short currentIndex = (short) idx;
            short finalIndex = (short) (currentIndex + (idx >> 16));

            for (int i = finalIndex; currentIndex < i; currentIndex++) {
                fillCustomizationVariables(llst[currentIndex], map, skipSharedOwner);
            }
        }
    }

    private final void parseNAME(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_NAME)) {
            name = new byte[root.getChunkSize()];
            reader.readBytes(name);
        } else {
            logger.error("Expected NAME chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }

    private final void parsePNOF(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_PNOF)) {
            int n = root.getChunkSize() / 2;
            pnof = new short[n];

            for (int i = 0; i < n; i++)
                pnof[i] = reader.readShort();

        } else {
            logger.error("Expected PNOF chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }

    private final void parseVNOF(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_VNOF)) {
            int n = root.getChunkSize() / 2;
            vnof = new short[n];

            for (int i = 0; i < n; i++)
                vnof[i] = reader.readShort();

        } else {
            logger.error("Expected VNOF chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }

    private final void parseDEFV(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_DEFV)) {
            int n = root.getChunkSize() / 4;
            defv = new int[n];

            for (int i = 0; i < n; i++)
                defv[i] = reader.readInt();

        } else {
            logger.error("Expected DEFV chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }

    private final void parseIRNG(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_IRNG)) {
            int n = root.getChunkSize() / 8;
            irng = new long[n];

            for (int i = 0; i < n; i++)
                irng[i] = reader.readLong();

        } else {
            logger.error("Expected IRNG chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }

    private final void parseRTYP(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_RTYP)) {
            int n = root.getChunkSize();
            rtyp = new short[n];

            for (int i = 0; i < n; i++)
                rtyp[i] = reader.readShort();

        } else {
            logger.error("Expected RTYP chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }

    private final void parseUCMP(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_UCMP)) {
            int n = root.getChunkSize() / 3;
            ucmp = new int[n];

            for (int i = 0; i < n; i++) {
                int val = (reader.readByte()) //name index
                        + (reader.readByte() << 8) //rtyp index
                        + (reader.readByte() << 16); //defv index

                ucmp[i] = val;
            }

        } else {
            logger.error("Expected UCMP chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }

    private final void parseULST(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_ULST)) {
            int n = root.getChunkSize() / 2;
            ulst = new short[n];

            for (int i = 0; i < n; i++)
                ulst[i] = reader.readShort();

        } else {
            logger.error("Expected ULST chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }

    private final void parseUIDX(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_UIDX)) {
            int n = root.getChunkSize() / 5;

            for (int i = 0; i < n; i++) {
                short key = reader.readShort();
                int val = reader.readShort()
                        + (reader.readByte() << 16);

                uidx.put(key, val);
            }
        } else {
            logger.error("Expected UIDX chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }

    private final void parseLLST(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_LLST)) {
            int n = root.getChunkSize() / 2;
            llst = new short[n];

            for (int i = 0; i < n; i++)
                llst[i] = reader.readShort();

        } else {
            logger.error("Expected LLST chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }

    private final void parseLIDX(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_LIDX)) {
            int n = root.getChunkSize() / 5;

            for (int i = 0; i < n; i++) {
                short key = reader.readShort();
                int val = reader.readShort();
                val += reader.readByte() << 16;

                lidx.put(key, val);
            }
        } else {
            logger.error("Expected LIDX chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }

    private final void parseCIDX(ChunkReader reader) {
        final ChunkBufferContext root = reader.nextChunk();

        if (root.isChunkId(ID_CIDX)) {
            int n = root.getChunkSize() / 6;

            for (int i = 0; i < n; i++) {
                int key = reader.readInt();
                short val = reader.readShort();

                cidx.put(key, val);
            }
        } else {
            logger.error("Expected CIDX chunk, but encountered: " + ChunkBuffer.getChunkName(root.getChunkId()));
        }
    }
}
