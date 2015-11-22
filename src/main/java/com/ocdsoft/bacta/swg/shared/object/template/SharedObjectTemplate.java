package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.swg.localization.StringId;
import com.ocdsoft.bacta.swg.shared.object.template.param.*;
import com.ocdsoft.bacta.swg.shared.slot.ArrangementDescriptor;
import com.ocdsoft.bacta.swg.shared.slot.SlotDescriptor;

/**
 * Created by crush on 3/4/14.
 */
public class SharedObjectTemplate extends ObjectTemplate {
    private final StringIdParam objectName = new StringIdParam();
    private final StringIdParam detailedDescription = new StringIdParam();
    private final StringIdParam lookAtText = new StringIdParam();
    private final BoolParam snapToTerrain = new BoolParam();
    private final IntegerParam containerType = new IntegerParam();
    private final IntegerParam containerVolumeLimit = new IntegerParam();
    private final StringParam tintPallete = new StringParam();
    private final StringParam slotDescriptorFilename = new StringParam();
    private final StringParam arrangementDescriptorFilename = new StringParam();
    private final StringParam appearanceFilename = new StringParam();
    private final StringParam portalLayoutFilename = new StringParam();
    private final StringParam clientDataFile = new StringParam();
    private final IntegerParam collisionMaterialFlags = new IntegerParam(); //These aren't in NGE?
    private final IntegerParam collisionMaterialPassFlags = new IntegerParam(); //These aren't in NGE?
    private final IntegerParam collisionActionFlags = new IntegerParam(); //These aren't in NGE?
    private final IntegerParam collisionActionPassFlags = new IntegerParam(); //These aren't in NGE?
    private final IntegerParam collisionActionBlockFlags = new IntegerParam(); //These aren't in NGE?
    private final FloatParam scale = new FloatParam();
    private final IntegerParam gameObjectType = new IntegerParam();
    private final BoolParam sendToClient = new BoolParam();
    private final FloatParam scaleThresholdBeforeExtentTest = new FloatParam();
    private final FloatParam clearFloraRadius = new FloatParam();
    private final IntegerParam surfaceType = new IntegerParam();
    private final FloatParam noBuildRadius = new FloatParam();
    private final BoolParam onlyVisibleInTools = new BoolParam();
    private final FloatParam locationReservationRadius = new FloatParam();

    private SlotDescriptor slotDescriptor = null;
    private ArrangementDescriptor arrangementDescriptor = null;

    public SharedObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() {
        return ID_SHOT;
    }

    public final boolean getSendToClient() {
        if (sendToClient.isLoaded()) {
            return sendToClient.getValue();
        } else {
            return baseData != null
                    && baseData instanceof SharedObjectTemplate
                    && ((SharedObjectTemplate) baseData).getSendToClient();
        }
    }

    public final boolean getOnlyVisibleInTools() {
        if (onlyVisibleInTools.isLoaded()) {
            return onlyVisibleInTools.getValue();
        } else {
            return baseData != null
                    && baseData instanceof SharedObjectTemplate
                    && ((SharedObjectTemplate) baseData).getOnlyVisibleInTools();
        }
    }

    public final boolean getSnapToTerrain() {
        if (snapToTerrain.isLoaded()) {
            return snapToTerrain.getValue();
        } else {
            return baseData != null
                    && baseData instanceof SharedObjectTemplate
                    && ((SharedObjectTemplate) baseData).getSnapToTerrain();
        }
    }

    public final float getScale() {
        if (scale.isLoaded()) {
            return scale.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getScale();
        } else {
            return 0.0f;
        }
    }

    public final float getScaleMin() {
        if (scale.isLoaded()) {
            return scale.getMinValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getScaleMin();
        } else {
            return 0.0f;
        }
    }

    public final float getScaleMax() {
        if (scale.isLoaded()) {
            return scale.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getScaleMax();
        } else {
            return 0.0f;
        }
    }

    public final float getScaleThresholdBeforeExtentTest() {
        if (scaleThresholdBeforeExtentTest.isLoaded()) {
            return scaleThresholdBeforeExtentTest.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getScaleThresholdBeforeExtentTest();
        } else {
            return 0.0f;
        }
    }

    public final float getScaleThresholdBeforeExtentTestMin() {
        if (scaleThresholdBeforeExtentTest.isLoaded()) {
            return scaleThresholdBeforeExtentTest.getMinValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getScaleThresholdBeforeExtentTestMin();
        } else {
            return 0.0f;
        }
    }

    public final float getScaleThresholdBeforeExtentTestMax() {
        if (scaleThresholdBeforeExtentTest.isLoaded()) {
            return scaleThresholdBeforeExtentTest.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getScaleThresholdBeforeExtentTestMax();
        } else {
            return 0.0f;
        }
    }

    public final int getGameObjectType() {
        if (gameObjectType.isLoaded()) {
            return gameObjectType.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getGameObjectType();
        } else {
            return 0;
        }
    }

    public final int getSurfaceType() {
        if (surfaceType.isLoaded()) {
            return surfaceType.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getSurfaceType();
        } else {
            return 0;
        }
    }

