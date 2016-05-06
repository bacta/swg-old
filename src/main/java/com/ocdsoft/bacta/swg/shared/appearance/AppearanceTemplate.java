package com.ocdsoft.bacta.swg.shared.appearance;

import com.ocdsoft.bacta.swg.shared.foundation.CrcLowerString;

/**
 * Created by crush on 4/22/2016.
 */
public class AppearanceTemplate {
    private CrcLowerString crcName;

    public String getName() {
        return crcName.getString();
    }
}
