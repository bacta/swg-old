package com.ocdsoft.bacta.swg.shared.iff.snapshot;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class WorldSnapshotNode {
    public List<WorldSnapshotNode> children = new ArrayList<WorldSnapshotNode>(0);

    //bool deleted
    //long networkIdInt
    //long containedByNetworkIdInt
    //int objectTemplateNameIndex
    //int cellIndex
    //Transform transform
    //float radius
    //unsigned int portalLayoutCrc
    //String eventName?
    //float distanceSquaredTo
    //bool inWorld


    public long objectId;
    public long parentId;
    public int nameId;
    public int cellId;

    public Quat4f orientation;
    public Vector3f position;

    float gameObjectType;
    int unknown;
}
