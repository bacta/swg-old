package com.ocdsoft.bacta.swg.shared.container;

import com.ocdsoft.bacta.swg.shared.foundation.CrcLowerString;
import com.ocdsoft.bacta.tre.TreeFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 4/22/2016.
 * <p>
 * Manages the list of SlotDescriptor objects.
 * <p>
 * This class provides an interface for creating SlotDescriptor
 * objects based on SlotDescriptor files.  So long as a single reference exists
 * to a SlotDescriptor, that SlotDescriptor will stay loaded and will be returned
 * to any caller that asks for it by the same filename.
 */
public class SlotDescriptorList {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlotDescriptorList.class);

    private final Map<CrcLowerString, SlotDescriptor> descriptors = new HashMap<>();
    private final TreeFile treeFile; //Used for loading unloaded descriptors.

    public SlotDescriptorList(final TreeFile treeFile) {
        this.treeFile = treeFile;
    }

    public SlotDescriptor fetch(final CrcLowerString filename) {
        return null;
    }

    public SlotDescriptor fetch(final String filename) {
        return null;
    }
}
