package com.ocdsoft.bacta.swg.server.game;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.ocdsoft.bacta.swg.network.soe.ServerState;
import com.ocdsoft.bacta.swg.server.game.chat.ChatServerAgent;
import com.ocdsoft.bacta.swg.server.game.chat.ChatServerAgentFactory;
import com.ocdsoft.bacta.swg.server.game.chat.xmpp.XmppChatServerAgent;
import com.ocdsoft.bacta.swg.server.game.service.name.DefaultNameService;
import com.ocdsoft.bacta.swg.server.game.service.name.NameService;
import com.ocdsoft.bacta.swg.server.game.zone.PlanetMap;
import com.ocdsoft.bacta.swg.server.game.zone.ZoneMap;

public class GameModule extends AbstractModule implements Module {

    @Override
    protected void configure() {

        install(new FactoryModuleBuilder()
                .implement(ChatServerAgent.class, XmppChatServerAgent.class)
                .build(ChatServerAgentFactory.class));

        install(new FactoryModuleBuilder()
                .build(GameTransceiverFactory.class));

        bind(GameServerStatusUpdater.class).asEagerSingleton();

        bind(ServerState.class).to(GameServerState.class);

        bind(NameService.class).to(DefaultNameService.class);

        bind(ZoneMap.class).to(PlanetMap.class);
    }

}
