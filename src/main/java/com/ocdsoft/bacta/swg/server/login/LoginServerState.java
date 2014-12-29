package com.ocdsoft.bacta.swg.server.login;


import com.ocdsoft.bacta.swg.network.soe.ServerState;
import com.ocdsoft.bacta.swg.network.swg.ServerType;

/**
 * Created by Kyle on 3/22/14.
 */
public class LoginServerState extends ServerState {

    public LoginServerState() {
        super(ServerType.LOGIN);
    }
}

