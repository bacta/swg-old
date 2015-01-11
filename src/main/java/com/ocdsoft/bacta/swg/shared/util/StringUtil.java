package com.ocdsoft.bacta.swg.shared.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	/**
	 * Attempts to get a null-terminated string from a {@link java.nio.ByteBuffer}. This method starts from the buffers current
	 * position, and adds each character until it encounters a <code>NUL</code> byte (0). The buffer will throw an
	 * exception
	 * @param buffer
	 * @return the String that was read from the ByteBuffer.
	 * @throws {@link java.nio.BufferUnderflowException} if there is no null terminating byte before the end of the buffer.
	 */
	public static String getNullTerminatedString(ByteBuffer buffer) {
		StringBuilder builder = new StringBuilder();
		byte b = 0;

		while ((b = buffer.get()) != 0)
			builder.append((char) b);

		return builder.toString();
	}
	/**
	 * This function is used to parse a list of null terminated strings from a {@link java.nio.ByteBuffer} when you don't know
	 * how many strings are actually in the buffer. When the list is returned, the ByteBuffer will be advanced to
	 * offset + size position. The returned list of strings will have it's capacity reduced to the number of items that
	 * were parsed from the ByteBuffer via {@link java.util.ArrayList#trimToSize()}.
	 * @param buffer  The {@link java.nio.ByteBuffer} containing the string data and advanced to the position of the first string.
	 * @param size    The total size of data to be iterated over. If there is data in the buffer beyond size + current position,
	 *                then it will not be processed.
	 * @return a List<String> with all the strings that were processed from the buffer. The ByteBuffer's position will be
	 *         advanced to offset + size; the size of the list will be equal to the number of strings.
	 * @throws {@link java.nio.BufferUnderflowException} if the there is no null terminating byte before the end of the last string
	 *         in the buffer.
	 */
	public static List<String> getNullTerminatedStringList(ByteBuffer buffer, int size) {
		List<String> strings = new ArrayList<String>();
		
		while (buffer.position() < size) {
			//It is cheaper to initialize a new StringBuilder each time, than to reset it.
			StringBuilder builder = new StringBuilder();
			byte b = 0;
			
			while ((b = buffer.get()) != 0)
				builder.append((char) b);
			
			strings.add(builder.toString());
		}
		
		((ArrayList<String>) strings).trimToSize();
		
		return strings;
	}

    public static String renderByte(byte b) {
        return "test";
    }
}
