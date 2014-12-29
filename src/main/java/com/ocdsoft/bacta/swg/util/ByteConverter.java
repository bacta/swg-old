package com.ocdsoft.bacta.swg.util;


import com.ocdsoft.bacta.swg.network.swg.lang.UnsupportedTypeException;

import java.util.HashMap;
import java.util.Map;

public class ByteConverter {
	public interface TypeToByteConverter<T> {
		byte[] convert(T value); 
	}
	
	private static final Map<Class<?>, TypeToByteConverter<?>> types = new HashMap<Class<?>, TypeToByteConverter<?>>();
	
	static {
		types.put(Short.class, new TypeToByteConverter<Short>() {
			@Override
			public byte[] convert(Short value) {
				return new byte[] {
						(byte)(value >>> 8),
						(byte)(value >>> 0)
				};
			}
		});
		
		types.put(Integer.class, new TypeToByteConverter<Integer>() {
			@Override
			public byte[] convert(Integer value) {
				return new byte[] {
						(byte)(value >>> 24),
						(byte)(value >>> 16),
						(byte)(value >>> 8),
						(byte)(value >>> 0)
				};
			}
		});
		
		types.put(Long.class, new TypeToByteConverter<Long>() {
			@Override
			public byte[] convert(Long value) {
				return new byte[] {
						(byte)(value >>> 56),
						(byte)(value >>> 48),
						(byte)(value >>> 40),
						(byte)(value >>> 32),
						(byte)(value >>> 24),
						(byte)(value >>> 16),
						(byte)(value >>> 8),
						(byte)(value >>> 0)
				};
			}
		});
		
		types.put(Float.class, new TypeToByteConverter<Float>() {
			@Override
			public byte[] convert(Float value) {
				int bits = Float.floatToRawIntBits(value);
				
				return new byte[] {
						(byte)(bits & 0xff),
						(byte)((bits >> 8) & 0xff),
						(byte)((bits >> 16) & 0xff),
						(byte)((bits >> 24) & 0xff)
				};
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public static <T> byte[] getBytes(T value) throws UnsupportedTypeException {
		TypeToByteConverter<T> typeInfo = (TypeToByteConverter<T>) types.get(value.getClass());
		
		if (typeInfo == null)
			throw new UnsupportedTypeException(String.format("The type '%s' doesn't have a registered TypeToByteConverter.", value.getClass().getSimpleName()));
		
		return typeInfo.convert(value);
	}
}
