package com.ocdsoft.bacta.swg.server.login;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.ocdsoft.bacta.swg.network.soe.ServerState;
import com.ocdsoft.bacta.swg.server.login.object.ClusterList;

public class LoginModule extends AbstractModule implements Module {

	@Override
	protected void configure() {

        install(new FactoryModuleBuilder()
                .build(LoginTransceiverFactory.class));

        bind(ServerState.class).to(LoginServerState.class);
        bind(ClusterList.class).asEagerSingleton();

	}

}
