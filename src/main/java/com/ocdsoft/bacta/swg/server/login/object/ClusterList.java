package com.ocdsoft.bacta.swg.server.login.object;

import com.google.gson.internal.LinkedTreeMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import com.ocdsoft.bacta.swg.network.swg.object.ClusterInfo;
import com.ocdsoft.network.client.ConnectionState;
import com.ocdsoft.network.data.DatabaseConnector;

import java.util.TreeMap;

@Singleton
public class ClusterList extends TreeMap<String, ClusterInfo> implements SoeByteBufSerializable {

    private transient final DatabaseConnector dbConnector;

    @Inject
	public ClusterList(DatabaseConnector dbConnector) {

        this.dbConnector = dbConnector;

        try {
            LinkedTreeMap<String, LinkedTreeMap<String, Object>> servers = dbConnector.getAdminObject(LinkedTreeMap.class, "ClusterList");

            if (servers != null) {
                for (String key : servers.keySet()) {
                    ClusterInfo clusterInfo = new ClusterInfo(servers.get(key));
                    clusterInfo.setStatus(ConnectionState.OFFLINE);
                    super.put(clusterInfo.getName(), clusterInfo);
                }
            }
        } catch(NullPointerException e) {

        }
	}

    @Override
    public synchronized ClusterInfo put(String key, ClusterInfo clusterInfo) {

        for (String currentKey : this.keySet()) {
            ClusterInfo current = this.get(currentKey);
            if(current.getAddress().equals(clusterInfo.getAddress())) {
                remove(currentKey);
                break;
            }
        }

        if(super.put(clusterInfo.getName(), clusterInfo) == null) {
            dbConnector.updateAdminObject("ClusterList", this);
        }

        return clusterInfo;
    }

    @Override
	public synchronized void writeToBuffer(SoeByteBuf message) {
		message.writeInt(size());
		
		for (ClusterInfo clusterInfo : this.values())
			clusterInfo.writeToBuffer(message);
	}
}