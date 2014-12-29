package com.ocdsoft.bacta.swg.message;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Kyle on 3/26/14.
 */
public class SwgMessage extends SoeByteBuf {

    @Getter
    @Setter
    private short sequenceNumber;

    public SwgMessage(int priority, int opname) {
        super();
        writeShort(priority);
        writeInt(opname);
    }

    public SwgMessage(ByteBuf copyBuffer) {
        super(copyBuffer);
    }
}
