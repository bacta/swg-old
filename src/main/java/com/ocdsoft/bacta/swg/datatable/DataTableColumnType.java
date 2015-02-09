package com.ocdsoft.bacta.swg.datatable;

import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.shared.object.crc.CrcString;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 2/8/15.
 */
public final class DataTableColumnType {
    private static final Logger logger = LoggerFactory.getLogger(DataTableColumnType.class);

    private static final char valueOpeningDelimiter = '[';
    private static final char valueClosingDelimiter = ']';
    private static final char enumOpeningDelimiter = '(';
    private static final char enumClosingDelimiter = ')';

    @Getter
    private String typeSpecString;
    @Getter
    private DataType type;
    @Getter
    private DataType basicType;
    @Getter
    private DataTableCell defaultCell;

    private String defaultValue;
    private TObjectIntMap<String> enumMap;

    public DataTableColumnType(final String typeSpecString) {
        this.typeSpecString = typeSpecString;
        this.enumMap = null;
        this.defaultCell = null;

        final char typeCode = typeSpecString.charAt(0);

        int openingIndex = typeSpecString.indexOf(valueOpeningDelimiter);
        int closingIndex = typeSpecString.indexOf(valueClosingDelimiter);

        if (openingIndex > -1 && closingIndex > -1)
            this.defaultValue = typeSpecString.substring(openingIndex + 1, closingIndex);

        switch (typeCode) {
            case 'i':
                this.basicType = DataType.Int;
                this.type = DataType.Int;

                if ("".equals(this.defaultValue))
                    this.defaultValue = "0";

                break;

            case 'f':
                this.basicType = DataType.Float;
                this.type = DataType.Float;

                if ("".equals(this.defaultValue))
                    this.defaultValue = "0";

                break;

            case 's':
                this.basicType = DataType.String;
                this.type = DataType.String;
                break;

            case 'c':
                this.basicType = DataType.Comment;
                this.type = DataType.Comment;
                break;

            case 'h':
                this.basicType = DataType.Int;
                this.type = DataType.HashString;
                break;

            case 'p':
                this.basicType = DataType.String;
                this.type = DataType.PackedObjVars;
                break;

            case 'b':
                this.basicType = DataType.Int;
                this.type = DataType.Bool;

                if (!"1".equals(this.defaultValue))
                    this.defaultValue = "0";

                break;

            case 'e':
                this.basicType = DataType.Int;
                this.type = DataType.Enum;

                openingIndex = typeSpecString.indexOf(enumOpeningDelimiter);
                closingIndex = typeSpecString.indexOf(enumClosingDelimiter);

                if (openingIndex > -1 && closingIndex > -1) {
                    final String enumString = typeSpecString.substring(openingIndex + 1, closingIndex);
                    final String[] enumValues = enumString.split(",");

                    this.enumMap = new TObjectIntHashMap<String>(enumValues.length);

                    for (final String enumValue : enumValues) {
                        final String[] keyValuePair = enumValue.split("=", 1);

                        this.enumMap.put(keyValuePair[0], Integer.parseInt(keyValuePair[1]));
                    }

                    if (!this.enumMap.containsKey(this.defaultValue))
                        logger.warn(String.format("Default value [%s] is not a member of enumeration.",
                                this.defaultValue));
                }

                break;

            case 'v':
                this.basicType = DataType.Int;
                this.type = DataType.BitVector;

                openingIndex = typeSpecString.indexOf(enumOpeningDelimiter);
                closingIndex = typeSpecString.indexOf(enumClosingDelimiter);

                if (openingIndex > -1 && closingIndex > -1) {
                    final String enumString = typeSpecString.substring(openingIndex + 1, closingIndex);
                    final String[] enumValues = enumString.split(",");

                    this.enumMap = new TObjectIntHashMap<String>(enumValues.length);

                    logger.warn("Trying to parse a BitVector data table column, but this functionality isn't completely implemented.");

                    for (final String enumValue : enumValues) {
                        final String[] keyValuePair = enumValue.split("=", 1);

                        this.enumMap.put(keyValuePair[0], Integer.parseInt(keyValuePair[1]));
                    }
                }
                break;

            case 'z':
                this.basicType = DataType.Int;
                this.type = DataType.BitVector;

                openingIndex = typeSpecString.indexOf(enumOpeningDelimiter);
                closingIndex = typeSpecString.indexOf(enumClosingDelimiter);

                if (openingIndex > -1 && closingIndex > -1) {
                    final String enumString = typeSpecString.substring(openingIndex + 1, closingIndex);

                    logger.warn(String.format("Attempted to parse data column of type z with enum value [%d].",
                            enumString));
                }

                //TODO: Open the specified file, and use it for enum map.

                break;

            default:
                this.basicType = DataType.Unknown;
                this.type = DataType.Unknown;
                break;
        }

        createDefaultCell();
    }

    private final void createDefaultCell() {
        try {
            if (this.defaultCell != null) {
                logger.error("Default cell already has a value. Unexpected call.");
                return ;
            }

            final String value = mangleValue(this.defaultValue);

            switch (this.basicType) {
                case Int:
                    this.defaultCell = new DataTableIntCell(Integer.parseInt(value));
                    break;

                case Float:
                    this.defaultCell = new DataTableFloatCell(Float.parseFloat(value));
                    break;

                case String:
                    this.defaultCell = new DataTableStringCell(value);
                    break;
            }

        } catch (InvalidDataTypeValueException ex) {
            logger.error(String.format("The default value [%s] is invalid. Unable to create default cell for type [%s]",
                    this.defaultValue,
                    this.typeSpecString));
        }
    }

    public final String mangleValue(String value) throws InvalidDataTypeValueException {
        if (value.length() == 0) {
            if ("required".equals(this.defaultValue) || "unique".equals(this.defaultValue))
                throw new InvalidDataTypeValueException(value, this.type);

            if (!this.defaultValue.equals(value)) {
                value = this.defaultValue;
            }
        }

        if (DataType.PackedObjVars.equals(this.type)) {
            throw new UnsupportedOperationException("Do some voodoo here with PackedObjVars");
        }

        if (DataType.Int.equals(this.basicType)) {
            switch (this.type) {
                default:
                    throw new InvalidDataTypeValueException(value, this.type);

                case Bool:
                    if (!("0".equals(value) || "1".equals(value)))
                        throw new InvalidDataTypeValueException(value, this.type);

                    return value;

                case HashString:
                    if (value.length() != 0)
                        value = String.valueOf(SOECRC32.hashCode(CrcString.normalize(value)));

                    break;

                case Enum:
                    if (!this.enumMap.containsKey(value))
                        throw new InvalidDataTypeValueException(value, this.type);

                    value = String.valueOf(this.enumMap.get(value));

                    break;

                case BitVector:
                    throw new UnsupportedOperationException("BitVector not yet supported.");
            }
        }

        return value;
    }

    private final int lookupBitVector(final String key) {
        throw new UnsupportedOperationException("Lookup bit vector not implemented yet.");
    }

    private final int lookupEnum(final String key) {
        if (this.enumMap == null)
            throw new NullPointerException("Enum map is null for this data table column type.");

        return this.enumMap.containsKey(key) ? this.enumMap.get(key) : -1;
    }

    public static enum DataType {
        Int(0),
        Float(1),
        String(2),
        Unknown(3),
        Comment(4),
        HashString(5),
        Enum(6),
        Bool(7),
        PackedObjVars(8),
        BitVector(9);

        private final int value;

        private DataType(final int value) {
            this.value = value;
        }

        public final int getValue() {
            return value;
        }
    }
}
