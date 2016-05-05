package com.ocdsoft.bacta.swg.shared.math;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.Data;
import lombok.Getter;
import org.magnos.steer.vec.Vec3;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 4/7/2016.
 */

public class Vector implements ByteBufferWritable {

    @Getter
    private final Vec3 position;

    public Vector(float x, float z, float y) {
        this.position = new Vec3(x, y, z);
    }

    public Vector(final Vec3 position) {
        this.position = position;
    }

    public Vector(final ByteBuffer buffer) {
        position = BufferUtil.getVec3(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putVec3(buffer, position);
    }


}
