package com.ocdsoft.bacta.swg.server.game;

import com.google.inject.assistedinject.Assisted;

import java.net.InetAddress;

/**
 * Created by Kyle on 8/24/2014.
 */
public interface GameTransceiverFactory {
    GameTransceiver create(InetAddress bindAddress, @Assisted("port") int port, @Assisted("pingPort") int pingPort, Class<GameClient> clientClass, @Assisted("sendQueueInterval") int sendQueueInterval);
}
