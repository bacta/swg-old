package com.ocdsoft.bacta.swg.server.ping;

import com.ocdsoft.bacta.engine.network.client.UdpClient;
import com.ocdsoft.bacta.engine.network.io.udp.BasicUdpTransceiver;
import com.ocdsoft.bacta.soe.message.ping.PongMessage;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;

public final class PingTransceiver extends BasicUdpTransceiver {

    private final Map<Object, GameClient> clients;

    public PingTransceiver(InetAddress bindAddress, int port, Map<Object, GameClient> clients) {
        super(bindAddress, port);

        this.clients = clients;
    }

    @Override
    protected void receiveMessage(ByteBuffer buffer, InetSocketAddress address) {

    }

    @Override
    public void sendMessage(UdpClient udpClient, ByteBuffer msg) {
        handleOutgoing(msg);
    }

    @Override
    public void receiveMessage(UdpClient udpClient, ByteBuffer msg) {
        PongMessage pong = new PongMessage(msg.content().readInt());

        DatagramPacket response = new DatagramPacket(pong, msg.sender());
        sendMessage(client, response);
    }
}
