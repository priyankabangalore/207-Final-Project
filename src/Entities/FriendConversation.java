/*
@layer: Entity
 */

package Entities;

import java.util.ArrayList;

public class FriendConversation extends Conversation {

    /**
     * For conversations between friends, in which everyone can respond (not one way)
     * @param participants the participants in the conversation
     * @param ID the ID of the conversation
     * @param name the name of the conversation
     */
    public FriendConversation(ArrayList<Integer> participants, int ID, String name) {
        super(participants, ID, name);
    }

    /**
     * Checks if a friend conversation is one way
     * @return false as friend conversations are two-way
     */
    @Override
    public boolean isOneway() {
        return false;
    }
}
