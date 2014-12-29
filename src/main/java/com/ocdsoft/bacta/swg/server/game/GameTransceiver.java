package com.ocdsoft.bacta.swg.server.game;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.io.udp.SoeTransceiver;
import com.ocdsoft.bacta.soe.protocol.SoeProtocol;
import com.ocdsoft.bacta.soe.router.SoeMessageRouter;
import com.ocdsoft.bacta.soe.router.SoeMessageRouterFactory;
import com.ocdsoft.bacta.swg.server.ping.PingServer;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by Kyle on 8/18/2014.
 */
public class GameTransceiver extends SoeTransceiver<GameClient> {

    private final SoeMessageRouter soeMessageRouter;

    @Inject
    public GameTransceiver(@Assisted InetAddress bindAddress,
                           @Assisted("port") int port,
                           @Assisted("pingPort") int pingPort,
                           @Assisted Class<GameClient> gameClientClass,
                           @Assisted("sendQueueInterval") int sendQueueInterval,
                           SoeMessageRouterFactory soeMessageRouterFactory,
                           SoeProtocol protocol) {

        super(bindAddress, port, ServerType.GAME, gameClientClass, sendQueueInterval, protocol);
        soeMessageRouter = soeMessageRouterFactory.create(ServerType.GAME);

        Thread pingThread = new Thread( new PingServer(bindAddress, pingPort, clients));
        pingThread.start();
    }

    @Override
    public void receiveMessage(GameClient client, ByteBuffer buffer) {

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
