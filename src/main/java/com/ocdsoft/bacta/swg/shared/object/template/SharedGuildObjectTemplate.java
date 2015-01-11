package com.ocdsoft.bacta.swg.shared.object.template;

/**
 * Created by crush on 3/4/14.
 */
public class SharedGuildObjectTemplate extends SharedUniverseObjectTemplate {
    public SharedGuildObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_SGLD; }

}
