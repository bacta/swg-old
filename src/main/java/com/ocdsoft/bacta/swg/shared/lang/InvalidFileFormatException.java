package com.ocdsoft.bacta.swg.shared.lang;

/**
 * Thrown whenever a file is expected to be of a specific format, but an alternative format is encountered.
 */
public class InvalidFileFormatException extends RuntimeException {
	private static final long serialVersionUID = -884491720409429405L;

	public InvalidFileFormatException(final String message) {
		super(message);
	}
	
	public InvalidFileFormatException() { 
		super("the format of the file was invalid or unexpected.");
	}
}
