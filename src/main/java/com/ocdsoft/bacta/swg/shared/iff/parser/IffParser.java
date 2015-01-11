package com.ocdsoft.bacta.swg.shared.iff.parser;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IffParser {
	
	private final List<IffParsingElement> parserList = new ArrayList<>();

	@Getter private final String path;
    @Getter private final String editorType;
	
	public IffParser(String parserPath, String editorType) {
		path = parserPath;
        this.editorType = editorType;
	}

	public void add(String fieldName, TypeParser<?> typeParser) {
		parserList.add(new IffParsingElement(fieldName, typeParser));
	}
	
	public Iterator<IffParsingElement> iterator() {
		return parserList.iterator();
	}

    public class IffParsingElement {
		@Getter private final String fieldName;
		@Getter private final TypeParser<?> typeParser;
		
		public IffParsingElement(String fieldName, TypeParser<?> typeParser) {
			this.fieldName = fieldName;
			this.typeParser = typeParser;
		}
	}
}
