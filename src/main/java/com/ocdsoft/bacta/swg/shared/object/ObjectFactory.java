package com.ocdsoft.bacta.swg.shared.object;

import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;

/**
 * Created by crush on 5/6/2016.
 * <p>
 * Creates an object, and/or initializes an object based on a template.
 *
 * @param <T> The type of object this factory will create.
 * @param <U> The type of object template this factory will use for initialization.
 */
public interface ObjectFactory<T extends GameObject, U extends ObjectTemplate> {
    /**
     * Create a new instance of an object of type {@link T} based on the template of type {@link U}.
     *
     * @param template The template to use to initialize the object.
     * @return A new instance of the object. NetworkId should be set post creation.
     */
    T create(U template);

    /**
     * Initializes the object with the data from the template.
     *
     * @param object   The object being initialized.
     * @param template The template to use for initialization.
     */
    void initialize(T object, U template);
}
