package com.ocdsoft.bacta.swg.router;

import com.ocdsoft.bacta.soe.ServerType;

public interface SwgMessageRouterFactory {
	SwgDevelopMessageRouter create(ServerType serverEnv);
}
