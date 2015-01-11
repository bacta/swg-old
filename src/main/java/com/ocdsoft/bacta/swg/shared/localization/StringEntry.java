package com.ocdsoft.bacta.swg.shared.localization;

import lombok.Getter;
import lombok.Setter;

public class StringEntry {
	@Getter @Setter private StringId stringId;
	@Getter private final String value;
	
	public StringEntry(StringId stringId, String value) {
		this.stringId = stringId;
		this.value = value;
	}
	
	public StringEntry(String value) {
		this.stringId = StringId.empty;
		this.value = value;
	}
}
