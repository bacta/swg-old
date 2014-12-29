package com.ocdsoft.bacta.swg.server.login.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.network.swg.object.ClusterInfo;
import com.ocdsoft.bacta.swg.server.login.object.ClusterList;

public class LoginClusterStatus extends SwgMessage {
    public static final class ClusterData {
        int clusterId;
        String connectionServerAddress;
        short connectionServerPort;
        short connectionServerPingPort;
        int populationOnline;
        //PopulationStatus populationOnlineStatus; //enum
        int maxCharactersPerAccount;
        int timeZone;
        //Status status; //enum
        boolean dontRecommend;
        int onlinePlayerLimit;
        int onlineFreeTrialLimit;
    }

	public LoginClusterStatus(ClusterList clusterList, int loginTimezone) {
		super(0x02, 0x3436AEB6);
		
		writeInt(clusterList.size());
		
		for (ClusterInfo clusterInfo : clusterList.values()) {
			writeInt(clusterInfo.getId());
			writeAscii(clusterInfo.getAddress());
			writeShort(clusterInfo.getPort());
			writeShort(clusterInfo.getPingPort());
			writeInt(clusterInfo.getPopulation());
			writeInt(clusterInfo.getMaximumPopulation()); //TODO Find out what this really is.
			writeInt(clusterInfo.getMaximumCharacters()); //TODO Find out what this really is.
			writeInt(clusterInfo.getTimezone());
			writeInt(clusterInfo.getStatus().getValue());
			writeBoolean(clusterInfo.isRecommended());
		}
	}
}
