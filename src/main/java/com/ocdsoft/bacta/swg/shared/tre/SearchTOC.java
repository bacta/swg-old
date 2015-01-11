package com.ocdsoft.bacta.swg.shared.tre;

import lombok.Getter;

/**
 * Created by crush on 3/19/14.
 */
public class SearchTOC extends SearchNode {

    public class Header {
        int token;
        int version;
        byte tocCompressor;
        byte fileNameBlockCompressor;
        byte unusedOne;
        byte unusedTwo;
        int numberOfFiles;
        int sizeOfTOC;
        int sizeOfNameBlock;
        int uncompSizeOfNameBlock;
        int numberOfTreeFiles;
        int sizeOfTreeFileNameBlock;
    }

    public class TableOfContentsEntry {
        byte compressor;
        byte unused;
        short treeFileIndex;
        int crc;
        int fileNameOffset;
        int offset;
        int length;
        int compressedLength;
    }

    @Getter
    private final String filePath;

    public SearchTOC(String filePath, int searchPriority) {
        super(searchPriority);

        this.filePath = filePath;
    }

    @Override
    public byte[] open(String filePath) {
        return null;
    }

    @Override
    public boolean exists(String filePath) {
        return false;
    }
}
