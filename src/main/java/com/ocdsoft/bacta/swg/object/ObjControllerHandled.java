package com.ocdsoft.bacta.swg.object;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ObjControllerHandled {
	Class<?> value();
}
