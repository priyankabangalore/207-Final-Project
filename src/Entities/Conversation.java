/*
@layer: Entity
 */

package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Conversation implements Serializable {
    private final ArrayList<Integer> messages;
    private final ArrayList<Integer> participants;
    private final int ID;
    private final String name;

    /**
     * Represented a Conversation Instance Object
     * @param participants The participants in a conversation, represented by ID
     * @param ID The ID of the conversation
     * @param name The name of the Conversation
     */
    public Conversation(ArrayList<Integer> participants, int ID, String name) {
        this.participants = participants;
        this.ID = ID;
        this.messages = new ArrayList<>();
        this.name = name;
    }

    // getters
    /**
     * Returns all messages sent sorted by time sent[oldest - newest].
     * @return a list of messages
     */
    public ArrayList<Integer> getMessages() {
        return messages;
    }

    /**
     * Returns all participants in this conversation
     * @return a list of participants
     */
    public ArrayList<Integer> getParticipants() {
        return participants;
    }

    /**
     * Returns the ID of this conversation
     * @return ID of conversation
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Returns the name of this conversation
     * @return name
     */
    public String getName() {
        return name;
    }
    // end getters

    /**
     * Add message to this conversation
     * @param message new message being added to the conversation
     */
    public void addMessage(int message){
        messages.add(message);
    }

    /**
     * Specifies if a conversation is one-way, i.e., cannot be replied to
     * @return true iff the conversation is one-way
     */
    public abstract boolean isOneway();
}
