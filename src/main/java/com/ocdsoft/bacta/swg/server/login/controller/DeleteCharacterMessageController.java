package com.ocdsoft.bacta.swg.server.login.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.object.account.CharacterInfo;
import com.ocdsoft.bacta.swg.network.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.login.LoginClient;
import com.ocdsoft.bacta.swg.server.login.message.DeleteCharacterMessage;
import com.ocdsoft.bacta.swg.server.login.message.DeleteCharacterReplyMessage;
import com.ocdsoft.network.security.authenticator.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SwgController(server= ServerType.LOGIN, handles=DeleteCharacterMessage.class)
public class DeleteCharacterMessageController implements SwgMessageController<LoginClient> {

	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final AccountService<SoeAccount> accountService;

    @Inject
    public DeleteCharacterMessageController(AccountService<SoeAccount> accountService) {
        this.accountService = accountService;
    }

	@Override
	public void handleIncoming(LoginClient client, SoeByteBuf buffer) {

        int clusterId = buffer.readInt();
	    long characterId = buffer.readLong();

        boolean success = false;

        SoeAccount account = accountService.getAccount(client.getAccountUsername());

        if(account != null) {
            List<CharacterInfo> charList = account.getCharacterList();
            for(CharacterInfo info : charList) {
                if(info.getNetworkId() == characterId && info.getClusterId() == clusterId) {
                    info.setDisabled(true);
                    accountService.updateAccount(account);
                    success = true;
                    break;
                }
            }
        }

        DeleteCharacterReplyMessage reply = new DeleteCharacterReplyMessage(success);
        client.sendMessage(reply);
	}
}
