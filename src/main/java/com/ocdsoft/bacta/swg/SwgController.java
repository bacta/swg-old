package com.ocdsoft.bacta.swg;

import com.ocdsoft.bacta.soe.ServerType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SwgController {

    ServerType[] server();

	Class<?> handles();

}