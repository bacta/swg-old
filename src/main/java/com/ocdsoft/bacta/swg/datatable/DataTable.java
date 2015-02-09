package com.ocdsoft.bacta.swg.datatable;

import bacta.iff.Iff;
import com.sun.javaws.exceptions.InvalidArgumentException;
import gnu.trove.TCollections;
import gnu.trove.TFloatCollection;
import gnu.trove.TIntCollection;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by crush on 2/8/15.
 */
public final class DataTable {
    private static final Logger logger = LoggerFactory.getLogger(DataTable.class);

    private static final int ID_DTII = Iff.createChunkId("DTII");
    private static final int ID_0000 = Iff.createChunkId("0000");
    private static final int ID_0001 = Iff.createChunkId("0001");
    private static final int ID_COLS = Iff.createChunkId("COLS");
    private static final int ID_ROWS = Iff.createChunkId("ROWS");
    private static final int ID_TYPE = Iff.createChunkId("TYPE");

    @Getter private String name;
    @Getter private int numCols;
    @Getter private int numRows;

    private List<DataTableCell> cells;
    private List<String> columns;
    private List<DataTableColumnType> types;

    public DataTable(final String name, final Iff iff) {
        this.name = name;

        iff.openForm(ID_DTII);

        Iff.FormContext currentForm = iff.nextForm();

        final int version = currentForm.getName();

        if (version == ID_0000) {
            load0000(iff);
        } else if (version == ID_0001) {
            load0001(iff);
        } else {
            throw new UnsupportedOperationException(
                    String.format("Unknown DataTable version [%s].", Iff.getChunkName(version)));
        }

        iff.closeChunk(); //version
        iff.closeChunk(); //ID_DTII

        //buildColumnIndexMap();
    }

    /**
     * Creates a new instance of DataTableColumnType based on the type specification string passed into the method.
     * @param typeSpecString The type specification string consisting of the type, enum values, and default value.
     * @return DataTableColumnType representing the type specification string.
     */
    private static final DataTableColumnType getDataType(final String typeSpecString) {
        return new DataTableColumnType(typeSpecString);
    }

    /**
     * Finds the index of the column in the DataTable by name.
     * @param columnName The name of the column to find.
     * @return The index of the column, or -1 if it was not found.
     */
    public final int findColumnNumber(final String columnName) {
        for (int col = 0; col < this.numCols; col++) {
            final String column = this.columns.get(col);

            if (column.equals(columnName))
                return col;
        }

        return -1;
    }

    /**
     * Checks to see if a specific column name exists in the DataTable.
     * @param columnName The name of the column to check.
     * @return True if the column exists, otherwise false.
     */
    public final boolean doesColumnExist(final String columnName) {
        return findColumnNumber(columnName) != -1;
    }

    /**
     * Returns the name of a column at a given index.
     * @param index The index of the column name.
     * @return The name of the column at the given index.
     */
    public final String getColumnName(int index) {
        return this.columns.get(index);
    }

    /**
     * Gets a DataTableCell from a given position within the DataTable.
     * @param column The column index of the cell.
     * @param row The row index of the cell.
     * @return The DataTableCell for the given cell.
     */
    public final DataTableCell getDataTableCell(int column, int row) {
        return this.cells.get(column * row);
    }

    /**
     * Gets the type for a specified column by name.
     * @param columnName The name of the column for which to retrieve the type.
     * @return The type of the column.
     */
    public final DataTableColumnType getDataTypeForColumn(final String columnName) {
        return getDataTypeForColumn(findColumnNumber(columnName));
    }

    /**
     * Gets the type for a specified column by index.
     * @param columnIndex The index of the column for which to retrieve the type.
     * @return The type of the column.
     */
    public final DataTableColumnType getDataTypeForColumn(final int columnIndex) {
        return this.types.get(columnIndex);
    }

    /**
     * Gets all the values in the specified column as floats.
     * @param columnName The name of the column from which to retrieve values.
     * @return A collection of float values from the specified column for each row in the DataTable.
     */
    public final TFloatCollection getFloatColumn(final String columnName) {
        return getFloatColumn(findColumnNumber(columnName));
    }

    /**
     * Gets all the values in the specified column as floats.
     * @param columnIndex The index of the column from which to retrieve values.
     * @return A collection of float values from the specified column for each row in the DataTable.
     */
    public final TFloatCollection getFloatColumn(final int columnIndex) {
        if (columnIndex < 0 || columnIndex >= this.columns.size())
            throw new ArrayIndexOutOfBoundsException("Column could not be found.");

        final TFloatCollection collection = new TFloatArrayList(this.numRows);

        for (int row = 0; row < this.numRows; row++) {
            final DataTableCell cell = this.cells.get(columnIndex + row * this.numCols);
            collection.add(cell.getFloatValue());
        }

        return TCollections.unmodifiableCollection(collection);
    }

