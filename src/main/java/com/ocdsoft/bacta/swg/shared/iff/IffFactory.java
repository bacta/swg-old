package com.ocdsoft.bacta.swg.shared.iff;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBuffer;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * Created by crush on 2/22/14.
 */
public abstract class IffFactory {
    protected final TIntObjectMap<IffReader<?>> readers = new TIntObjectHashMap<>();

    public IffFactory() {
        registerReaders();
    }

    protected abstract void registerReaders();

    public Object read(final String filePath, final byte[] bytes) {
        ChunkReader chunkReader = new ChunkReader(filePath, bytes);

        int iffType = chunkReader.getRootFormType();

        IffReader<?> reader = readers.get(iffType);

        if (reader == null)
            throw new UnregisteredIffReaderException(ChunkBuffer.getChunkName(iffType));

        return reader.read(chunkReader);
    }
}
