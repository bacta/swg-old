package com.ocdsoft.bacta.swg.shared.iff;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;

/**
 * Created by crush on 8/20/2014.
 */
public interface IffLoader<T> {
    void load(T object, ChunkReader reader);
}
