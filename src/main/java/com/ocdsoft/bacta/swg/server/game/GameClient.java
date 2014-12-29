package com.ocdsoft.bacta.swg.server.game;

import com.ocdsoft.bacta.soe.client.SoeUdpClient;
import lombok.Getter;
import lombok.Setter;

public class GameClient extends SoeUdpClient {

    @Getter
    @Setter
    private ChatServerAgent chatServerAgent;

    @Setter
    @Getter
    private CreatureObject character;

    public GameClient() {

    }

    @Override
    public void disconnect() {
        super.disconnect();
    }

    @Override
    public void close() {
        if(character != null) {
            Zone zone = character.getZone();

            if(zone != null) {
                zone.remove(character);
            }
        }
    }
}
