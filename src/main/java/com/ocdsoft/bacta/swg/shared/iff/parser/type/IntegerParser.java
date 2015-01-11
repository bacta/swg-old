package com.ocdsoft.bacta.swg.shared.iff.parser.type;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.parser.TypeParser;

public class IntegerParser implements TypeParser<Integer> {

    @Override
    public Integer parse(ChunkReader reader) {
        return reader.readInt();
    }

}
