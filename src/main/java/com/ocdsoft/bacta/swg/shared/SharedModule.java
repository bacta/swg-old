package com.ocdsoft.bacta.swg.shared;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.ocdsoft.bacta.tre.TreeFile;


public class SharedModule extends AbstractModule implements Module {

    @Override
    protected void configure() {
        bind(TreeFile.class).asEagerSingleton();
    }

}
