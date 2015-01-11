package com.ocdsoft.bacta.swg.shared.iff.terrain.filter;

import com.ocdsoft.bacta.swg.shared.geometry.Rectangle;
import com.ocdsoft.bacta.swg.shared.iff.terrain.Filter;

public class FilterBitmap extends Filter {
    int familyId;
    float lowBitmapLimit;
    float highBitmapLimit;
    Rectangle extent;
    float gain;
}
