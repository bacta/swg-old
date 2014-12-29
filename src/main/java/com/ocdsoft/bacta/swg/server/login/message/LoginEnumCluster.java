package com.ocdsoft.bacta.swg.server.login.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.network.swg.object.ClusterInfo;
import com.ocdsoft.bacta.swg.server.login.object.ClusterList;

public class LoginEnumCluster extends SwgMessage {

	public LoginEnumCluster(ClusterList clusterList, int loginTimezone) {
		super(0x03, 0xC11C63B9);
 
		writeInt(clusterList.size());

		for (ClusterInfo clusterInfo : clusterList.values()) {
			writeInt(clusterInfo.getId());
			writeAscii(clusterInfo.getName());
			writeInt(clusterInfo.getTimezone());
		}

		writeInt(1000); // MaxCharactersPerAccount
	}
}
