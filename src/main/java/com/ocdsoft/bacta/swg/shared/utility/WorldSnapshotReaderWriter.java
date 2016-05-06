package com.ocdsoft.bacta.swg.shared.utility;

import bacta.iff.Iff;
import com.ocdsoft.bacta.swg.shared.foundation.CrcString;
import com.ocdsoft.bacta.swg.shared.math.Transform;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crush on 4/22/2016.
 */
public class WorldSnapshotReaderWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorldSnapshotReaderWriter.class);

    private final List<Node> nodeList = new ArrayList<>();
    private final List<String> objectTemplateNameList = new ArrayList<>();
    private final TIntIntMap objectTemplateCrcMap = new TIntIntHashMap();
    private final TLongObjectMap<Node> networkIdNodeMap = new TLongObjectHashMap<>();

    public WorldSnapshotReaderWriter() {
    }

    public void load(final Iff iff) {
    }

    public void save(final Iff iff) {
    }

    public void clear() {
    }

    public Node addObject(
            final long networkId,
            final long containedByNetworkId,
            final CrcString objectTemplateName,
            final int cellIndex,
            final Transform transformP,
            float radius,
            final int portalLayoutCrc,
            final String eventName) {

        return null;
    }

    public int getNumberOfNodes() {
        return nodeList.size();
    }

    public Node getNode(final int nodeIndex) {
        return nodeList.get(nodeIndex);
    }

    public int getNumberOfObjectTemplateNames() {
        return objectTemplateNameList.size();
    }

    public String getObjectTemplateName(final int objectTemplateNameIndex) {
        return objectTemplateNameList.get(objectTemplateNameIndex);
    }

    public int getTotalNumberOfNodes() {
        return 0;
    }

    public Node find(final long networkId) {
        return null;
    }

    public void removeNode(final long networkId) {
    }

    public void removeFromWorld() {
    }

    private void load0001(final Iff iff) {
    }

    private void preSave() {
    }

    public static class Node {
    }
}
