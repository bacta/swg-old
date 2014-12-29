package com.ocdsoft.bacta.swg.server.login.message;


import com.ocdsoft.bacta.swg.network.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class LoginClientToken extends SwgMessage {

	public LoginClientToken(SoeAccount account) {
		super(0x04, 0xAAB296C6);

        writeBinaryString(account.getAuthToken());

		writeInt(account.getId()); // Station ID
		
		writeAscii(account.getUsername()); // UserName
		
		writeByte(0);
		writeShort(0);
	}

    /**  Example
	 *  04 00 
	 *  C6 96 B2 AA 
	 *  08 00 00 00 
	 *  EF F8 3C 5D 
	 *  66 28 00 00 
	 *  40 D9 52 76 
	 *  04 00 4B 79 6C 65 
	 *  00 
	 *  00 00 
	 */
}
