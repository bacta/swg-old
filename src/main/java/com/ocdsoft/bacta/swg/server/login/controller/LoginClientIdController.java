package com.ocdsoft.bacta.swg.server.login.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.login.LoginClient;
import com.ocdsoft.bacta.swg.server.login.message.*;
import com.ocdsoft.bacta.swg.server.login.object.ClusterList;
import com.ocdsoft.network.security.authenticator.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server = ServerType.LOGIN, handles = LoginClientId.class)
public class LoginClientIdController implements SwgMessageController<LoginClient> {

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private static final int TIMEZONE = 0xFFFFB9B0; //TODO Refactor this?

    private final ClusterList clusterList;
    private final AccountService<SoeAccount> accountService;

    @Inject
    public LoginClientIdController(ClusterList clusterList, AccountService<SoeAccount> accountService) {
        this.clusterList = clusterList;
        this.accountService = accountService;
    }

    @Override
    public void handleIncoming(LoginClient client, SoeByteBuf message) {

        String username = message.readAscii();
        String password = message.readAscii();
        String clientVersion = message.readAscii();

        // Validate client version
        if (!isRequiredVersion(clientVersion)) {
            client.sendErrorMessage("Login Error", "The client you are attempting to connect with does not match that required by the server.", false);
            return;
        }

        if (password.isEmpty()) {
            client.sendErrorMessage("Login Error", "Please enter a password.", false);
            return;
        }

        SoeAccount account = null;
        try {

            account = accountService.getAccount(username);

        } catch (Exception e) {
            client.sendErrorMessage("Login Error", "Duplicate Accounts in the database", false);
            logger.error("Duplicate accounts in database", e);
            return;
        }

        if (account == null) {
            account = accountService.createAccount(username, password);
            if (account == null) {
                client.sendErrorMessage("Login Error", "Unable to create account.", false);
                return;
            }
        } else if (!accountService.authenticate(account, password)) {
            client.sendErrorMessage("Login Error", "Invalid username or password.", false);
            return;
        }

        accountService.createAuthToken(account);
        client.setAccountId(account.getId());
        client.setAccountUsername(account.getUsername());

        LoginClientToken token = new LoginClientToken(account);
        client.sendMessage(token);

        LoginEnumCluster cluster = new LoginEnumCluster(clusterList, TIMEZONE);
        client.sendMessage(cluster);

        LoginClusterStatus status = new LoginClusterStatus(clusterList, TIMEZONE);
        client.sendMessage(status);

        EnumerateCharacterId characters = new EnumerateCharacterId(account);
        client.sendMessage(characters);
    }

    private boolean isRequiredVersion(String clientVersion) {
        return clientVersion.equals("20050408-18:00");
    }
}
