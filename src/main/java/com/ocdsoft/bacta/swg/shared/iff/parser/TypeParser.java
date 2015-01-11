package com.ocdsoft.bacta.swg.shared.iff.parser;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;

public interface TypeParser<T> {
    T parse(ChunkReader reader);
}
