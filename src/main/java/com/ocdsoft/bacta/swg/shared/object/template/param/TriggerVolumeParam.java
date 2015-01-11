package com.ocdsoft.bacta.swg.shared.object.template.param;

/**
 * Created by crush on 4/1/14.
 */
public class TriggerVolumeParam extends TemplateBase<TriggerVolumeParam> {
    private TriggerVolumeParamData dataSingle;

    @Override
    public TriggerVolumeParam createNewParam() { return new TriggerVolumeParam(); }

    @Override
    public TriggerVolumeParam createDeepCopy() {
        return null;
    }
}
