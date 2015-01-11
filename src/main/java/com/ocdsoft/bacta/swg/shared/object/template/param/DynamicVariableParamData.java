package com.ocdsoft.bacta.swg.shared.object.template.param;

import java.util.List;

/**
 * Created by crush on 8/15/2014.
 */
public class DynamicVariableParamData {
    private String name;
    private int type;

    //C union here fml - choose one of the following basically...it can be any!
    IntegerParam iparam;
    FloatParam fparam;
    StringParam sparam;
    List<DynamicVariableParamData> lparam;
}
