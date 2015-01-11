package com.ocdsoft.bacta.swg.shared.iff.parser.type;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.parser.TypeParser;

import java.util.List;

public class IntegerStringListParser implements TypeParser<List<String>> {

    @Override
    public List<String> parse(ChunkReader chunk) {
        /*
        int length = chunk.getBuffer().getInt();
		List<String> stringList = new ArrayList<>();
		
		for(int i = 0; i < length; ++i) {
			stringList.add(StringUtil.getNullTerminatedString(chunk.getBuffer()));
		}
		
		return stringList;*/

        return null;
    }

}
