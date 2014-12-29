package com.ocdsoft.bacta.swg.router;

import com.ocdsoft.bacta.swg.network.swg.ServerType;

public interface SwgMessageRouterFactory {
	SwgDevelopMessageRouter create(ServerType serverEnv);
}
