package com.ocdsoft.bacta.swg.shared.object.template;

/**
 * Created by crush on 3/4/14.
 */
public class SharedConstructionContractObjectTemplate extends SharedIntangibleObjectTemplate {
    public SharedConstructionContractObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_SCNC; }
}
