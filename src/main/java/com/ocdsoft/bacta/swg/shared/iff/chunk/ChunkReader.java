package com.ocdsoft.bacta.swg.shared.iff.chunk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crush on 2/17/14.
 */
public class ChunkReader extends ChunkBuffer {
    public boolean getBoolean(int index) {
        return buffer.getBoolean(index);
    }

    public byte getByte(int index) {
        return buffer.getByte(index);
    }

    public short getUnsignedByte(int index) {
        return buffer.getUnsignedByte(index);
    }

    public short getShort(int index) {
        return buffer.getShort(index);
    }

    public int getUnsignedShort(int index) {
        return buffer.getUnsignedShort(index);
    }

    public int getMedium(int index) {
        return buffer.getMedium(index);
    }

    public int getUnsignedMedium(int index) {
        return buffer.getUnsignedMedium(index);
    }

    public int getInt(int index) {
        return buffer.getInt(index);
    }

    public long getUnsignedInt(int index) {
        return buffer.getUnsignedInt(index);
    }

    public long getLong(int index) {
        return buffer.getLong(index);
    }

    public char getChar(int index) {
        return buffer.getChar(index);
    }

    public float getFloat(int index) {
        return buffer.getFloat(index);
    }

    public double getDouble(int index) {
        return buffer.getDouble(index);
    }

    public ChunkReader getBytes(int index, ByteBuf dst) {
        buffer.getBytes(index, dst);
        return this;
    }

    public ChunkReader getBytes(int index, ByteBuf dst, int length) {
        buffer.getBytes(index, dst, length);
        return this;
    }

