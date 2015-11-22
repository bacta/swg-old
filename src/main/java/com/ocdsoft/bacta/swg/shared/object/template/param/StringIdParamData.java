package com.ocdsoft.bacta.swg.shared.object.template.param;

/**
 * Created by crush on 11/21/2015.
 */
public final class StringIdParamData {
    public StringParam table;
    public StringParam index;

    public StringIdParamData() {
        this.table = new StringParam();
        this.index = new StringParam();
    }
}
