package com.ocdsoft.bacta.swg.shared.object.template.param;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Created by crush on 8/21/2014.
 */
public class TemplateBaseCollection<T extends TemplateBase> extends ArrayList<T> {
    @Getter @Setter private boolean append;
    @Getter @Setter private boolean loaded;
}
