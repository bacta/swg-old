package com.ocdsoft.bacta.swg.shared.iff.chunk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by crush on 2/17/14.
 */
public class ChunkWriter extends ChunkBuffer {
    public ChunkWriter() {
        super(Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN));
    }

    public ChunkWriter setBoolean(int index, boolean value) {
        buffer.setBoolean(index, value);
        return this;
    }

    public ChunkWriter setByte(int index, int value) {
        buffer.setByte(index, value);
        return this;
    }

    public ChunkWriter setShort(int index, int value) {
        buffer.setShort(index, value);
        return this;
    }

    public ChunkWriter setMedium(int index, int value) {
        buffer.setMedium(index, value);
        return this;
    }

    public ChunkWriter setInt(int index, int value) {
        buffer.setInt(index, value);
        return this;
    }

    public ChunkWriter setLong(int index, long value) {
        buffer.setLong(index, value);
        return this;
    }

    public ChunkWriter setChar(int index, int value) {
        buffer.setChar(index, value);
        return this;
    }

    public ChunkWriter setFloat(int index, float value) {
        buffer.setFloat(index, value);
        return this;
    }

    public ChunkWriter setDouble(int index, double value) {
        buffer.setDouble(index, value);
        return this;
    }

    public ChunkWriter setBytes(int index, ByteBuf src) {
        buffer.setBytes(index, src);
        return this;
    }

    public ChunkWriter setBytes(int index, ByteBuf src, int length) {
        buffer.setBytes(index, src, length);
        return this;
    }

    public ChunkWriter setBytes(int index, ByteBuf src, int srcIndex, int length) {
        buffer.setBytes(index, src, srcIndex, length);
        return this;
    }

    public ChunkWriter setBytes(int index, byte[] src) {
        buffer.setBytes(index, src);
        return this;
    }

    public ChunkWriter setBytes(int index, byte[] src, int srcIndex, int length) {
        buffer.setBytes(index, src, srcIndex, length);
        return this;
    }

    public ChunkWriter setBytes(int index, ByteBuffer src) {
        buffer.setBytes(index, src);
        return this;
    }

    public ChunkWriter setZero(int index, int length) {
        buffer.setZero(index, length);
        return this;
    }

    public ChunkWriter skipBytes(int length) {
        buffer.skipBytes(length);
        return this;
    }

    public ChunkWriter writeBoolean(boolean value) {
        buffer.writeBoolean(value);
        return this;
    }

    public ChunkWriter writeByte(int value) {
        buffer.writeByte(value);
        return this;
    }

    public ChunkWriter writeShort(int value) {
        buffer.writeShort(value);
        return this;
    }

    public ChunkWriter writeMedium(int value) {
        buffer.writeMedium(value);
        return this;
    }

    public ChunkWriter writeInt(int value) {
        buffer.writeInt(value);
        return this;
    }

    public ChunkWriter writeLong(long value) {
        buffer.writeLong(value);
        return this;
    }

    public ChunkWriter writeChar(int value) {
        buffer.writeChar(value);
        return this;
    }

    public ChunkWriter writeFloat(float value) {
        buffer.writeFloat(value);
        return this;
    }

    public ChunkWriter writeDouble(double value) {
        buffer.writeDouble(value);
        return this;
    }

    public ChunkWriter writeBytes(ByteBuf src) {
        buffer.writeBytes(src);
        return this;
    }

    public ChunkWriter writeBytes(ByteBuf src, int length) {
        buffer.writeBytes(src, length);
        return this;
    }

    public ChunkWriter writeBytes(ByteBuf src, int srcIndex, int length) {
        buffer.writeBytes(src, srcIndex, length);
        return this;
    }

    public ChunkWriter writeBytes(byte[] src) {
        buffer.writeBytes(src);
        return this;
    }

    public ChunkWriter writeBytes(byte[] src, int srcIndex, int length) {
        buffer.writeBytes(src, srcIndex, length);
        return this;
    }

    public ChunkWriter writeBytes(ByteBuffer src) {
        buffer.writeBytes(src);
        return this;
    }

    public ChunkWriter writeZero(int length) {
        buffer.writeZero(length);
        return this;
    }

    public ChunkWriter copy() {
        buffer.copy();
        return this;
    }

    public ChunkWriter copy(int index, int length) {
        buffer.copy(index, length);
        return this;
    }

    public ChunkWriter slice() {
        buffer.slice();
        return this;
    }

    public ChunkWriter slice(int index, int length) {
        buffer.slice(index, length);
        return this;
    }

    public ChunkWriter duplicate() {
        buffer.duplicate();
        return this;
    }

    public ChunkWriter writeAscii(final String value) {
        writeBytes(value.getBytes(CharsetUtil.US_ASCII));
        return this;
    }

    public ChunkWriter writeNullTerminatedAscii(final String value) {
        writeAscii(value);
        writeByte(0);
        return this;
    }

    public void openForm(int formType) {
        openGroup(FORM, formType);
    }

    public void openForm(int formType, int chunkSize) {
        openGroup(FORM, chunkSize, formType);
    }

    public void openGroup(int chunkId, int groupId) {
        openGroup(chunkId, SIZE_NOT_KNOWN, groupId);
    }

    public void openGroup(int chunkId, int chunkSize, int groupId) {
        openChunk(chunkId, chunkSize);
        writeInt(endianSwap32(groupId));
    }

    public void openChunk(int chunkId) {
        openChunk(chunkId, SIZE_NOT_KNOWN);
    }

    public void openChunk(int chunkId, int chunkSize) {
        currentContext = new ChunkBufferContext(currentContext, buffer.writerIndex());

        writeInt(endianSwap32(chunkId));
        writeInt(endianSwap32(chunkSize));
    }

    public void closeChunk() {
        int bytes = buffer.writerIndex() - currentContext.getOffset();

        if ((bytes & 1) == 1) {
            writeByte(0); //Add padding if the size is odd.
            bytes += 1;
        }

        setInt(currentContext.getOffset() + 4, endianSwap32(bytes));

        currentContext = currentContext.getParent();
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.getBytes(0, bytes);
        return bytes;
    }
}