    public final StringId getObjectName() {
        if (objectName.isLoaded()) {
            return objectName.getStringIdValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getObjectName();
        } else {
            return StringId.Invalid;
        }
    }

    public final StringId getDetailedDescription() {
        if (detailedDescription.isLoaded()) {
            return detailedDescription.getStringIdValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getDetailedDescription();
        } else {
            return StringId.Invalid;
        }
    }

    public final StringId getLookAtText() {
        if (lookAtText.isLoaded()) {
            return lookAtText.getStringIdValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getLookAtText();
        } else {
            return StringId.Invalid;
        }
    }

    public final ArrangementDescriptor getArrangementDescriptor() {
        return arrangementDescriptor;
    }

    public final void setArrangementDescriptor(ArrangementDescriptor arrangementDescriptor) {
        this.arrangementDescriptor = arrangementDescriptor;
    }

    public final SlotDescriptor getSlotDescriptor() {
        return slotDescriptor;
    }

    public final void setSlotDescriptor(SlotDescriptor slotDescriptor) {
        this.slotDescriptor = slotDescriptor;
    }

    public final String getTintPalette() {
        if (tintPallete.isLoaded()) {
            return tintPallete.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getTintPalette();
        } else {
            return "";
        }
    }

    public final String getClientDataFile() {
        if (clientDataFile.isLoaded()) {
            return clientDataFile.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getClientDataFile();
        } else {
            return "";
        }
    }

    public final String getSlotDescriptorFilename() {
        if (slotDescriptorFilename.isLoaded()) {
            return slotDescriptorFilename.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getSlotDescriptorFilename();
        } else {
            return "";
        }
    }

    public final String getArrangementDescriptorFilename() {
        if (arrangementDescriptorFilename.isLoaded()) {
            return arrangementDescriptorFilename.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getArrangementDescriptorFilename();
        } else {
            return "";
        }
    }

    public final String getAppearanceFilename() {
        if (appearanceFilename.isLoaded()) {
            return appearanceFilename.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getAppearanceFilename();
        } else {
            return "";
        }
    }

    public final String getPortalLayoutFilename() {
        if (portalLayoutFilename.isLoaded()) {
            return portalLayoutFilename.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getPortalLayoutFilename();
        } else {
            return "";
        }
    }

    public final float getNoBuildRadius() {
        if (noBuildRadius.isLoaded()) {
            return noBuildRadius.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getNoBuildRadius();
        } else {
            return 0.0f;
        }
    }

    public final float getNoBuildRadiusMax() {
        if (noBuildRadius.isLoaded()) {
            return noBuildRadius.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getNoBuildRadiusMax();
        } else {
            return 0.0f;
        }
    }

    public final float getNoBuildRadiusMin() {
        if (noBuildRadius.isLoaded()) {
            return noBuildRadius.getMinValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getNoBuildRadiusMin();
        } else {
            return 0.0f;
        }
    }

    public final float getLocationReservationRadius() {
        if (locationReservationRadius.isLoaded()) {
            return locationReservationRadius.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getLocationReservationRadius();
        } else {
            return 0.0f;
        }
    }

    public final float getLocationReservationRadiusMax() {
        if (locationReservationRadius.isLoaded()) {
            return locationReservationRadius.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getLocationReservationRadiusMax();
        } else {
            return 0.0f;
        }
    }

    public final float getLocationReservationRadiusMin() {
        if (locationReservationRadius.isLoaded()) {
            return locationReservationRadius.getMinValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getLocationReservationRadiusMin();
        } else {
            return 0.0f;
        }
    }

    public final float getClearFloraRadius() {
        if (clearFloraRadius.isLoaded()) {
            return clearFloraRadius.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getClearFloraRadius();
        } else {
            return 0.0f;
        }
    }

    public final float getClearFloraRadiusMax() {
        if (clearFloraRadius.isLoaded()) {
            return clearFloraRadius.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getClearFloraRadiusMax();
        } else {
            return 0.0f;
        }
    }

    public final float getClearFloraRadiusMin() {
        if (clearFloraRadius.isLoaded()) {
            return clearFloraRadius.getMinValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getClearFloraRadiusMin();
        } else {
            return 0.0f;
        }
    }

    public final int getContainerType() {
        if (containerType.isLoaded()) {
            return containerType.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getContainerType();
        } else {
            return 0;
        }
    }

    public final int getContainerVolumeLimit() {
        if (containerVolumeLimit.isLoaded()) {
            return containerVolumeLimit.getValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getContainerVolumeLimit();
        } else {
            return 0;
        }
    }

    public final int getContainerVolumeLimitMax() {
        if (containerVolumeLimit.isLoaded()) {
            return containerVolumeLimit.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getContainerVolumeLimitMax();
        } else {
            return 0;
        }
    }

    public final int getContainerVolumeLimitMin() {
        if (containerVolumeLimit.isLoaded()) {
            return containerVolumeLimit.getMinValue();
        } else if (baseData != null && baseData instanceof SharedObjectTemplate) {
            return ((SharedObjectTemplate) baseData).getContainerVolumeLimitMin();
        } else {
            return 0;
        }
    }
}
