package com.ocdsoft.bacta.swg.shared.foundation;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/9/2016.
 */
public class BitArrayTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BitArrayTest.class);

    @Test
    public void shouldSetBits() {
        final BitArray bitArray = new BitArray();
        bitArray.setBit(1);//0100 0000 0000
        bitArray.setBit(3);//0101 0000 0000
        bitArray.setBit(6);//0101 0010 0000
        bitArray.setBit(9);//0101 0010 0100
        bitArray.setBit(130); //This should cause a resize.

        Assert.assertFalse(bitArray.testBit(0));
        Assert.assertTrue(bitArray.testBit(1));
        Assert.assertFalse(bitArray.testBit(2));
        Assert.assertTrue(bitArray.testBit(3));
        Assert.assertFalse(bitArray.testBit(4));
        Assert.assertFalse(bitArray.testBit(5));
        Assert.assertTrue(bitArray.testBit(6));
        Assert.assertFalse(bitArray.testBit(7));
        Assert.assertFalse(bitArray.testBit(8));
        Assert.assertTrue(bitArray.testBit(9));
        Assert.assertTrue(bitArray.testBit(130));
    }
}
