package com.ocdsoft.bacta.swg.server.login;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.io.udp.SoeTransceiver;
import com.ocdsoft.bacta.swg.network.soe.protocol.SoeProtocol;
import com.ocdsoft.bacta.swg.network.soe.router.SoeMessageRouterFactory;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import io.netty.buffer.ByteBuf;

import java.net.InetAddress;

/**
 * Created by Kyle on 8/18/2014.
 */
public class LoginTransceiver extends SoeTransceiver<LoginClient> {

    @Inject
    public LoginTransceiver(@Assisted InetAddress bindAddress,
                            @Assisted("port") int port,
                            @Assisted Class<LoginClient> gameClientClass,
                            @Assisted("sendQueueInterval") int sendQueueInterval,
                            SoeMessageRouterFactory soeMessageRouterFactory,
                            SoeProtocol protocol) {

        super(bindAddress, port, ServerType.LOGIN, gameClientClass, sendQueueInterval, soeMessageRouterFactory.create(ServerType.LOGIN), protocol);

    }

    @Override
    public void receiveMessage(LoginClient client, ByteBuf buffer) {

        SoeByteBuf message = new SoeByteBuf(buffer);
        boolean swgMessage = message.getByte(0) != 0;

        if(swgMessage) {
            message.skipBytes(2);
            swgRouter.routeMessage(message.readInt(), client, message);
        } else {
            soeRouter.routeMessage(message.readShortBE(), client, message);
        }
    }
}
