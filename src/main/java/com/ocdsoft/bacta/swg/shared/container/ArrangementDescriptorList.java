package com.ocdsoft.bacta.swg.shared.container;

import com.ocdsoft.bacta.swg.shared.foundation.CrcLowerString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 4/22/2016.
 */
public class ArrangementDescriptorList {

    private final Map<CrcLowerString, ArrangementDescriptor> descriptors = new HashMap<>();

    public ArrangementDescriptor fetch(final CrcLowerString filename) {
        return null;
    }

    public ArrangementDescriptor fetch(final String filename) {
        return null;
    }
}
