package com.ocdsoft.bacta.swg.server.login.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.BactaController;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.client.SoeUdpClient;
import com.ocdsoft.bacta.swg.network.soe.controller.BactaMessageController;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.object.ClusterInfo;
import com.ocdsoft.bacta.swg.server.login.message.bacta.GameServerStatusUpdateResponse;
import com.ocdsoft.bacta.swg.server.login.object.ClusterList;
import com.ocdsoft.network.client.ConnectionState;
import com.ocdsoft.network.data.DatabaseConnector;
import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;

@BactaController(server= ServerType.LOGIN, command=BactaController.GAMESERVERUPDATE)
public class GameServerStatusUpdateController extends BactaMessageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Inject
    private ClusterList clusterList;

    @Inject
    private DatabaseConnector dbConnector;

    @Override
    public void handleIncoming(SoeUdpClient client, SoeByteBuf message) throws Exception {

        String secret = message.readAscii();
        String name = message.readAscii();
        String address = message.readAscii();

        ClusterInfo currentServer = null;

        for(ClusterInfo serverInfo : clusterList.values()) {
            if(serverInfo.getAddress().equals(address)) {

                if(serverInfo.getSecret().equals(secret)) {
                    currentServer = serverInfo;
                    break;
                } else {
                    logger.warn("Invalid secret for " + address + " Expecting: " + serverInfo.getSecret() + " Actual: " + secret) ;
                    client.sendMessage(new GameServerStatusUpdateResponse(GameServerStatusUpdateResponse.INVALID_SECRET));
                    return;
                }
            }
        }

        if(currentServer == null) {
            currentServer = new ClusterInfo();
            currentServer.setSecret(secret);
            currentServer.setId((int)dbConnector.nextClusterId());
        }

        currentServer.setName(name);
        currentServer.setAddress(address);
        currentServer.setPort(message.readInt());
        currentServer.setPingPort(message.readInt());
        currentServer.setPopulation(message.readInt());
        currentServer.setMaximumPopulation(message.readInt());
        currentServer.setMaximumCharacters(message.readInt());
        currentServer.setTimezone(message.readInt());

        int status = message.readInt();
        ConnectionState[] states = ConnectionState.values();
        for(ConnectionState state : states) {
            if(state.getValue() == status) {
                currentServer.setStatus(state);
                break;
            }
        }

        currentServer.setRecommended(message.readBoolean());
        clusterList.put(currentServer.getName(), currentServer);

        logger.trace("Server: " + currentServer.getName() + " status is " + currentServer.getStatus());

        client.sendMessage(new GameServerStatusUpdateResponse(GameServerStatusUpdateResponse.OK, currentServer.getId()));
    }
}
