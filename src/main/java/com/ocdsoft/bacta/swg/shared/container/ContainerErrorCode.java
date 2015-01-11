package com.ocdsoft.bacta.swg.shared.container;

/**
 * Created by crush on 8/26/2014.
 */
public final class ContainerErrorCode {
    public static final int Success = 0x0;
    public static final int Unknown = 0x1;
    public static final int AddSelf = 0x2;
    public static final int Full = 0x3;
    public static final int SlotOccupied = 0x4;
    public static final int NoSlot = 0x5;
    public static final int InvalidArrangement = 0x6;
    public static final int WrongType = 0x7;
    public static final int NoPermission = 0x8;
    public static final int OutOfRange = 0x9;
    public static final int NotFound = 0xA;
    public static final int AlreadyIn = 0xB;
    public static final int TooLarge = 0xC;
    public static final int HouseItemLimit = 0xD;
    public static final int TooDeep = 0xE;
    public static final int TryAgain = 0xF;
    public static final int UnmovableType = 0x10;
    public static final int Unmovable = 0x11;
    public static final int CantSee = 0x12;
    public static final int InventoryFull = 0x13;
    public static final int TradeEquipped = 0x14;
    public static final int HopperNotEmpty = 0x15;
    public static final int VirtualContainerUnreachable = 0x16;
    public static final int VirtualContainerUserUnreachable = 0x17;
    public static final int VirtualContainerUserInvalid = 0x18;
    public static final int BlockedByScript = 0x19;
    public static final int BlockedByItemBeingTransferred = 0x1A;
    public static final int BlockedBySourceContainer = 0x1B;
    public static final int BlockedByDestinationContainer = 0x1C;
    public static final int NoContainer = 0x1D;
    public static final int SilentError = 0x1E;
    public static final int BioLinkedToOtherPlayer = 0x1F;
    public static final int Last = 0x20;
}
