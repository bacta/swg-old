package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.swg.shared.object.template.param.IntegerParam;
import com.ocdsoft.bacta.swg.shared.object.template.param.StringParam;
import lombok.Getter;

/**
 * Created by crush on 3/4/14.
 */
public class SharedTangibleObjectTemplate extends SharedObjectTemplate {
    public SharedTangibleObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_STOT; }
//
//    private final TemplateBaseCollection<StructParam<ObjectTemplate>> paletteColorCustomizationVariables = new TemplateBaseCollection<>();
//    private final TemplateBaseCollection<StructParam<ObjectTemplate>> rangedIntCustomizationVariables = new TemplateBaseCollection<>();
//    private final TemplateBaseCollection<StructParam<ObjectTemplate>> constStringCustomizationVariables = new TemplateBaseCollection<>();
//    private final TemplateBaseCollection<IntegerParam> socketDestinations = new TemplateBaseCollection<>();
//    private final StringParam structureFootprintFileName = new StringParam();
//    private final BoolParam useStructureFootprintOutline = new BoolParam();
//    private final BoolParam targetable = new BoolParam();
//    private final TemplateBaseCollection<StringParam> certificationsRequired = new TemplateBaseCollection<>();
//    private final TemplateBaseCollection<StructParam<ObjectTemplate>> customizationVariableMapping = new TemplateBaseCollection<>();
//
//    //private StructureFootprint structureFootprint = null; //TODO: When does this get set? Lazy set?
//
//    public final int getCertificationsRequiredCount() {
//        int count = 0;
//
//        if (certificationsRequired.isLoaded()) {
//            count = certificationsRequired.size();
//
//            if (certificationsRequired.isAppend()) {
//                if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                    count += ((SharedTangibleObjectTemplate) baseData).getCertificationsRequiredCount();
//            }
//        } else {
//            if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                count += ((SharedTangibleObjectTemplate) baseData).getCertificationsRequiredCount();
//        }
//
//        return count;
//    }
//
//    public final String getCertificationsRequired(int index) {
//        SharedTangibleObjectTemplate objectTemplate = this;
//        SharedTangibleObjectTemplate baseObjectTemplate = null;
//
//        int finalIndex = index;
//        for (objectTemplate = this; ; objectTemplate = baseObjectTemplate) {
//            while (true) {
//                if (objectTemplate.baseData != null && objectTemplate.baseData instanceof SharedTangibleObjectTemplate)
//                    baseObjectTemplate = (SharedTangibleObjectTemplate) baseData;
//
//                if (objectTemplate.certificationsRequired.isLoaded())
//                    break;
//
//                if (baseObjectTemplate == null)
//                    return "";
//
//                objectTemplate = baseObjectTemplate;
//            }
//
//            if (!objectTemplate.socketDestinations.isAppend() || baseObjectTemplate == null)
//                break;
//
//            int count = baseObjectTemplate.getCertificationsRequiredCount();
//
//            if (index >= count) {
//                finalIndex = index - count;
//                break;
//            }
//        }
//
//        if (finalIndex < 0 || finalIndex >= objectTemplate.certificationsRequired.size())
//            logger.error("Certifications required index out of range <{}>", finalIndex);
//
//        return objectTemplate.certificationsRequired.get(finalIndex).getValue();
//    }
//
//    public final int getSocketDestinationsCount() {
//        int count = 0;
//
//        if (socketDestinations.isLoaded()) {
//            count = socketDestinations.size();
//
//            if (socketDestinations.isAppend()) {
//                if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                    count += ((SharedTangibleObjectTemplate) baseData).getSocketDestinationsCount();
//            }
//        } else {
//            if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                count += ((SharedTangibleObjectTemplate) baseData).getSocketDestinationsCount();
//        }
//
//        return count;
//    }
//
//    public final int getSocketDestinations(int index) {
//        SharedTangibleObjectTemplate objectTemplate = this;
//        SharedTangibleObjectTemplate baseObjectTemplate = null;
//
//        int finalIndex = index;
//        for (objectTemplate = this; ; objectTemplate = baseObjectTemplate) {
//            while (true) {
//                if (objectTemplate.baseData != null && objectTemplate.baseData instanceof SharedTangibleObjectTemplate)
//                    baseObjectTemplate = (SharedTangibleObjectTemplate) baseData;
//
//                if (objectTemplate.socketDestinations.isLoaded())
//                    break;
//
//                if (baseObjectTemplate == null)
//                    return 0;
//
//                objectTemplate = baseObjectTemplate;
//            }
//
//            if (!objectTemplate.socketDestinations.isAppend() || baseObjectTemplate == null)
//                break;
//
//            int count = baseObjectTemplate.getSocketDestinationsCount();
//
//            if (index >= count) {
//                finalIndex = index - count;
//                break;
//            }
//        }
//
//        if (finalIndex < 0 || finalIndex >= objectTemplate.socketDestinations.size())
//            logger.error("Socket destinations index out of range <{}>", finalIndex);
//
//        return objectTemplate.socketDestinations.get(finalIndex).getValue();
//    }
//
//    public final int getRangedIntCustomizationVariablesCount() {
//        int count = 0;
//
//        if (rangedIntCustomizationVariables.isLoaded()) {
//            count = rangedIntCustomizationVariables.size();
//
//            if (rangedIntCustomizationVariables.isAppend()) {
//                if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                    count += ((SharedTangibleObjectTemplate) baseData).getRangedIntCustomizationVariablesCount();
//            }
//        } else {
//            if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                count += ((SharedTangibleObjectTemplate) baseData).getRangedIntCustomizationVariablesCount();
//        }
//
//        return count;
//    }
//
//    public final int getPaletteColorCustomizationVariablesCount() {
//        int count = 0;
//
//        if (paletteColorCustomizationVariables.isLoaded()) {
//            count = paletteColorCustomizationVariables.size();
//
//            if (paletteColorCustomizationVariables.isAppend()) {
//                if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                    count += ((SharedTangibleObjectTemplate) baseData).getPaletteColorCustomizationVariablesCount();
//            }
//        } else {
//            if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                count += ((SharedTangibleObjectTemplate) baseData).getPaletteColorCustomizationVariablesCount();
//        }
//
//        return count;
//    }
//
//    public final int getCustomizationVariableMappingCount() {
//        int count = 0;
//
//        if (customizationVariableMapping.isLoaded()) {
//            count = customizationVariableMapping.size();
//
//            if (customizationVariableMapping.isAppend()) {
//                if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                    count += ((SharedTangibleObjectTemplate) baseData).getCustomizationVariableMappingCount();
//            }
//        } else {
//            if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                count += ((SharedTangibleObjectTemplate) baseData).getCustomizationVariableMappingCount();
//        }
//
//        return count;
//    }
//
//    public final int getConstStringCustomizationVariablesCount() {
//        int count = 0;
//
//        if (constStringCustomizationVariables.isLoaded()) {
//            count = constStringCustomizationVariables.size();
//
//            if (constStringCustomizationVariables.isAppend()) {
//                if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                    count += ((SharedTangibleObjectTemplate) baseData).getConstStringCustomizationVariablesCount();
//            }
//        } else {
//            if (baseData != null && baseData instanceof SharedTangibleObjectTemplate)
//                count += ((SharedTangibleObjectTemplate) baseData).getConstStringCustomizationVariablesCount();
//        }
//
//        return count;
//    }
//
//    public final boolean getTargetable() {
//        if (targetable.isLoaded()) {
//            return targetable.getValue();
//        } else {
//            return baseData != null
//                    && baseData instanceof SharedTangibleObjectTemplate
//                    && ((SharedTangibleObjectTemplate) baseData).getTargetable();
//        }
//    }
//
//    public final boolean getUseStructureFootprintOutline() {
//        if (useStructureFootprintOutline.isLoaded()) {
//            return useStructureFootprintOutline.getValue();
//        } else {
//            return baseData != null
//                    && baseData instanceof SharedTangibleObjectTemplate
//                    && ((SharedTangibleObjectTemplate) baseData).getUseStructureFootprintOutline();
//        }
//    }
//
//    //public final StructureFootprint getStructureFootprint() {
//        //return structureFootprint;
//    //}
//
//    public final String getStructureFootprintFileName() {
//        if (structureFootprintFileName.isLoaded()) {
//            return structureFootprintFileName.getValue();
//        } else if (baseData != null && baseData instanceof SharedTangibleObjectTemplate) {
//            return ((SharedTangibleObjectTemplate) baseData).getStructureFootprintFileName();
//        } else {
//            return "";
//        }
//    }

    public static final class RangedIntCustomizationVariable extends ObjectTemplate {
        public RangedIntCustomizationVariable(String templateName) {
            super(templateName);
        }

        @Override
        public int getId() { return ID_RICV; }

        @Getter
        private final StringParam variableName = new StringParam();
        @Getter
        private final IntegerParam defaultValue = new IntegerParam();
        @Getter
        private final IntegerParam minValueInclusive = new IntegerParam();
        @Getter
        private final IntegerParam maxValueExclusive = new IntegerParam();
    }

    public static final class PaletteColorCustomizationVariable extends ObjectTemplate {
        public PaletteColorCustomizationVariable(String templateName) {
            super(templateName);
        }

        @Override
        public int getId() { return ID_PCCV; }

        @Getter
        private final StringParam variableName = new StringParam();
        @Getter
        private final StringParam palettePathName = new StringParam();
        @Getter
        private final IntegerParam defaultPaletteIndex = new IntegerParam();
    }

    public static final class ConstStringCustomizationVariable extends ObjectTemplate {
        public ConstStringCustomizationVariable(String templateName) {
            super(templateName);
        }

        @Override
        public int getId() { return ID_CSCV; }

        @Getter
        private final StringParam variableName = new StringParam();
        @Getter
        private final StringParam constValue = new StringParam();
    }

    public static final class CustomizationVariableMapping extends ObjectTemplate {
        public CustomizationVariableMapping(String templateName) {
            super(templateName);
        }

        @Override
        public int getId() { return ID_CVMM; }

        @Getter
        private final StringParam sourceVariable = new StringParam();
        @Getter
        private final StringParam dependentVariable = new StringParam();
    }
}
