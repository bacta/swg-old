package com.ocdsoft.bacta.swg.server.game.network;

/**
 * Created by crush on 4/22/2016.
 * <p>
 * Seems to be an interface with helper methods for creating chat messages to the chat server.
 */
public interface Chat {
    void addFriend(final String player, final String friendName);

    void removeFriend(final String player, final String friendName);
}
