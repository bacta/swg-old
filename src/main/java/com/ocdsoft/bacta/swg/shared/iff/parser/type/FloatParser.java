package com.ocdsoft.bacta.swg.shared.iff.parser.type;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.parser.TypeParser;

public class FloatParser implements TypeParser<Float> {

    @Override
    public Float parse(ChunkReader chunk) {
        return null; //chunk.getBuffer().getFloat();
    }

}
