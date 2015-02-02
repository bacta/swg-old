package com.ocdsoft.bacta.swg.data;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.tre.TreeFile;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;

/**
 * SharedFileService houses all the shared data that different services will need to fulfill their function.
 * <p>
 * To add a new shared data resource to the class, simply implement the {@link SharedFileLoader} interface on
 * the class, and then add it as a field (visibility doesn't matter). When a new instance of SharedDataService is created,
 * the {@link SharedFileLoader#load(TreeFile)} method will be called on each field.
 * </p>
 * <p>
 * For this reason, it is important to remember to initialize your field before the field reflection is done in
 * this class's constructor.
 * </p>
 */
@Singleton
public final class SharedFileService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Injector injector;

    @Inject
    public SharedFileService(SetupSharedFile sharedFile, TreeFile treeFile, Injector injector) {

        this.injector = injector;

        logger.info("Loading shared data.");

        final long start = System.nanoTime();

        Reflections reflections = new Reflections();

        Set<Class<? extends SharedFileLoader>> loaders = reflections.getSubTypesOf(SharedFileLoader.class);

        Iterator<Class<? extends SharedFileLoader>> fileLoader = loaders.iterator();
        while(fileLoader.hasNext()) {
            Class<? extends SharedFileLoader> clazz = fileLoader.next();
            if(clazz.isAnnotationPresent(Singleton.class)) {
                injector.getInstance(clazz);
            } else {
                logger.error(clazz.getName() + " is not annotated as a singleton when it should be");
            }
        }

        logger.info(String.format("Loaded all data in %.2f", (System.nanoTime() - start) / 1e6));
    }

    public void reload() {
        Reflections reflections = new Reflections();

        Set<Class<? extends SharedFileLoader>> loaders = reflections.getSubTypesOf(SharedFileLoader.class);

        Iterator<Class<? extends SharedFileLoader>> fileLoader = loaders.iterator();
        while(fileLoader.hasNext()) {
            SharedFileLoader loader = injector.getInstance(fileLoader.next());
            loader.reload();
        }
    }
}
