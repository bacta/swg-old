package com.ocdsoft.bacta.swg.shared.container;

/**
 * Created by crush on 8/26/2014.
 */
public enum ContainerErrorCode {
    SUCCESS(0x0),
    UNKNOWN(0x1),
    ADD_SELF(0x2),
    FULL(0x3),
    SLOT_OCCUPIED(0x4),
    NO_SLOT(0x5),
    INVALID_ARRANGEMENT(0x6),
    WRONG_TYPE(0x7),
    NO_PERMISSION(0x8),
    OUT_OF_RANGE(0x9),
    NOT_FOUND(0xA),
    ALREADY_IN(0xB),
    TOO_LARGE(0xC),
    HOUSE_ITEM_LIMIT(0xD),
    TOO_DEEP(0xE),
    TRY_AGAIN(0xF),
    UNMOVABLE_TYPE(0x10),
    UNMOVABLE(0x11),
    CANT_SEE(0x12),
    INVENTORY_FULL(0x13),
    TRADE_EQUIPPED(0x14),
    HOPPER_NOT_EMPTY(0x15),
    VIRTUAL_CONTAINER_UNREACHABLE(0x16),
    VIRTUAL_CONTAINER_USER_UNREACHABLE(0x17),
    VIRTUAL_CONTAINER_USER_INVALID(0x18),
    BLOCKED_BY_SCRIPT(0x19),
    BLOCKED_BY_ITEM_BEING_TRANSFERRED(0x1A),
    BLOCKED_BY_SOURCE_CONTAINER(0x1B),
    BLOCKED_BY_DESTINATION_CONTAINER(0x1C),
    NO_CONTAINER(0x1D),
    SILENT_ERROR(0x1E),
    BIO_LINKED_TO_OTHER_PLAYER(0x1F);

    private final int value;

    ContainerErrorCode(final int value) {
        this.value = value;
    }
}
