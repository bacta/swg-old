package com.ocdsoft.bacta.swg.shared.iff.chunk;

/**
 * Created by crush on 2/22/14.
 */
public interface GroupChunkCallback {
    void execute(ChunkReader reader, ChunkBufferContext context);
}
