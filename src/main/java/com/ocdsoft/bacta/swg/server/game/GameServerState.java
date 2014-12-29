package com.ocdsoft.bacta.swg.server.game;

import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.network.soe.ServerState;
import com.ocdsoft.bacta.swg.network.swg.ServerType;

/**
 * Created by Kyle on 3/22/14.
 */
@Singleton
public class GameServerState extends ServerState {

    public GameServerState() {
        super(ServerType.GAME);
    }
}
