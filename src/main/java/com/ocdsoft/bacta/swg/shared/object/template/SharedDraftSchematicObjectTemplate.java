package com.ocdsoft.bacta.swg.shared.object.template;

/**
 * Created by crush on 3/4/14.
 */
public class SharedDraftSchematicObjectTemplate extends SharedIntangibleObjectTemplate {
    public SharedDraftSchematicObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_SDSC; }

    private enum ArmorRating {
        None,
        Light,
        Medium,
        Heavy
    }
//
//    @Getter
//    private final TemplateBaseCollection<StructParam<IngredientSlot>> slots = new TemplateBaseCollection<>();
//    @Getter
//    private final TemplateBaseCollection<StructParam<SchematicAttribute>> attributes = new TemplateBaseCollection<>();
//    @Getter
//    private final StringParam craftedSharedTemplate = new StringParam();
//
//
//
//
//
//    public static final class SchematicAttribute extends ObjectTemplate {
//        public SchematicAttribute(String templateName) {
//            super(templateName);
//        }
//
//        @Override
//        public int getId() { return ID_DSSA; }
//
//        private StringIdParam name = new StringIdParam();
//
//        public StringId getStringIdName() { return name.getValue(); }
//
//        @Getter
//        private StringIdParam experiment = new StringIdParam();
//        @Getter
//        private IntegerParam value = new IntegerParam();
//    }
//
//    public static final class IngredientSlot extends ObjectTemplate {
//        public IngredientSlot(String templateName) {
//            super(templateName);
//        }
//
//        @Override
//        public int getId() { return ID_SISS; }
//
//        private StringIdParam name = new StringIdParam();
//
//        public StringId getStringIdName() { return name.getValue(); }
//
//        @Getter
//        private StringParam hardpoint = new StringParam();
//    }
}
