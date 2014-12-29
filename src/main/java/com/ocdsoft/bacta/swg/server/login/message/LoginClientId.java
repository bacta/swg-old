package com.ocdsoft.bacta.swg.server.login.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class LoginClientId extends SwgMessage {

    public LoginClientId() {
        super(0x1f96, 0x44113);

        //string id
        //string key
        //string version

    }
    /**
     00 09 00 00 04 00 96 1F 13 41 04 00 6B 79 6C 65
     04 00 6B 79 6C 65 0E 00 32 30 30 35 30 34 30 38
     2D 31 38 3A 30 30 00 10 D3

     */
}