    /**
     * Gets the float value of the cell in the column specified, at the row specified.
     * @param columnName The name of the column from which to retrieve the value.
     * @param row The row from which to retrieve the value.
     * @return The float value in the cell specified by the column and row.
     */
    public final float getFloatValue(final String columnName, int row) {
        return getFloatValue(findColumnNumber(columnName), row);
    }

    /**
     * Gets the float value of the cell in the column specified, at the row specified.
     * @param columnIndex The index of the column from which to retrieve the value.
     * @param row The row from which to retrieve the value.
     * @return The float value in the cell specified by the column and row.
     */
    public final float getFloatValue(final int columnIndex, int row) {
        final DataTableCell cell = this.cells.get(columnIndex + row * this.numCols);

        return cell.getFloatValue();
    }

    /**
     * Gets the default value of a float cell for the specified column.
     * @param columnName The name of the column for which to retrieve the value.
     * @return The default value of the float cell.
     * @throws InvalidDataTypeValueException
     */
    public final float getFloatDefaultForColumn(final String columnName)
            throws InvalidDataTypeValueException {
        return getFloatDefaultForColumn(findColumnNumber(columnName));
    }

    /**
     * Gets the default value of a float cell for the specified column.
     * @param columnIndex The index of the column for which to retrieve the value.
     * @return The default value of the float cell.
     * @throws InvalidDataTypeValueException
     */
    public final float getFloatDefaultForColumn(final int columnIndex)
            throws InvalidDataTypeValueException {
        if (columnIndex < 0 || columnIndex >= this.columns.size())
            throw new ArrayIndexOutOfBoundsException("Column could not be found.");

        final DataTableColumnType dataType = this.types.get(columnIndex);

        if (!DataTableColumnType.DataType.Float.equals(dataType.getBasicType())) {
            throw new IllegalArgumentException(
                    String.format("Wrong data type for column %d", columnIndex));
        }

        return Float.parseFloat(dataType.mangleValue(""));
    }

    /**
     * Gets all the values in the specified column as ints.
     * @param columnName The name of the column from which to retrieve values.
     * @return A collection of int values from the specified column for each row in the DataTable.
     */
    public final TIntCollection getIntColumn(final String columnName) {
        return getIntColumn(findColumnNumber(columnName));
    }

    /**
     * Gets all the values in the specified column as ints.
     * @param columnIndex The index of the column from which to retrieve values.
     * @return A collection of int values from the specified column for each row in the DataTable.
     */
    public final TIntCollection getIntColumn(final int columnIndex) {
        if (columnIndex < 0 || columnIndex >= this.columns.size())
            throw new ArrayIndexOutOfBoundsException("Column could not be found.");

        final TIntCollection collection = new TIntArrayList(this.numRows);

        for (int row = 0; row < this.numRows; row++) {
            final DataTableCell cell = this.cells.get(columnIndex + row * this.numCols);
            collection.add(cell.getIntValue());
        }

        return TCollections.unmodifiableCollection(collection);
    }

    /**
     * Gets the float value of the cell in the column specified, at the row specified.
     * @param columnName The name of the column from which to retrieve the value.
     * @param row The row from which to retrieve the value.
     * @return The float value in the cell specified by the column and row.
     */
    public final int getIntValue(final String columnName, int row) {
        return getIntValue(findColumnNumber(columnName), row);
    }

    /**
     * Gets the int value of the cell in the column specified, at the row specified.
     * @param columnIndex The index of the column from which to retrieve the value.
     * @param row The row from which to retrieve the value.
     * @return The int value in the cell specified by the column and row.
     */
    public final int getIntValue(final int columnIndex, int row) {
        final DataTableCell cell = this.cells.get(columnIndex + row * this.numCols);

        return cell.getIntValue();
    }

    /**
     * Gets the default value of a int cell for the specified column.
     * @param columnName The name of the column for which to retrieve the value.
     * @return The default value of the int cell.
     * @throws InvalidDataTypeValueException
     */
    public final int getIntDefaultForColumn(final String columnName)
            throws InvalidDataTypeValueException {
        return getIntDefaultForColumn(findColumnNumber(columnName));
    }

    /**
     * Gets the default value of a int cell for the specified column.
     * @param columnIndex The index of the column for which to retrieve the value.
     * @return The default value of the int cell.
     * @throws InvalidDataTypeValueException
     */
    public final int getIntDefaultForColumn(final int columnIndex)
            throws InvalidDataTypeValueException {
        if (columnIndex < 0 || columnIndex >= this.columns.size())
            throw new ArrayIndexOutOfBoundsException("Column could not be found.");

        final DataTableColumnType dataType = this.types.get(columnIndex);

        if (!DataTableColumnType.DataType.Int.equals(dataType.getBasicType())) {
            throw new IllegalArgumentException(
                    String.format("Wrong data type for column %d", columnIndex));
        }

        return Integer.parseInt(dataType.mangleValue(""));
    }

    /**
     * Gets all the values in the specified column as strings.
     * @param columnName The name of the column from which to retrieve values.
     * @return A collection of string values from the specified column for each row in the DataTable.
     */
    public final Collection<String> getStringColumn(final String columnName) {
        return getStringColumn(findColumnNumber(columnName));
    }

