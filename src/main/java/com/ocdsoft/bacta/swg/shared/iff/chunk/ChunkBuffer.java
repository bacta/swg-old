package com.ocdsoft.bacta.swg.shared.iff.chunk;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 2/14/14.
 */
public abstract class ChunkBuffer {
    public static final int FORM = createChunkId("FORM");
    public static final int PROP = createChunkId("PROP");
    public static final int LIST = createChunkId("LIST");
    public static final int CAT = createChunkId("CAT ");
    public static final int FILLER = createChunkId("    ");

    protected static final int SIZE_NOT_KNOWN = 0x80000001;

    protected static final int CHUNK_HEADER_SIZE = 8;

    protected final ByteBuf buffer;

    @Getter
    protected final String fileName;

    @Getter
    protected ChunkBufferContext currentContext = null;

    public ChunkBuffer(ByteBuf buffer) {
        this("", buffer);
    }

    public ChunkBuffer(final String fileName, ByteBuf buffer) {
        this.buffer = buffer;
        this.fileName = fileName;
    }

    public abstract byte[] toByteArray();

    public static int endianSwap32(int val) {
        return (((val & 0x000000ff) << 24) +
                ((val & 0x0000ff00) << 8) +
                ((val & 0x00ff0000) >> 8) +
                ((val >> 24) & 0x000000ff));
    }

    public static final boolean isGroupChunkId(final int id) {
        return id == LIST || id == FORM || id == CAT;
    }

    public static final int createChunkId(final String id) {
        final byte[] b = id.getBytes();
        return ((b[0] << 24) | (b[1] << 16) | (b[2] << 8) | b[3]);
    }

    public static final String getChunkName(final int id) {
        return new String(ByteBuffer.allocate(4).putInt(id).array());
    }
}
