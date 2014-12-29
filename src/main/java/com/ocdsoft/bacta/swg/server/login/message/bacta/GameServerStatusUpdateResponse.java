package com.ocdsoft.bacta.swg.server.login.message.bacta;

import com.ocdsoft.bacta.swg.network.soe.message.BactaMessage;

/**
 * Created by kburkhardt on 2/22/14.
 */
public class GameServerStatusUpdateResponse extends BactaMessage {

    public final static byte OK = 0;
    public final static byte INVALID_SECRET = 1;
    public final static byte INVALID_HOST = 2;


    public GameServerStatusUpdateResponse(byte result) {
        this(result, -1);
    }

    public GameServerStatusUpdateResponse(byte result, int serverId) {
        writeByte(result);
        writeByte(serverId);
    }

}
