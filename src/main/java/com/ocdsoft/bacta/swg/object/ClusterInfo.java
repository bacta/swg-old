package com.ocdsoft.bacta.swg.object;

import com.google.gson.internal.LinkedTreeMap;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import com.ocdsoft.network.client.ConnectionState;
import lombok.Getter;
import lombok.Setter;

import static com.ocdsoft.network.client.ConnectionState.*;

public class ClusterInfo implements SoeByteBufSerializable, Comparable<ClusterInfo> {

    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String secret;
    @Getter
    @Setter
    private String name;
	@Getter
    @Setter
    private String address;
	@Getter
    @Setter
    private int port;
	@Getter
    @Setter
    private int pingPort;
	@Getter
    @Setter
    private int population;
	@Getter
    @Setter
    private int maximumPopulation;
	@Getter
    @Setter
    private int maximumCharacters;
	@Getter
    @Setter
    private int timezone;
	@Getter
    @Setter
    private ConnectionState status;
	@Getter
    @Setter
    private boolean recommended;

    public ClusterInfo() {}

    public ClusterInfo(LinkedTreeMap<String, Object> clusterInfo) {
        id = ((Double)clusterInfo.get("id")).intValue();
        secret = (String) clusterInfo.get("secret");
        name = (String) clusterInfo.get("name");
        address = (String) clusterInfo.get("address");
        port = ((Double)clusterInfo.get("port")).intValue();
        pingPort = ((Double)clusterInfo.get("pingPort")).intValue();
        population = ((Double)clusterInfo.get("population")).intValue();
        maximumPopulation = ((Double)clusterInfo.get("maximumPopulation")).intValue();
        maximumCharacters = ((Double)clusterInfo.get("maximumCharacters")).intValue();
        timezone = ((Double)clusterInfo.get("timezone")).intValue();
        status = ConnectionState.valueOf((String) clusterInfo.get("status"));
        recommended = (boolean) clusterInfo.get("recommended");

    }

    public boolean isOffline() { return status == OFFLINE; }
	public boolean isLoading() { return status == LOADING; }
	public boolean isOnline()  { return status == ONLINE;  }
	public boolean isLocked()  { return status == LOCKED;  }
    public boolean isRestricted()  { return status == RESTRICTED;  }
    public boolean isFull()  { return status == FULL;  }

	@Override
	public void writeToBuffer(SoeByteBuf message) {
		message.writeInt(getId());
        message.writeAscii(getName());
        message.writeAscii(getAddress());
		message.writeShort((short)getPort());
		message.writeShort((short)getPingPort());
		message.writeInt(getPopulation());
		message.writeInt(getMaximumPopulation());
		message.writeInt(getMaximumCharacters());
		message.writeInt(getTimezone());
		message.writeInt(getStatus().getValue());
		message.writeBoolean(isRecommended());
	}

    @Override
    public int compareTo(ClusterInfo o) {
        return o.getName().compareTo(getName());
    }
}
