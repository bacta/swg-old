package com.ocdsoft.bacta.swg.server.login.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class DeleteCharacterMessage extends SwgMessage {

    public DeleteCharacterMessage() {
        super(0xffffd031, 0x2e87a);
        
    }
    /**
         00 09 00 01 03 00 31 D0 7A E8 02 00 00 00 01 00
    00 00 01 00 00 00 00 72 BD 

     */
}
