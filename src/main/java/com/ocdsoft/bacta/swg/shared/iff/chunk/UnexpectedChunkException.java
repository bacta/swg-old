package com.ocdsoft.bacta.swg.shared.iff.chunk;

/**
 * Created by crush on 2/17/14.
 */
public class UnexpectedChunkException extends RuntimeException {
    public UnexpectedChunkException(int chunkId) {
        super("Unexpected chunk encountered with chunk id " + ChunkBuffer.getChunkName(chunkId) + ".");
    }
}
