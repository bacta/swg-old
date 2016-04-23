package com.ocdsoft.bacta.swg.shared.template.definition;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by crush on 4/19/2016.
 */
public class TagInfoTest {
    @Test
    public void convertStringToTagString() {
        final String tagString = TagInfo.convertStringToTagString("0000");
        Assert.assertEquals(tagString, "TAG(0,0,0,0)");
    }
}
