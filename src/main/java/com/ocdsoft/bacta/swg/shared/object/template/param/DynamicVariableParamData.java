package com.ocdsoft.bacta.swg.shared.object.template.param;

import java.util.List;

/**
 * Created by crush on 11/21/2015.
 */
public final class DynamicVariableParamData {
    public String name;
    public DataType type;

    //data
    private IntegerParam integerParam;
    private FloatParam floatParam;
    private StringParam stringParam;
    private List<DynamicVariableParamData> listParam;

    public enum DataType {
        UNKNOWN,
        INTEGER,
        FLOAT,
        STRING,
        LIST
    }
}
