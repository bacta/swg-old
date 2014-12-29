package com.ocdsoft.bacta.swg.server.ping;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration;
import com.ocdsoft.bacta.engine.serialization.NetworkSerializer;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.swg.server.ping.data.PingObjectSerializer;

public class PingModule extends AbstractModule implements Module {

	@Override
	protected void configure() {
        bind(NetworkSerializer.class).to(PingObjectSerializer.class);
        bind(ServerState.class).to(PingServerState.class);

        bind(BactaConfiguration.class).to(IniBactaConfiguration.class);

	}

}
