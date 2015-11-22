package com.ocdsoft.bacta.swg.shared.object.template.param;

import bacta.iff.Iff;

/**
 * Created by crush on 11/21/2015.
 */
public final class StructParam<StructType> extends TemplateBase<StructType, StructType> {
    @Override
    public void loadFromIff(Iff iff) {

    }

    @Override
    public void saveToIff(Iff iff) {

    }

    @Override
    protected TemplateBase<StructType, StructType> createNewParam() {
        return null;
    }
}
