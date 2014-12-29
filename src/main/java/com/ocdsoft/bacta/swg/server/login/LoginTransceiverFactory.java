package com.ocdsoft.bacta.swg.server.login;

import com.google.inject.assistedinject.Assisted;

import java.net.InetAddress;

/**
 * Created by Kyle on 8/24/2014.
 */
public interface LoginTransceiverFactory {
    LoginTransceiver create(InetAddress bindAddress, @Assisted("port") int port, Class<LoginClient> clientClass, @Assisted("sendQueueInterval") int sendQueueInterval);
}
