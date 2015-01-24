package com.ocdsoft.bacta.swg.controller;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public interface SwgMessageController<Client extends SoeUdpConnection, Data extends GameNetworkMessage>  {
    void handleIncoming(Client client, Data message) throws Exception;
}