    public ChunkReader getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        buffer.getBytes(index, dst, dstIndex, length);
        return this;
    }

    public ChunkReader getBytes(int index, byte[] dst) {
        buffer.getBytes(index, dst);
        return this;
    }

    public ChunkReader getBytes(int index, byte[] dst, int dstIndex, int length) {
        buffer.getBytes(index, dst, dstIndex, length);
        return this;
    }

    public ChunkReader getBytes(int index, ByteBuffer dst) {
        buffer.getBytes(index, dst);
        return this;
    }

    public boolean readBoolean() {
        return buffer.readBoolean();
    }

    public byte readByte() {
        return buffer.readByte();
    }

    public short readUnsignedByte() {
        return buffer.readUnsignedByte();
    }

    public short readShort() {
        return buffer.readShort();
    }

    public int readUnsignedShort() {
        return buffer.readUnsignedShort();
    }

    public int readMedium() {
        return buffer.readMedium();
    }

    public int readUnsignedMedium() {
        return buffer.readUnsignedMedium();
    }

    public long readUnsignedInt() {
        return buffer.readUnsignedInt();
    }

    public int readInt() {
        return buffer.readInt();
    }

    public long readLong() {
        return buffer.readLong();
    }

    public float readFloat() {
        return buffer.readFloat();
    }

    public char readChar() {
        return buffer.readChar();
    }

    public double readDouble() {
        return buffer.readDouble();
    }

    public ByteBuf readBytes(int length) {
        return buffer.readBytes(length);
    }

    public ByteBuf readSlice(int length) {
        return buffer.readSlice(length);
    }

    public ByteBuf readBytes(ByteBuf dst) {
        return buffer.readBytes(dst);
    }

    public ByteBuf readBytes(ByteBuf dst, int length) {
        return buffer.readBytes(dst, length);
    }

    public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
        return buffer.readBytes(dst, dstIndex, length);
    }

    public ByteBuf readBytes(byte[] dst) {
        return buffer.readBytes(dst);
    }

    public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
        return buffer.readBytes(dst, dstIndex, length);
    }

    public ByteBuf readBytes(ByteBuffer dst) {
        return buffer.readBytes(dst);
    }

    public ByteBuf readBytes(OutputStream out, int length) throws IOException {
        return buffer.readBytes(out, length);
    }

    public int readBytes(GatheringByteChannel out, int length) throws IOException {
        return buffer.readBytes(out, length);
    }

    public ByteBuf skipBytes(int length) {
        return buffer.skipBytes(length);
    }

    public ByteBuf copy() {
        return buffer.copy();
    }

    public ByteBuf copy(int index, int length) {
        return buffer.copy(index, length);
    }

    public ByteBuf slice() {
        return buffer.slice();
    }

    public ByteBuf slice(int index, int length) {
        return buffer.slice(index, length);
    }

    public ByteBuf duplicate() {
        return buffer.duplicate();
    }

    public String readNullTerminatedAscii() {
        final StringBuilder builder = new StringBuilder();
        byte b = 0;

        while ((b = buffer.readByte()) != 0)
            builder.append((char) b);

        return builder.toString();
    }

    /**
     * Will read null terminated strings until the end of the current chunk context is reached. In order to read
     * a specific number of null terminated strings as a list, see {@link #readNullTerminatedAsciiList(int limit)}.
     *
     * @return A list of all the null terminated strings remaining in the current chunk context. The chunk context
     * will be at the end of its content when this operation completes.
     */
    public List<String> readNullTerminatedAsciiList() {
        return readNullTerminatedAsciiList(Integer.MAX_VALUE);
    }

    /**
     * Will read null terminated strings up to the specified limit or the end of the current chunk context is reached.
     *
     * @param limit How many strings should be read from the current chunk context.
     * @return A list of null terminated strings. The size of the list will either be equal to limit or however many
     * strings were in the current chunk context before the end of its content was reached.
     */
    public List<String> readNullTerminatedAsciiList(int limit) {
        final List<String> list = new ArrayList<>();

        for (int i = 0; i < limit && (buffer.readerIndex() < currentContext.getChunkSize() + currentContext.getOffset()); i++) {
            list.add(readNullTerminatedAscii());
        }

        return list;
    }

    public ChunkReader(final String fileName, final ByteBuf buffer) {
        //Ensure that the buffer is unmodifiable and in little endian.
        super(fileName, Unpooled.unmodifiableBuffer(
                Unpooled.wrappedBuffer(buffer).order(ByteOrder.LITTLE_ENDIAN)));
    }

    public ChunkReader(final String fileName, final ByteBuffer buffer) {
        super(fileName, Unpooled.unmodifiableBuffer(
                Unpooled.wrappedBuffer(buffer).order(ByteOrder.LITTLE_ENDIAN)));
    }

    public ChunkReader(final String fileName, final byte[] bytes) {
        super(fileName, Unpooled.unmodifiableBuffer(
                Unpooled.wrappedBuffer(bytes).order(ByteOrder.LITTLE_ENDIAN)));
    }

    public boolean openForm(int formType) {
        ChunkBufferContext context = openChunk();

        if (context == null)
            return false;

        return true;
    }

    public boolean openChunk(int chunkId) {
        ChunkBufferContext context = openChunk();

        if (context == null || context.getChunkId() != chunkId)
            return false;

        return true;
    }

    public boolean hasMoreChunks() {
        if (currentContext == null)
            return buffer.readableBytes() > CHUNK_HEADER_SIZE;

        return buffer.readerIndex() < currentContext.getChunkSize() + currentContext.getOffset();
    }

    public int remainingBytesInCurrentContext() {
        if (currentContext == null)
            return buffer.readableBytes();

        return currentContext.getChunkSize() + currentContext.getOffset() - buffer.readerIndex();
    }

    public int getRootFormType() {
        if (buffer.readableBytes() < 8)
            throw new InvalidGroupChunkException("No root group chunk is available.");

        int position = buffer.readerIndex();
        buffer.readerIndex(8); //Form type would have to be here if it exists.

        int formType = endianSwap32(buffer.readInt());

        buffer.readerIndex(position);

        return formType;
    }

    public void skipCurrentChunk() {
        if (currentContext == null)
            return;

        buffer.readerIndex(currentContext.getOffset() + currentContext.getChunkSize());
    }

    public ChunkBufferContext openChunk() {
        if (!hasMoreChunks())
            return null;

        int chunkId = endianSwap32(buffer.readInt());
        int chunkSize = endianSwap32(buffer.readInt());

        ChunkBufferContext context = new ChunkBufferContext(currentContext, buffer.readerIndex());
        context.setChunkId(chunkId);
        context.setChunkSize(chunkSize);

        //If it's a group chunk, then parse the group id out.
        if (chunkId == FORM || chunkId == CAT || chunkId == LIST) {
            if (remainingBytesInCurrentContext() + 4 <= 0) {
                throw new InvalidGroupChunkException("Group chunk is missing a group id.");
            }

            context.setGroupId(endianSwap32(buffer.readInt()));
        }

        currentContext = context;

        return context;
    }

    public void closeChunk() {
        if (currentContext == null)
            return;

        //Set the buffer to the end of the current chunk.
        buffer.readerIndex(currentContext.getOffset() + currentContext.getChunkSize());

        currentContext = currentContext.getParent();
    }

    /**
     * Returns the next chunk from the buffer. It could be a group chunk or a normal chunk.
     * If it is the last chunk in the current group, then the next chunk in the parent chunk will be returned.
     *
     * @return Returns the next chunk in the buffer.
     */
    public ChunkBufferContext nextChunk() {
        if (buffer.readableBytes() <= 0)
            return null;

        if (currentContext != null) {
            if (!currentContext.isGroupChunk() || !currentContext.hasMoreChunks(buffer.readerIndex())) {
                buffer.readerIndex(currentContext.getOffset() + currentContext.getChunkSize());
                currentContext = currentContext.getParent();

                return nextChunk();
            }
        }

        ChunkBufferContext context = new ChunkBufferContext(this, currentContext);
        currentContext = context;

        return context;
    }

    public int readerIndex() {
        return buffer.readerIndex();
    }

    public byte[] toByteArray() {
        byte[] bytes = new byte[buffer.writerIndex()];
        buffer.getBytes(0, bytes);
        return bytes;
    }

    public void printStructure() {
        final ChunkBufferContext originalContext = currentContext;
        final int originalIndex = buffer.readerIndex();

        buffer.readerIndex(0);

        int depth = 0;
        while (buffer.readableBytes() > 0) {
            ChunkBufferContext context = openChunk();

            if (context != null) {
                for (int i = 0; i < depth; i++)
                    System.out.print("-");

                System.out.println(context.toString());

                depth++;

                if (!context.isFormGroup()) {
                    depth--;
                    closeChunk();
                }
            } else {
                depth--;
                closeChunk();
            }
        }

        currentContext = originalContext;
        buffer.readerIndex(originalIndex); //Reset the current index.
    }
}
