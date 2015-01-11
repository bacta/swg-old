package com.ocdsoft.bacta.swg.shared.iff.terrain;

/**
 * Created by crush on 8/14/2014.
 */
public class MultiFractal {
    public class CombinationRule {
        public static final int CR_add = 0x0;
        public static final int CR_multiply = 0x1;
        public static final int CR_crest = 0x2;
        public static final int CR_turbulence = 0x3;
        public static final int CR_crestClamp = 0x4;
        public static final int CR_turbulenceClamp = 0x5;
        public static final int CR_COUNT = 0x6;
    }
}
