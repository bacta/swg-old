package com.ocdsoft.bacta.swg.util;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.lang.UnicodeString;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.lang.UnsupportedTypeException;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class ByteAppender {
    private interface TypeAppender<T> {
        void append(T value, ByteBuffer buffer);
    }

    private static final Map<Class<?>, TypeAppender<?>> appenders = new HashMap<Class<?>, TypeAppender<?>>();
    private static final TypeAppender<ByteBufferSerializable> messageWritableAppender = new TypeAppender<ByteBufferSerializable>() {
        @Override
        public void append(ByteBufferSerializable value, ByteBuffer buffer) {
            ByteBuffer message = ByteBuffer.allocate(496); {
            }; //TODO remove this dirty hack.

            value.writeToBuffer(message);
        }
    };

    static {
        appenders.put(Boolean.class, new TypeAppender<Boolean>() {
            @Override
            public void append(Boolean value, ByteBuffer buffer) {
                BufferUtil.putBoolean(buffer, value);
            }
        });

        appenders.put(Byte.class, new TypeAppender<Byte>() {
            @Override
            public void append(Byte value, ByteBuffer buffer) {
                buffer.put(value);
            }
        });

        appenders.put(Short.class, new TypeAppender<Short>() {
            @Override
            public void append(Short value, ByteBuffer buffer) {
                buffer.putShort(value);
            }
        });

        appenders.put(Integer.class, new TypeAppender<Integer>() {
            @Override
            public void append(Integer value, ByteBuffer buffer) {
                buffer.putInt(value);
            }
        });

        appenders.put(Long.class, new TypeAppender<Long>() {
            @Override
            public void append(Long value, ByteBuffer buffer) {
                buffer.putLong(value);
            }
        });

        appenders.put(Float.class, new TypeAppender<Float>() {
            @Override
            public void append(Float value, ByteBuffer buffer) {
                buffer.putFloat(value);
            }
        });

        appenders.put(String.class, new TypeAppender<String>() {
            @Override
            public void append(String value, ByteBuffer buffer) {
                BufferUtil.putAscii(buffer, value);
            }
        });

        appenders.put(UnicodeString.class, new TypeAppender<UnicodeString>() {
            @Override
            public void append(UnicodeString value, ByteBuffer buffer) {
                BufferUtil.putUnicode(buffer, value.getString());

            }
        });

    }

    @SuppressWarnings("unchecked")
    public static <T> void append(T value, ByteBuffer buffer) throws UnsupportedTypeException {
        if (value instanceof ByteBufferSerializable) {
            messageWritableAppender.append((ByteBufferSerializable) value, buffer);
            return;
        }

        TypeAppender<T> appender = (TypeAppender<T>) appenders.get(value.getClass());

        if (appender == null)
            throw new UnsupportedTypeException(String.format("No TypeAppender found for type '%s'", value.getClass()));

        appender.append(value, buffer);
    }
}