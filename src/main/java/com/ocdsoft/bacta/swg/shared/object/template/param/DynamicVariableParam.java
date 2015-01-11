package com.ocdsoft.bacta.swg.shared.object.template.param;

/**
 * Created by crush on 4/1/14.
 */
public final class DynamicVariableParam extends TemplateBase<DynamicVariableParam> {
    private DynamicVariableParamData dataSingle;

    @Override
    public DynamicVariableParam createNewParam() {
        return new DynamicVariableParam();
    }

    @Override
    public DynamicVariableParam createDeepCopy() {
        return null;
    }
}
