package com.ocdsoft.bacta.swg.shared.foundation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by crush on 4/19/2016.
 */
public class TagTest {
    static final int TAG_0000 = 0x30303030;
    static final int TAG_0012 = 0x30303132;
    static final int TAG_FORM = 0x464F524D;
    static final int TAG_CAT = 0x43415420;

    @Test
    public void shouldConvertStringToTag() {
        Assert.assertEquals(TAG_0000, Tag.convertStringToTag("0000"));
        Assert.assertEquals(TAG_FORM, Tag.convertStringToTag("FORM"));
        Assert.assertEquals(TAG_CAT, Tag.convertStringToTag("CAT "));
    }

    @Test
    public void shouldConvertIntToTag() {
        //Should convert 0 -> '0000', and then to 0x30303030
        Assert.assertEquals(TAG_0000, Tag.convertIntToTag(0));
        //Should convert 12 -> '0012', and then to 0x30303132
        Assert.assertEquals(TAG_0012, Tag.convertIntToTag(12));
    }

    @Test
    public void shouldConvertTagToString() {
        Assert.assertEquals("FORM", Tag.convertTagToString(TAG_FORM));
        Assert.assertEquals("CAT ", Tag.convertTagToString(TAG_CAT));
    }
}
