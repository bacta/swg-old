package com.ocdsoft.bacta.swg.shared.object.crc;

import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBuffer;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.tre.TreeFile;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by crush on 8/13/2014.
 *
 * This is a generic structure. There are many CrcStringTables
 */
public class CrcStringTable {
    private static final Logger logger = LoggerFactory.getLogger(CrcStringTable.class);

    private static final int ID_CSTB = ChunkBuffer.createChunkId("CSTB");
    private static final int ID_0000 = ChunkBuffer.createChunkId("0000");

    private TIntObjectMap<ConstCharCrcString> crcStringTable;
    private final TreeFile treeFile;

    private int numberOfEntries = 0;

    public CrcStringTable(TreeFile treeFile) {
        this.treeFile = treeFile;
    }

    public Collection<ConstCharCrcString> getAllStrings() {
        return crcStringTable.valueCollection();
    }

    public ConstCharCrcString lookUp(final String templatePath) {
        return lookUp(SOECRC32.hashCode(templatePath));
    }

    public ConstCharCrcString lookUp(final int crc) {
        return crcStringTable.get(crc);
    }

    public void load(final String tablePath) {
        final ChunkReader reader = new ChunkReader(tablePath, treeFile.open(tablePath));

        reader.openForm(ID_CSTB);
        {
            reader.openForm(ID_0000);
            {
                reader.openChunk(); //DATA
                {
                    numberOfEntries = reader.readInt();
                    crcStringTable = new TIntObjectHashMap<>(numberOfEntries);
                }
                reader.nextChunk(); //CRCT - We are skipping these because we don't need them...
                reader.nextChunk(); //STRT
                reader.nextChunk(); //STNG //stringTable
                {
                    for (int i = 0; i < numberOfEntries; i++) {
                        final ConstCharCrcString crcString = new ConstCharCrcString(reader.readNullTerminatedAscii());
                        crcStringTable.put(crcString.getCrc(), crcString);
                    }

                    logger.info("Added {} entries to the crc string table.", numberOfEntries);
                }
                reader.closeChunk();
            }
            reader.closeChunk();
        }
        reader.closeChunk();
    }
}
