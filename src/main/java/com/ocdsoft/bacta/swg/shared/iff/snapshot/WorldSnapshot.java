package com.ocdsoft.bacta.swg.shared.iff.snapshot;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBuffer;

import java.util.ArrayList;
import java.util.List;


public class WorldSnapshot {
    public static final int WSNP = ChunkBuffer.createChunkId("WSNP"); //WorldSnapshot
    public static final int V000 = ChunkBuffer.createChunkId("0000");
    public static final int V001 = ChunkBuffer.createChunkId("0001");
    public static final int OTNL = ChunkBuffer.createChunkId("OTNL"); //ObjectTemplateNameList
    public static final int NODS = ChunkBuffer.createChunkId("NODS"); //Nodes
    public static final int NODE = ChunkBuffer.createChunkId("NODE");
    public static final int DATA = ChunkBuffer.createChunkId("DATA");

    private List<WorldSnapshotNode> objects = new ArrayList<WorldSnapshotNode>();
}
