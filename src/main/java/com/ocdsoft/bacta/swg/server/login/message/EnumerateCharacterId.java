package com.ocdsoft.bacta.swg.server.login.message;


import com.ocdsoft.bacta.swg.network.soe.object.account.CharacterInfo;
import com.ocdsoft.bacta.swg.network.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

import java.util.List;

public class EnumerateCharacterId extends SwgMessage {
	public EnumerateCharacterId(SoeAccount account) {
		super(0x02, 0x65EA4574);

        List<CharacterInfo> charMap = account.getCharacterList();

		writeInt(charMap.size());

		for(CharacterInfo info : charMap) {
            writeUnicode(info.getName());
            writeInt(info.getObjectTemplateId());
            writeLong(info.getNetworkId());
            writeInt(info.getClusterId());
            writeInt(info.getCharacterType());
		}
	}
}
