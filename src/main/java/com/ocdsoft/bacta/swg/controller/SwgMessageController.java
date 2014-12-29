package com.ocdsoft.bacta.swg.controller;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.client.SoeUdpClient;
import com.ocdsoft.network.controller.MessageController;

public interface SwgMessageController<Client extends SoeUdpClient> extends MessageController<Client, SoeByteBuf> {

}
