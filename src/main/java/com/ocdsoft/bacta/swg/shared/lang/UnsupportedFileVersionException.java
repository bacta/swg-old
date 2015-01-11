package com.ocdsoft.bacta.swg.shared.lang;

/**
 * Thrown whenever the version specified by a file does not match the supported versions of the particular file format.
 */
public class UnsupportedFileVersionException extends RuntimeException {
	private static final long serialVersionUID = 2244508501599914414L;

	public UnsupportedFileVersionException(final String message) {
		super(message);
	}
	
	public UnsupportedFileVersionException() {
		super("an unsupported version of the file was encountered.");
	}

}
