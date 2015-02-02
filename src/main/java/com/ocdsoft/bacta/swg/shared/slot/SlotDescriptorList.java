package com.ocdsoft.bacta.swg.shared.slot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBufferContext;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.tre.TreeFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 8/25/2014.
 */
@Singleton
public class SlotDescriptorList {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final Map<String, SlotDescriptor> descriptors = new HashMap<>();
    private final SlotIdManager slotIdManager;
    private final TreeFile treeFile;

    @Inject
    public SlotDescriptorList(TreeFile treeFile, SlotIdManager slotIdManager) {
        this.treeFile = treeFile;
        this.slotIdManager = slotIdManager;
    }

    public SlotDescriptor fetch(final String filename) {
        //if (filename.isEmpty())
        //    return null;

        SlotDescriptor slotDescriptor = descriptors.get(filename);

        if (slotDescriptor == null) {
            if (treeFile.exists(filename)) {
                slotDescriptor = new SlotDescriptor(filename);

                ChunkReader chunkReader = new ChunkReader(filename, treeFile.open(filename));
                chunkReader.nextChunk(); //SLTD
                chunkReader.nextChunk(); //0000

                ChunkBufferContext context = chunkReader.nextChunk(); //DATA

                while (context.hasMoreBytes(chunkReader.readerIndex())) {
                    String slotName = chunkReader.readNullTerminatedAscii();
                    int slotId = slotIdManager.findSlotId(slotName);

                    slotDescriptor.slots.add(slotIdManager.findSlotId(slotName));
                }

                descriptors.put(filename, slotDescriptor);
            }
        }

        return slotDescriptor;
    }
}