    /**
     * Gets all the values in the specified column as strings.
     * @param columnIndex The index of the column from which to retrieve values.
     * @return A collection of string values from the specified column for each row in the DataTable.
     */
    public final Collection<String> getStringColumn(final int columnIndex) {
        if (columnIndex < 0 || columnIndex >= this.columns.size())
            throw new ArrayIndexOutOfBoundsException("Column could not be found.");

        final Collection<String> collection = new ArrayList<>(this.numRows);

        for (int row = 0; row < this.numRows; row++) {
            final DataTableCell cell = this.cells.get(columnIndex + row * this.numCols);
            collection.add(cell.getStringValue());
        }

        return Collections.unmodifiableCollection(collection);
    }

    /**
     * Gets the float value of the cell in the column specified, at the row specified.
     * @param columnName The name of the column from which to retrieve the value.
     * @param row The row from which to retrieve the value.
     * @return The float value in the cell specified by the column and row.
     */
    public final String getStringValue(final String columnName, int row) {
        return getStringValue(findColumnNumber(columnName), row);
    }

    /**
     * Gets the string value of the cell in the column specified, at the row specified.
     * @param columnIndex The index of the column from which to retrieve the value.
     * @param row The row from which to retrieve the value.
     * @return The string value in the cell specified by the column and row.
     */
    public final String getStringValue(final int columnIndex, int row) {
        final DataTableCell cell = this.cells.get(columnIndex + row * this.numCols);

        return cell.getStringValue();
    }

    /**
     * Gets the default value of a string cell for the specified column.
     * @param columnName The name of the column for which to retrieve the value.
     * @return The default value of the string cell.
     * @throws InvalidDataTypeValueException
     */
    public final String getStringDefaultForColumn(final String columnName)
            throws InvalidDataTypeValueException {
        return getStringDefaultForColumn(findColumnNumber(columnName));
    }

    /**
     * Gets the default value of a string cell for the specified column.
     * @param columnIndex The index of the column for which to retrieve the value.
     * @return The default value of the string cell.
     * @throws InvalidDataTypeValueException
     */
    public final String getStringDefaultForColumn(final int columnIndex)
            throws InvalidDataTypeValueException {
        if (columnIndex < 0 || columnIndex >= this.columns.size())
            throw new ArrayIndexOutOfBoundsException("Column could not be found.");

        final DataTableColumnType dataType = this.types.get(columnIndex);

        if (!DataTableColumnType.DataType.String.equals(dataType.getBasicType())) {
            throw new IllegalArgumentException(
                    String.format("Wrong data type for column %d", columnIndex));
        }

        return dataType.mangleValue("");
    }

    private final void load0000(Iff iff) {
        logger.error("Unsupported data table of type 0000 [{}]", name);

        iff.openChunk(ID_COLS);

        this.numCols = iff.readInt();

        this.columns = new ArrayList<>(this.numCols);

        for (int col = 0; col < this.numCols; col++) {
            this.columns.add(iff.readString());
        }

        iff.closeChunk(); //ID_COLS
        iff.openChunk(ID_TYPE);

        //float = 1
        //string = ??
        //int = ??

        iff.closeChunk(); //ID_TYPE
        iff.openChunk(ID_ROWS);

        iff.closeChunk(); //ID_ROWS
    }

    private final void load0001(Iff iff) {
        iff.openChunk(ID_COLS);

        this.numCols = iff.readInt();

        this.columns = new ArrayList<>(this.numCols);

        for (int col = 0; col < this.numCols; col++) {
            this.columns.add(iff.readString());
        }

        iff.closeChunk(); //ID_COLS
        iff.openChunk(ID_TYPE);

        this.types = new ArrayList<>(this.numCols);

        for (int col = 0; col < this.numCols; col++) {
            this.types.add(new DataTableColumnType(iff.readString()));
        }

        iff.closeChunk(); //ID_TYPE
        iff.openChunk(ID_ROWS);

        this.numRows = iff.readInt();

        this.cells = new ArrayList<DataTableCell>(this.numRows * this.numCols);

        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                readCell(iff, col, row);
            }
        }

        iff.closeChunk(); //ID_ROWS
    }

    private final void readCell(Iff iff, int column, int row) {
        final DataTableColumnType columnType = this.types.get(column);
        final DataTableCell cell;

        switch (columnType.getBasicType()) {
            case Int:
                cell = new DataTableIntCell(iff.readInt());
                break;
            case Float:
                cell = new DataTableFloatCell(iff.readFloat());
                break;
            case String:
                cell = new DataTableStringCell(iff.readString());
                break;
            default:
                throw new UnsupportedOperationException(String.format("Unknown basic type of cell [%d].",
                        columnType.getBasicType()));
        }

        int index = column + row * this.numCols;

        //If the cell is already in the list somehow, then overwrite it.
        if (this.cells.size() >= index) {
            this.cells.set(index, cell);
        } else {
            this.cells.add(cell);
        }
    }
}
