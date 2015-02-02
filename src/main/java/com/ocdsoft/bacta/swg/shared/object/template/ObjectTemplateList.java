package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.object.crc.ConstCharCrcString;
import com.ocdsoft.bacta.swg.shared.object.crc.CrcStringTable;
import com.ocdsoft.bacta.swg.shared.object.template.param.TemplateBase;
import com.ocdsoft.bacta.swg.shared.object.template.param.TemplateBaseIffLoader;
import com.ocdsoft.bacta.tre.TreeFile;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by crush on 8/19/2014.
 */
public class ObjectTemplateList extends DataResourceList {
    protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    protected final CrcStringTable crcStringTable;

    protected final Map<Class, TemplateBaseIffLoader> templateBaseLoaders = new HashMap<>();
    protected final TIntObjectMap<Function<String, ? extends ObjectTemplate>> templateBindings = new TIntObjectHashMap<>();

    private final ObjectTemplateIffReader templateReader = new ObjectTemplateIffReader(this);
    protected final TIntObjectMap<ObjectTemplate> loadedTemplates = new TIntObjectHashMap<>();
    protected final TreeFile treeFile;

    public ObjectTemplateList(TreeFile treeFile) {
        this.treeFile = treeFile;

        this.crcStringTable = new CrcStringTable(treeFile);
    }

    public final <T extends ObjectTemplate> void assignBinding(final int tag, Function<String, T> constructor) {
        templateBindings.put(tag, constructor);
    }

    public final <T extends TemplateBase> TemplateBaseIffLoader<T> getTemplateBaseIffLoader(Class classType) {
        return templateBaseLoaders.get(classType);
    }

    public final <T extends TemplateBase> void registerTemplateBaseLoader(Class loaderType, TemplateBaseIffLoader<T> loader) {
        templateBaseLoaders.put(loaderType, loader);
    }

    public final ObjectTemplate createObjectTemplate(final int tag, final String templateName) {
        Function<String, ? extends ObjectTemplate> ctor = templateBindings.get(tag);

        if (ctor == null)
            return null;

        return ctor.apply(templateName);
    }

    public final void loadCrcStringTable(final String fileName) {
        crcStringTable.load(fileName);
    }

    public <T> T getObjectTemplate(final String templateName) {
        int crc = SOECRC32.hashCode(templateName);

        ObjectTemplate objectTemplate = loadedTemplates.get(crc);

        if (objectTemplate == null) {
            byte[] bytes = treeFile.open(templateName);

            if (bytes != null && bytes.length > 0) {
                objectTemplate = templateReader.read(new ChunkReader(templateName, bytes));

                if (objectTemplate != null)
                    loadedTemplates.put(crc, objectTemplate);
            }
        }

        return (T) objectTemplate;
    }

    public <T> T getObjectTemplate(final ConstCharCrcString crcString) {
        ObjectTemplate objectTemplate = loadedTemplates.get(crcString.getCrc());

        if (objectTemplate == null) {
            byte[] bytes = treeFile.open(crcString.getString());

            if (bytes != null && bytes.length > 0) {
                objectTemplate = templateReader.read(new ChunkReader(crcString.getString(), bytes));

                if (objectTemplate != null)
                    loadedTemplates.put(crcString.getCrc(), objectTemplate);
            }
        }

        return (T) objectTemplate;
    }

    //public abstract ObjectTemplate getObjectTemplate(final ChunkReader chunkReader);
    //public abstract ObjectTemplate getObjectTemplate(final int crc);
}
