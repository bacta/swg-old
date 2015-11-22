package com.ocdsoft.bacta.swg.foundation;

import bacta.iff.Iff;
import gnu.trove.list.TIntList;

import java.util.List;

/**
 * Created by crush on 11/22/2015.
 */
public class CrcStringTable {
    private static final int TAG_CSTB = Iff.createChunkId("CSTB");
    private static final int TAG_CRCT = Iff.createChunkId("CRCT");
    private static final int TAG_STNG = Iff.createChunkId("STNG");
    private static final int TAG_STRT = Iff.createChunkId("STRT");

    private int numberOfEntries;
    private TIntList crcTable;
    private TIntList stringsOffsetTable;
    private List<String> strings;

    public CrcStringTable() {
    }

    public CrcStringTable(final String fileName) {
    }

    public void load(final String fileName) {
    }

    public void load(final Iff iff) {
    }

    public ConstCharCrcString lookUp(final String string) {
        return null;
    }

    public ConstCharCrcString lookUp(final int crc) {
        return null;
    }

    public List<String> getAllStrings() {
        return null;
    }

    public int getNumberOfStrings() {
        return 0;
    }

    public ConstCharCrcString getString(int index) {
        return null;
    }

    private void load0000(final Iff iff) {
    }
}
