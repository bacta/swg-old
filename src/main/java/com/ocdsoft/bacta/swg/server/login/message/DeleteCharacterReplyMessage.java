package com.ocdsoft.bacta.swg.server.login.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class DeleteCharacterReplyMessage extends SwgMessage{

    public DeleteCharacterReplyMessage(boolean success) {
        super(2, 0x8268989B);

        writeInt(success ? 0 : 1);
    }
}
