package com.ocdsoft.bacta.swg.shared.lang;

/**
 * This exception indicates that a method of function has not yet been implemented. Throw this exception from a method
 * in the event that the method is invoked before it has been implemented.
 */
public class NotImplementedException extends RuntimeException {
	private static final long serialVersionUID = 5143463304004626539L;

	public NotImplementedException() {
		super("the method or function is not implemented.");
	}
}
