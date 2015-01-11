package com.ocdsoft.bacta.swg.controller;

import com.ocdsoft.bacta.engine.network.controller.MessageController;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;

import java.nio.ByteBuffer;

public interface SwgMessageController<Client extends SoeUdpConnection> extends MessageController<Client, ByteBuffer> {

}
