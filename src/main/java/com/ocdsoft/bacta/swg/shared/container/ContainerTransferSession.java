package com.ocdsoft.bacta.swg.shared.container;

import com.ocdsoft.bacta.swg.shared.object.GameObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by crush on 4/29/2016.
 * <p>
 * Encapsulates all the data required for a transfer session.
 */
public abstract class ContainerTransferSession<T extends GameObject> {
    /**
     * The destination object to which the item will be transferred.
     */
    @Getter
    private final T destination;
    /**
     * The object making the transfer.
     */
    @Getter
    private final T transferer;
    /**
     * The item being transferred at the top level.
     */
    @Getter
    private final T item;
    /**
     * The last error code that was recorded.
     */
    @Getter
    @Setter
    private ContainerErrorCode errorCode;

    /**
     * Creates a new ContainerTransferSession.
     *
     * @param destination The destination object to which to the item will be transferred.
     * @param transferer  The initiator of the transfer request.
     * @param item        The item that is being transferred.
     */
    public ContainerTransferSession(final T destination,
                                    final T transferer,
                                    final T item) {
        this.destination = destination;
        this.transferer = transferer;
        this.item = item;
    }
}
