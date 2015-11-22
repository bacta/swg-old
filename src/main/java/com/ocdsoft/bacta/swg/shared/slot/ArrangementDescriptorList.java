package com.ocdsoft.bacta.swg.shared.slot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.tre.TreeFile;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 8/25/2014.
 */
@Singleton
public class ArrangementDescriptorList {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final Map<String, ArrangementDescriptor> descriptors = new HashMap<>();
    private final SlotIdManager slotIdManager;
    private final TreeFile treeFile;

    @Inject
    public ArrangementDescriptorList(TreeFile treeFile, SlotIdManager slotIdManager) {
        this.treeFile = treeFile;
        this.slotIdManager = slotIdManager;
    }

    public ArrangementDescriptor fetch(final String filename) {
        if (filename.isEmpty())
            return null;

        ArrangementDescriptor arrangementDescriptor = descriptors.get(filename);

        if (arrangementDescriptor == null) {
            if (treeFile.exists(filename)) {
                arrangementDescriptor = new ArrangementDescriptor(filename);

                //Add all of the slots that hold anything to each arrangement descriptor?
                //This isn't right. It actually goes on SlottedContainmentProperty, but will it matter?
                for (String slotName : slotIdManager.getSlotsThatHoldAnything()) {
                    TIntList arrangementSlots = new TIntArrayList(1);
                    arrangementSlots.add(slotIdManager.findSlotId(slotName));
                    arrangementDescriptor.addArrangement(arrangementSlots);
                }

//                ChunkReader chunkReader = new ChunkReader(filename, treeFile.open(filename));
//                chunkReader.nextChunk(); //ARGD
//                chunkReader.nextChunk(); //0000
//
//                while (chunkReader.hasMoreChunks()) {
//                    ChunkBufferContext context = chunkReader.nextChunk();
//
//                    TIntList arrangementSlots = new TIntArrayList();
//
//                    while (context.hasMoreBytes(chunkReader.readerIndex()))
//                        arrangementSlots.add(slotIdManager.findSlotId(chunkReader.readNullTerminatedAscii()));
//
//                    arrangementDescriptor.addArrangement(arrangementSlots);
//                }

                descriptors.put(filename, arrangementDescriptor);
            }
        }

        return arrangementDescriptor;
    }
}
