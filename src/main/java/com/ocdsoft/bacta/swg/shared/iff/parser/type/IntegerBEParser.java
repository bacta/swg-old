package com.ocdsoft.bacta.swg.shared.iff.parser.type;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.parser.TypeParser;

public class IntegerBEParser implements TypeParser<Integer> {

    @Override
    public Integer parse(ChunkReader reader) {
        ///chunk.getBuffer().order(ByteOrder.BIG_ENDIAN);
        //int value = chunk.getBuffer().getInt();
        //chunk.getBuffer().order(ByteOrder.LITTLE_ENDIAN);
        return 0;
    }

}
