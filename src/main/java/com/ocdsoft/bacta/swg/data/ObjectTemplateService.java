package com.ocdsoft.bacta.swg.data;

import com.ocdsoft.bacta.swg.template.ObjectTemplate;

import java.util.function.Function;

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

    <T extends ObjectTemplate> void assignBinding(final int tag, Function<String, T> constructor);

    ObjectTemplate createObjectTemplate(final int tag, final String templateName);

    void loadCrcStringTable(final String fileName);

    <T> T getObjectTemplate(final String templateName);
}
