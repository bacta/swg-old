package com.ocdsoft.bacta.swg.shared.container;

import lombok.Getter;

/**
 * Created by crush on 4/22/2016.
 * <p>
 * An exception occurred during a container operation. Contains an error code that identifies the problem.
 */
public class ContainerException extends RuntimeException {
    @Getter
    private final ContainerErrorCode errorCode;

    public ContainerException(final ContainerErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
