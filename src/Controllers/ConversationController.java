/*
@layer: Controller
@collaborators: EventManager, ConversationManager, UserManager, VenueManager
 */

package Controllers;

import UseCases.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ConversationController {
    private final EventManager em;
    private final ConversationManager cm;
    private final UserManager um;
    private final VenueManager vm;
    private final MessagesManager mm;

    /**
     * Constructor for ConversationController
     * @param mm: MessagesManager instance
     * @param cm: ConversationManager instance
     * @param um: UserManager instance
     * @param em: EventManager instance
     * @param vm: VenueManager instance
     */
    public ConversationController(EventManager em, ConversationManager cm, UserManager um, VenueManager vm,
                                  MessagesManager mm) {
        this.em = em;
        this.cm = cm;
        this.um = um;
        this.vm = vm;
        this.mm = mm;
    }

    /**
     * make a new empty conversation with a specific title
     * @param convoTitle title of this conversation
     * @param userIDs participants
     */
    public void buildConversation(String convoTitle, ArrayList<Integer> userIDs){
        if (userIDs.size() <= 20) {
            cm.newConversationFriends(userIDs, convoTitle, um);
        } else {
            cm.newConversationAnnouncement(convoTitle, userIDs, um);
        }
    }

    /**
     * Deletes convo from user
     * @author Priyanka
     * @param conversationID ID of this conversation
     * @param userID user's ID
     */
    public void deleteConvoFromUser(int conversationID, int userID){
        um.deleteConversationFromUser(conversationID, userID);
    }

    /**
     * Returns true iff the currently logged in user can message in the given conversation.
     * @param conversationID the conversation to be messaged in
     * @param userID ID of the user
     * @return whether the user can message
     */
    public boolean currentlyLoggedInCanMessage(int conversationID, int userID) {
        return cm.currentlyLoggedInCanMessage(conversationID, userID, um);
    }

    /**
     * Gets a string representation of a given conversation.
     * @param conversationID the conversation ID
     * @return a string rep of the conversation
     */
    public String getStringRep(int conversationID) {
        return cm.getStringRep(conversationID);
    }

    /**
     * Get numOfMessages number of messages from a conversation.
     * @param conversationID the conversationID
     * @param numOfMessages the number of recent messages to return
     * @param LC LoginController
     * @return A string formatted with message sender and message content
     * for numOfMessages number of messages from some conversation.
     */
    public String getRecentMessages(int conversationID, int numOfMessages, LoginController LC){
        return cm.getRecentMessages(conversationID, numOfMessages, mm, um, LC.getCurrentUserID());
    }
    /**
     * Sends a message in a given conversation
     * @param conversationID the conversation ID
     * @param userID ID of the user
     * @param messageText the content of the message
     */
    public void sendMessage(int conversationID, String messageText, int userID) {
        cm.sendMessage(conversationID, messageText, userID, mm, um);
    }

    /**
     * Gets all conversations the user is currently in
     * @param userid ID of the user
     * @return Hashmap(K,V), where K: Conversation name and V: Conversation id
     */
    public HashMap<String, Integer> getConvos(int userid) {
        return cm.getConversationMap(userid, um);
    }

    /**
     * Ensures that the message being sent is not blank
     * @author Priyanka
     * @param messageText: the text in the message
     * @return true iff message is not a blank space
     */
    public Boolean blankMessage(String messageText)
    {
        return !messageText.equals(" ");
    }

    /**
     * Delete the message with the given ID
     * @param messageID ID of the message
     */
    public void deleteMessage(int messageID) {
        mm.deleteMessage(messageID);
    }

    /**
     * Archive the message with the given ID
     * @param messageID ID of the message
     */
    public void archiveMessage(int messageID) {
        mm.archiveMessage(messageID);
    }

    /**
     * Unarchive the message with the given ID
     * @param messageID ID of the message
     */
    public void unarchiveMessage(int messageID) {
        mm.unArchiveMessage(messageID);
    }

    /**
     * Mark the message with the given ID as read if the current user is not the
     * sender of the message. If the current user is the sender of the message, do
     * nothing.
     * @param messageID ID of the message
     * @param LC the LogicController
     */
    public void markAsRead(int messageID, LoginController LC) {
        mm.changeMessageRead(true, messageID, LC.getCurrentUserID());
    }

    /**
     * Mark the message with the given ID as unread if the current user is not the
     * sender of the message. If the current user is the sender of the message, do
     * nothing.
     * @param messageID ID of the message
     * @param LC LoginController
     */
    public void markAsUnread(int messageID, LoginController LC) {
        mm.changeMessageRead(false, messageID, LC.getCurrentUserID());
    }

    /**
     * Return the recent archived messages in the conversation with the given ID
     * @param conversationID ID of the conversation
     * @param numOfMessages required number of recent messages
     * @return HashMap of the last numOfMessages messages in this conversation such that
     *         K: the message content and,
     *         V: the message ID
     */
    public HashMap<String, Integer> getRecentMessagesArchived(int conversationID, int numOfMessages) {
        return cm.getRecentMessagesDict(conversationID, numOfMessages, mm, true);
    }

    /**
     * Return the recent un-archived messages in the conversation with the given ID
     * @param conversationID ID of the conversation
     * @param numOfMessages required number of recent messages
     * @return HashMap of the last numOfMessages messages in this conversation such that
     *         K: the message content and,
     *         V: the message ID
     */
    public HashMap<String, Integer> getRecentMessagesUnarchived(int conversationID, int numOfMessages) {
        return cm.getRecentMessagesDict(conversationID, numOfMessages, mm, false);
    }

    /**
     * get the sender ID of the message
     * @param messageID the id of the message
     * @return the ID of the sender
     */
    public Integer getSenderID(int messageID){
        return mm.getSenderID(messageID);
    }
}
