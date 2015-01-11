package com.ocdsoft.bacta.swg.shared.iff;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;

/**
 * Created by crush on 2/12/14.
 */
public interface IffReader<T> {
    /**
     * Reads an array of bytes in IFF format, and produces an object from it.
     *
     * @param buffer An array of bytes wrapped by a {@link ChunkReader}.
     * @return
     */
    T read(ChunkReader buffer);
}
