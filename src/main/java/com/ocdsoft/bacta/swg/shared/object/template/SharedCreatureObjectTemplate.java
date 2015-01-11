package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.swg.shared.object.MovementTable;
import com.ocdsoft.bacta.swg.shared.object.template.param.BoolParam;
import com.ocdsoft.bacta.swg.shared.object.template.param.FloatParam;
import com.ocdsoft.bacta.swg.shared.object.template.param.IntegerParam;
import com.ocdsoft.bacta.swg.shared.object.template.param.StringParam;

/**
 * Created by crush on 3/4/14.
 */
public class SharedCreatureObjectTemplate extends SharedTangibleObjectTemplate {
    public static final class MovementTypes {
        public static final int Run = 0x00;
        public static final int Walk = 0x01;
    }

    public SharedCreatureObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_SCOT; }

    private IntegerParam gender = new IntegerParam();
    private IntegerParam niche = new IntegerParam();
    private IntegerParam species = new IntegerParam();
    private IntegerParam race = new IntegerParam();
    private FloatParam[] acceleration = new FloatParam[] {
            new FloatParam(),
            new FloatParam()
    };
    private FloatParam[] speed = new FloatParam[] {
            new FloatParam(),
            new FloatParam()
    };
    private FloatParam[] turnRate = new FloatParam[] {
            new FloatParam(),
            new FloatParam()
    };
    private StringParam animationMapFilename = new StringParam();
    private FloatParam slopeModAngle = new FloatParam();
    private FloatParam slopeModPercent = new FloatParam();
    private FloatParam waterModPercent = new FloatParam();
    private FloatParam stepHeight = new FloatParam();
    private StringParam movementDatatable = new StringParam();
    private BoolParam[] postureAlignToTerrain = new BoolParam[] {
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam(),
            new BoolParam()
    };
    private FloatParam swimHeight = new FloatParam();
    private FloatParam warpTolerance = new FloatParam();
    private FloatParam collisionHeight = new FloatParam();
    private FloatParam collisionRadius = new FloatParam();
    private FloatParam collisionOffsetX = new FloatParam();
    private FloatParam collisionOffsetZ = new FloatParam();
    private FloatParam collisionLength = new FloatParam();
    private FloatParam cameraHeight = new FloatParam();

    private MovementTable movementTable = null;

    public final boolean getPostureAlignToTerrain(int posture) {
        if (postureAlignToTerrain[posture].isLoaded()) {
            return postureAlignToTerrain[posture].getValue();
        } else {
            return baseData != null
                    && baseData instanceof SharedCreatureObjectTemplate
                    && ((SharedCreatureObjectTemplate) baseData).getPostureAlignToTerrain(posture);
        }
    }

    public final float getAcceleration(int movementType) {
        if (acceleration[movementType].isLoaded()) {
            return acceleration[movementType].getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getAcceleration(movementType);
        } else {
            return 0.0f;
        }
    }

    public final float getAccelerationMax(int movementType) {
        if (acceleration[movementType].isLoaded()) {
            return acceleration[movementType].getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getAccelerationMax(movementType);
        } else {
            return 0.0f;
        }
    }

    public final float getAccelerationMin(int movementType) {
        if (acceleration[movementType].isLoaded()) {
            return acceleration[movementType].getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getAccelerationMin(movementType);
        } else {
            return 0.0f;
        }
    }

    public final float getSpeed(int movementType) {
        if (speed[movementType].isLoaded()) {
            return speed[movementType].getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSpeed(movementType);
        } else {
            return 0.0f;
        }
    }

    public final float getSpeedMax(int movementType) {
        if (speed[movementType].isLoaded()) {
            return speed[movementType].getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSpeedMax(movementType);
        } else {
            return 0.0f;
        }
    }

    public final float getSpeedMin(int movementType) {
        if (speed[movementType].isLoaded()) {
            return speed[movementType].getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSpeedMin(movementType);
        } else {
            return 0.0f;
        }
    }

    public final float getTurnRate(int movementType) {
        if (turnRate[movementType].isLoaded()) {
            return turnRate[movementType].getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getTurnRate(movementType);
        } else {
            return 0.0f;
        }
    }

    public final float getTurnRateMax(int movementType) {
        if (turnRate[movementType].isLoaded()) {
            return turnRate[movementType].getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getTurnRateMax(movementType);
        } else {
            return 0.0f;
        }
    }

    public final float getTurnRateMin(int movementType) {
        if (turnRate[movementType].isLoaded()) {
            return turnRate[movementType].getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getTurnRateMin(movementType);
        } else {
            return 0.0f;
        }
    }

    public final int getSpecies() {
        if (species.isLoaded()) {
            return species.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSpecies();
        } else {
            return 0;
        }
    }

    public final int getGender() {
        if (gender.isLoaded()) {
            return gender.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getGender();
        } else {
            return 0;
        }
    }

    public final int getNiche() {
        if (niche.isLoaded()) {
            return niche.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getNiche();
        } else {
            return 0;
        }
    }

    public final int getRace() {
        if (race.isLoaded()) {
            return race.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getRace();
        } else {
            return 0;
        }
    }

    public final String getAnimationMapFilename() {
        if (animationMapFilename.isLoaded()) {
            return animationMapFilename.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getAnimationMapFilename();
        } else {
            return "";
        }
    }

    public final String getMovementDatatable() {
        if (movementDatatable.isLoaded()) {
            return movementDatatable.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getMovementDatatable();
        } else {
            return "";
        }
    }

    public final MovementTable getMovementTable() {
        return movementTable;
    }

    public final float getSlopeModAngle() {
        if (slopeModAngle.isLoaded()) {
            return slopeModAngle.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSlopeModAngle();
        } else {
            return 0.0f;
        }
    }

    public final float getSlopeModAngleMax() {
        if (slopeModAngle.isLoaded()) {
            return slopeModAngle.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSlopeModAngleMax();
        } else {
            return 0.0f;
        }
    }

    public final float getSlopeModAngleMin() {
        if (slopeModAngle.isLoaded()) {
            return slopeModAngle.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSlopeModAngleMin();
        } else {
            return 0.0f;
        }
    }

    public final float getSlopeModPercent() {
        if (slopeModPercent.isLoaded()) {
            return slopeModPercent.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSlopeModPercent();
        } else {
            return 0.0f;
        }
    }

    public final float getSlopeModPercentMax() {
        if (slopeModPercent.isLoaded()) {
            return slopeModPercent.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSlopeModPercentMax();
        } else {
            return 0.0f;
        }
    }

    public final float getSlopeModPercentMin() {
        if (slopeModPercent.isLoaded()) {
            return slopeModPercent.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSlopeModPercentMin();
        } else {
            return 0.0f;
        }
    }

    public final float getStepHeight() {
        if (stepHeight.isLoaded()) {
            return stepHeight.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getStepHeight();
        } else {
            return 0.0f;
        }
    }

    public final float getStepHeightMax() {
        if (stepHeight.isLoaded()) {
            return stepHeight.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getStepHeightMax();
        } else {
            return 0.0f;
        }
    }

    public final float getStepHeightMin() {
        if (stepHeight.isLoaded()) {
            return stepHeight.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getStepHeightMin();
        } else {
            return 0.0f;
        }
    }

    public final float getSwimHeight() {
        if (swimHeight.isLoaded()) {
            return swimHeight.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSwimHeight();
        } else {
            return 0.0f;
        }
    }

    public final float getSwimHeightMax() {
        if (swimHeight.isLoaded()) {
            return swimHeight.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSwimHeightMax();
        } else {
            return 0.0f;
        }
    }

    public final float getSwimHeightMin() {
        if (swimHeight.isLoaded()) {
            return swimHeight.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getSwimHeightMin();
        } else {
            return 0.0f;
        }
    }

    public final float getWaterModPercent() {
        if (waterModPercent.isLoaded()) {
            return waterModPercent.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getWaterModPercent();
        } else {
            return 0.0f;
        }
    }

    public final float getWaterModPercentMax() {
        if (waterModPercent.isLoaded()) {
            return waterModPercent.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getWaterModPercentMax();
        } else {
            return 0.0f;
        }
    }

    public final float getWaterModPercentMin() {
        if (waterModPercent.isLoaded()) {
            return waterModPercent.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getWaterModPercentMin();
        } else {
            return 0.0f;
        }
    }

    public final float getWarpTolerance() {
        if (warpTolerance.isLoaded()) {
            return warpTolerance.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getWarpTolerance();
        } else {
            return 0.0f;
        }
    }

    public final float getWarpToleranceMax() {
        if (warpTolerance.isLoaded()) {
            return warpTolerance.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getWarpToleranceMax();
        } else {
            return 0.0f;
        }
    }

    public final float getWarpToleranceMin() {
        if (warpTolerance.isLoaded()) {
            return warpTolerance.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getWarpToleranceMin();
        } else {
            return 0.0f;
        }
    }

    public final float getCameraHeight() {
        if (cameraHeight.isLoaded()) {
            return cameraHeight.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getWarpTolerance();
        } else {
            return 0.0f;
        }
    }

    public final float getCameraHeightMax() {
        if (cameraHeight.isLoaded()) {
            return cameraHeight.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCameraHeightMax();
        } else {
            return 0.0f;
        }
    }

    public final float getCameraHeightMin() {
        if (cameraHeight.isLoaded()) {
            return cameraHeight.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCameraHeightMin();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionHeight() {
        if (collisionHeight.isLoaded()) {
            return collisionHeight.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getWarpTolerance();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionHeightMax() {
        if (collisionHeight.isLoaded()) {
            return collisionHeight.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionHeightMax();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionHeightMin() {
        if (collisionHeight.isLoaded()) {
            return collisionHeight.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionHeightMin();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionRadius() {
        if (collisionRadius.isLoaded()) {
            return collisionRadius.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionRadius();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionRadiusMax() {
        if (collisionRadius.isLoaded()) {
            return collisionRadius.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionRadiusMax();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionRadiusMin() {
        if (collisionRadius.isLoaded()) {
            return collisionRadius.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionRadiusMin();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionLength() {
        if (collisionLength.isLoaded()) {
            return collisionLength.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionRadius();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionLengthMax() {
        if (collisionLength.isLoaded()) {
            return collisionLength.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionLengthMax();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionLengthMin() {
        if (collisionLength.isLoaded()) {
            return collisionLength.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionLengthMin();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionOffsetX() {
        if (collisionOffsetX.isLoaded()) {
            return collisionOffsetX.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionOffsetX();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionOffsetXMax() {
        if (collisionOffsetX.isLoaded()) {
            return collisionOffsetX.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionOffsetXMax();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionOffsetXMin() {
        if (collisionOffsetX.isLoaded()) {
            return collisionOffsetX.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionOffsetXMin();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionOffsetZ() {
        if (collisionOffsetZ.isLoaded()) {
            return collisionOffsetZ.getValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionOffsetZ();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionOffsetZMax() {
        if (collisionOffsetZ.isLoaded()) {
            return collisionOffsetZ.getMaxValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionOffsetZMax();
        } else {
            return 0.0f;
        }
    }

    public final float getCollisionOffsetZMin() {
        if (collisionOffsetZ.isLoaded()) {
            return collisionOffsetZ.getMinValue();
        } else if (baseData != null && baseData instanceof SharedCreatureObjectTemplate) {
            return ((SharedCreatureObjectTemplate) baseData).getCollisionOffsetZMin();
        } else {
            return 0.0f;
        }
    }
}
