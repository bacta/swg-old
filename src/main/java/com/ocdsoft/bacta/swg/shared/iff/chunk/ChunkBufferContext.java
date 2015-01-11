package com.ocdsoft.bacta.swg.shared.iff.chunk;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by crush on 2/14/14.
 */
public class ChunkBufferContext {
    @Getter
    protected final ChunkBufferContext parent;

    @Getter
    protected final int offset;

    @Getter
    @Setter
    protected int chunkId;

    @Getter
    @Setter
    protected int chunkSize;

    @Getter
    @Setter
    protected int groupId;

    public ChunkBufferContext(final ChunkReader reader, final ChunkBufferContext parent) {
        this.chunkId = ChunkBuffer.endianSwap32(reader.readInt());
        this.chunkSize = ChunkBuffer.endianSwap32(reader.readInt());

        offset = reader.readerIndex();

        if (isGroupChunk())
            this.groupId = ChunkBuffer.endianSwap32(reader.readInt());

        this.parent = parent;
    }

    public ChunkBufferContext(final ChunkBufferContext parent, final int offset) {
        this.parent = parent;
        this.offset = offset;
    }

    public boolean hasMoreChunks(int position) {
        return position + 8 <= offset + chunkSize; //Is there enough space for another chunk header?
    }

    public boolean isFormType(int formType) {
        return isFormGroup() && groupId == formType;
    }

    public boolean isChunkId(int chunkId) {
        return this.chunkId == chunkId;
    }

    public boolean isFormGroup() {
        return chunkId == ChunkBuffer.FORM;
    }

    public boolean isGroupChunk() {
        return chunkId == ChunkBuffer.FORM
                || chunkId == ChunkBuffer.CAT
                || chunkId == ChunkBuffer.LIST;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(ChunkBuffer.getChunkName(chunkId));

        if (isGroupChunk())
            builder.append(":").append(ChunkBuffer.getChunkName(groupId));

        builder.append("[").append(offset).append(":").append(chunkSize).append("]");

        return builder.toString();
    }

    public boolean hasMoreBytes(int readerIndex) {
        return chunkSize + offset > readerIndex;
    }
}
