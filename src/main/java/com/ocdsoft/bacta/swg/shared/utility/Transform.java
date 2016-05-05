package com.ocdsoft.bacta.swg.shared.utility;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.shared.math.Vector;
import lombok.Data;
import lombok.Getter;
import org.magnos.steer.vec.Vec3;

import javax.vecmath.Quat4f;
import java.nio.ByteBuffer;

/**
 * Created by kyle on 4/7/2016.
 */
public class Transform extends Vector implements ByteBufferWritable {

    @Getter
    private final Quat4f orientation;

    public Transform() {
        super(0, 0, 0);
        this.orientation = new Quat4f();
    }

    public Transform(Quat4f orientation) {
        super(0, 0, 0);
        this.orientation = orientation;
    }

    public Transform(Vec3 position) {
        super(position);
        this.orientation = new Quat4f();
    }

    public Transform(Vec3 position, Quat4f orientation) {
        super(position);
        this.orientation = orientation;
    }

    public Transform(final ByteBuffer buffer) {
        super(buffer);
        orientation = BufferUtil.getQuat4f(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putQuat4f(buffer, orientation);
        super.writeToBuffer(buffer);
    }
}
