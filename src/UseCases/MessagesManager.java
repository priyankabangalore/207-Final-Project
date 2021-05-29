package UseCases;

import Entities.Message;

import java.util.ArrayList;

public class MessagesManager {

    private final ArrayList<Integer> messageIDs;
    private final ArrayList<Message> messages;
    private final ArrayList<Message> archivedMessages;

    /**
     * Constructor for MessagesManager
     * @param messages: list of Messages
     * @param ids: Ids of messages in system
     * @param archivedMessages: list of archived Messages
     */
    public MessagesManager(ArrayList<Message> messages, ArrayList<Integer> ids, ArrayList<Message> archivedMessages) {
        this.messageIDs = ids;
        this.messages = messages;
        this.archivedMessages = archivedMessages;
    }

    /**
     * Get all the messages stored in this manager
     * @return list of messages
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Get all the messageIDs stored in this manager
     * @return list of messageIDs
     */
    public ArrayList<Integer> getMessageIDs() {
        return messageIDs;
    }

    /**
     * Generate a unique ID for a new message
     * @return unique ID
     */
    public int generateID() {
        return this.messageIDs.size();
    }

    /**
     * Create a new message
     * @param content content of the message
     * @param senderID ID for the sender of the message
     * @return new message object
     */
    public Message createMessage(String content, int senderID) {
        int messageID = generateID();
        this.messageIDs.add(messageID);
        Message message = new Message(content, senderID, messageID);
        this.messages.add(message);
        return message;
    }

    /**
     * Return the message with the given ID, if it exists
     * @param messageID ID of the message
     * @return the message with the given ID iff it exists
     */
    public Message getMessage(int messageID) {
        if (messageIDs.contains(messageID)) {
            for (Message message: this.messages) {
                if (message.getMessageID() == messageID) {
                    return message;
                }
            }
        }
        return null;
    }

    /**
     * Create a new message and return its ID
     * @param content the content of the new message
     * @param senderID ID of the sender of the message
     * @return the unique ID of the message that is being sent
     */
    public int sendMessage(String content, int senderID) { //helper for sending messages in ConversationManager
        Message message = createMessage(content, senderID);
        return message.getMessageID();
    }

    /**
     *Change whether the given message has been read or not, iff the user accessing the message is not the sender
     * @param messageReadStatus true if the message has been read
     *                          false if the message has not been read
     * @param messageID ID of the message
     * @param userID ID of the current user
     */
    public void changeMessageRead(boolean messageReadStatus, int messageID, int userID) {
        Message message = getMessage(messageID);
        if (message != null && message.getSenderID() != userID) {
            message.setMessageRead(messageReadStatus);
        }
    }

    /**
     * Return whether or not the given message has been read by the user
     * @param messageID ID of the message
     * @return true iff the message has been read and has not been marked unread
     */
    public boolean getMessageRead(int messageID) {
        Message message = getMessage(messageID);
        if (message == null) {
            return false;
        }
        return message.getMessageRead();
    }

    /**
     * Delete the given message
     * @param messageID ID of the message
     */
    public void deleteMessage(int messageID) {
        Message message = getMessage(messageID);
        if (message != null) {
            this.messages.remove(message);
        }
    }

    /**
     * Archive this message
     * @param messageID ID of the message
     */
    public void archiveMessage(int messageID) {
        Message message = getMessage(messageID);
        if (message != null) {
            message.setArchived(true);
            this.archivedMessages.add(message);
        }
    }

    /**
     * Unarchive this message iff it has been previously archived. Otherwise, do nothing
     * @param messageID ID of the message
     */
    public void unArchiveMessage(int messageID) {
        Message message = getMessage(messageID);
        if (message != null) {
            if (getMessageArchiveStatus(messageID)) {
                message.setArchived(false);
                this.archivedMessages.remove(message);
            }
        }
    }

    /**
     * Return the archive status of the given message
     * @param messageID ID of the message
     * @return true iff the message has been archived
     */
    public boolean getMessageArchiveStatus(int messageID) {
        Message message = getMessage(messageID);
        if (message == null) {
            return false;
        }
        return message.getMessageArchived();
    }

    /**
     * Validate whether the current user is the same as the sender of this message
     * @param messageID ID of the message
     * @param userID ID of the current user
     * @return true iff the current user is the same as the sender
     */
    public boolean validateMessageSender(int messageID, int userID) {
        Message message = getMessage(messageID);
        if (message == null) {
            return false;
        }
        return message.getSenderID() == userID;
    }

    /**
     * get the message sender ID
     * @param messageID the id of the message
     * @return the ID of the sender
     */
    public int getSenderID(int messageID){
        return getMessage(messageID).getSenderID();
    }
}
