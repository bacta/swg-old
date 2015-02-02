package com.ocdsoft.bacta.swg.data;

import com.ocdsoft.bacta.swg.shared.object.template.ObjectTemplate;

/**
 * Created by crush on 3/4/14.
 */
public interface ObjectTemplateService<Type>  {

    /**
     * This gets the class associated with the provided template type
     * this is use in object creation.
     *
     * @param template template to get class object for
     * @return Class object related to the specified type
     * @throws com.ocdsoft.bacta.swg.shared.lang.NotImplementedException when chunk type is not mapped to a class
     */
    <T extends Type> Class<T> getClassForTemplate(ObjectTemplate template);
}
