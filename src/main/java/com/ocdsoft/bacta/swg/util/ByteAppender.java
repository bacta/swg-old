package com.ocdsoft.bacta.swg.util;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.lang.UnicodeString;
import com.ocdsoft.bacta.swg.network.swg.lang.UnsupportedTypeException;
import com.ocdsoft.network.buffer.ByteBufSerializable;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

public class ByteAppender {
    private interface TypeAppender<T> {
        void append(T value, io.netty.buffer.ByteBuf buffer);
    }

    private static final Map<Class<?>, TypeAppender<?>> appenders = new HashMap<Class<?>, TypeAppender<?>>();
    private static final TypeAppender<ByteBufSerializable> messageWritableAppender = new TypeAppender<ByteBufSerializable>() {
        @Override
        public void append(ByteBufSerializable value, io.netty.buffer.ByteBuf buffer) {
            SoeByteBuf message = new SoeByteBuf(buffer) {
            }; //TODO remove this dirty hack.

            value.writeToBuffer(message);
        }
    };

    static {
        appenders.put(Boolean.class, new TypeAppender<Boolean>() {
            @Override
            public void append(Boolean value, io.netty.buffer.ByteBuf buffer) {
                buffer.writeBoolean(value);
            }
        });

        appenders.put(Byte.class, new TypeAppender<Byte>() {
            @Override
            public void append(Byte value, io.netty.buffer.ByteBuf buffer) {
                buffer.writeByte(value);
            }
        });

        appenders.put(Short.class, new TypeAppender<Short>() {
            @Override
            public void append(Short value, io.netty.buffer.ByteBuf buffer) {
                buffer.writeShort(value);
            }
        });

        appenders.put(Integer.class, new TypeAppender<Integer>() {
            @Override
            public void append(Integer value, io.netty.buffer.ByteBuf buffer) {
                buffer.writeInt(value);
            }
        });

        appenders.put(Long.class, new TypeAppender<Long>() {
            @Override
            public void append(Long value, io.netty.buffer.ByteBuf buffer) {
                buffer.writeLong(value);
            }
        });

        appenders.put(Float.class, new TypeAppender<Float>() {
            @Override
            public void append(Float value, io.netty.buffer.ByteBuf buffer) {
                buffer.writeFloat(value);
            }
        });

        appenders.put(String.class, new TypeAppender<String>() {
            @Override
            public void append(String value, io.netty.buffer.ByteBuf buffer) {
                buffer.writeShort((short) value.length());
                buffer.writeBytes(value.getBytes(CharsetUtil.ISO_8859_1));
            }
        });

        appenders.put(UnicodeString.class, new TypeAppender<UnicodeString>() {
            @Override
            public void append(UnicodeString value, io.netty.buffer.ByteBuf buffer) {
                buffer.writeInt(value.getString().length());
                buffer.writeBytes(value.getString().getBytes(CharsetUtil.UTF_16LE));
            }
        });

    }

    @SuppressWarnings("unchecked")
    public static <T> void append(T value, io.netty.buffer.ByteBuf buffer) throws UnsupportedTypeException {
        if (value instanceof ByteBufSerializable) {
            messageWritableAppender.append((ByteBufSerializable) value, buffer);
            return;
        }

        TypeAppender<T> appender = (TypeAppender<T>) appenders.get(value.getClass());

        if (appender == null)
            throw new UnsupportedTypeException(String.format("No TypeAppender found for type '%s'", value.getClass()));

        appender.append(value, buffer);
    }
}