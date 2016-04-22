package com.ocdsoft.bacta.swg.chat;

/**
 * Created by crush on 4/22/2016.
 */
public interface Chat {
    void addFriend(final String player, final String friendName);

    void removeFriend(final String player, final String friendName);

    void addIgnore(final String player, final String friendName);

    void removeIgnore(final String player, final String friendName);

    void addModeratorToRoom(final String who, final String roomPath);

    void removeModeratorFromRoom(final String who, final String roomPath);

    void createRoom(final String ownerName, final boolean isPublic, final String roomPath, final String roomTitle);

    void createSystemRooms(final String galaxyName, final String planetName);

    void destroyRoom(final String roomPath);

    void enterRoom(final String who, final String roomPath, final boolean forceCreate, final boolean createPrivate);

    void exitRoom(final String who, final String roomPath);

    void sendInstantMessage(final String from, final String to, final String message, final String oob);

    void sendSystemMessage();

    void emptyMail(final long sourceNetworkId, final long targetNetworkId);

    void sendPersistentMessage(final String from, final String to, final String subject, final String message, final String oob);
    //void sendPersistentMessage(final ServerObject from, final String to, final StringId subject, final String message, final String oob);
    //void sendPersistentMessage(final ServerObject from, final String to, final StringId subject, final StringId message, final String oob);

    //void sendToChatserver(final GameNetworkMessage message);
}
