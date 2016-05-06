package com.ocdsoft.bacta.swg.shared.portal;

import com.ocdsoft.bacta.swg.shared.container.Container;
import com.ocdsoft.bacta.swg.shared.object.GameObject;

/**
 * Created by crush on 4/27/2016.
 */
public class PortalProperty extends Container {
    public static int getClassPropertyId() {
        return 0x939616B9;
    }

    public PortalProperty(final GameObject owner, final String filename) {
        super(getClassPropertyId(), owner);
    }
}
