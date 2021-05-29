/*
@layer: Use Case
@collaborators: Conversation entity, User entity, Organizer entity, UserManager, EventManager, VenueManager
 */

package UseCases;

import Entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Stores ArrayList<Conversation> containing all conversations of this program.
 */
public class ConversationManager {
    private final ArrayList<Conversation> conversations;

    /**
     * Constructor for ConversationManager
     * @param conversations: list of all Conversations in system
     */
    public ConversationManager(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
    }

    /**
     * Get all the conversations stored in this manager
     *
     * @return list of conversations
     */
    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    /**
     * Create a string for participants' names
     *
     * @param participants ArrayList<User>, contains participants of this Conversation.
     * @return String of name for this conversation.
     */
    public String generateName(ArrayList<User> participants) {
        StringBuilder c = new StringBuilder();
        for (User i : participants){
            c.append(i.getUsername());
        }
        return c.toString();
    }

    /**
     * Create a unique ID for this conversation.
     * @return int
     */
    public Integer generateID() {
        if (conversations.isEmpty()){
            return 1;
        }
        return conversations.get(conversations.size() -1).getID() + 1;
    }

    /**
     * Creating a new empty conversation with a specific title
     *
     * @param friendsID ArrayList of UserID
     * @param title title of this new conversation
     * @param um UserManager
     */
    public void newConversationFriends(ArrayList<Integer> friendsID, String title, UserManager um){
        int tempID = generateID();
        conversations.add(new FriendConversation(friendsID, tempID, title));
        um.addConversation(friendsID, tempID);
    }

    /**
     * Creates an Announcement-type Conversation, between one sender and multiple receivers of an Announcement Message.
     * @param name conversation title
     * @param participantIDs list of ids of participants
     * @param um UserManager
     * @return this Conversation.
     */
    public Conversation newConversationAnnouncement(String name,ArrayList<Integer> participantIDs, UserManager um){
        int newID = generateID();
        conversations.add(new Announcement(participantIDs, newID, name));
        um.addConversation(participantIDs, newID);
        return conversations.get(conversations.size() - 1);
    }

    /**
     * Send an Announcement from the Organizer of an Event to all Attendees of an Event.
     * @param senderID Integer, ID of the Organizer sender of this Announcement
     * @param AttendeeIDs ArrayList containing Integer IDs of receivers of this Announcement
     * @param eventID Integer, ID of event whose Attendees are being sent the Announcement
     * @param um UserManager
     * @param em EventManager
     * @param vm VenueManager
     * @param MM MessagesManager
     */
    public void sendAnnouncement(Integer senderID, ArrayList<Integer> AttendeeIDs, Integer eventID,
                                 UserManager um, EventManager em, VenueManager vm, MessagesManager MM){
        String s = "You are registered for " + em.getEventName(eventID) + " at " + em.getStartTime(eventID) + " in Room" +
                vm.getRoomName(em.getEventRoom(eventID));
        Message m = MM.createMessage(s, senderID);
        ArrayList<Integer> participantIDs = new ArrayList<>();
        ArrayList<User> participants = new ArrayList<>();
        ArrayList<Integer> ids = em.getAttendees(eventID);
        for (Integer AttendeeID: AttendeeIDs) {
            if (ids.contains(AttendeeID)) {
                participantIDs.add(AttendeeID);
                participants.add(um.getUser(AttendeeID));
            }
        }
        participantIDs.add(senderID);
        participants.add(um.getUser(senderID));
        Conversation c = newConversationAnnouncement(generateName(participants), participantIDs, um);
        c.addMessage(m.getMessageID());
    }

    /**
     * Gets the conversation associated with an ID
     *
     * @param conversationID id of the conversation
     * @return conversation object
     */
    private Conversation getConversation(int conversationID) {
        for (Conversation conversation : this.conversations) {
            if (conversation.getID() == conversationID) {
                return conversation;
            }
        }
        return null;
    }

    /**
     * Gets a string representation of a given conversation.
     * @param conversationID the conversation ID
     * @return a string rep of the conversation
     */
    public String getStringRep(int conversationID) {
        Conversation conversation = getConversation(conversationID);
        if (conversation != null) {
            return "Conversation " + conversation.getName();
        } else {
            return "Cannot find this conversation";
        }
    }

    /**
     * Get numOfMessages recent number of messages from a conversation
     * with ID conversationID.
     * @param conversationID The conversationID
     * @param numOfMessages The number of messages to return
     * @param um User Manager
     * @param mm Messages Manager
     * @param userID ID of the currently logged in user who is accessing the conversation
     * @return a string representing numOfMessages number of messages from conversation
     * with ID conversationID.
     */
    public String getRecentMessages(int conversationID, int numOfMessages, MessagesManager mm,
                                    UserManager um, int userID) {
        Conversation conversation = getConversation(conversationID);
        if (conversation != null) {
            ArrayList<Integer> messages = conversation.getMessages();
            int messageNumber = Math.min(numOfMessages, messages.size());
            List<Integer> recentMessages = messages.subList(messages.size() - messageNumber, messages.size());

            StringBuilder conversationString = new StringBuilder();
            for (int messageID : recentMessages) {
                Message message = mm.getMessage(messageID);
                conversationString.append("[" + message.getTime() + "]" +
                        " " + um.getUserName(message.getSenderID()) + ": ");
                conversationString.append(message.getContent());
                if (mm.validateMessageSender(messageID, userID)) {
                    conversationString.append("\n");
                    Boolean messageReadStatus = mm.getMessageRead(messageID);
                    conversationString.append(messageReadStatus.toString());
                }
                conversationString.append("\n");
                mm.changeMessageRead(true, messageID, userID);
            }
            return conversationString.toString();
        } else {
            return "Cannot find this conversation.";
        }
    }

    /**
     * Gets all a map of conversations the user is currently log in
     * @param userID id of current logged in user
     * @param um UserManager
     * @return Hashmap(K,V), where K: Conversation name and V: Conversation id
     */
    public HashMap<String, Integer> getConversationMap(int userID, UserManager um) {
        User user = um.getUser(userID);
        if (user == null) {
            return new HashMap<>();
        }
        ArrayList<Integer> convoIDs = user.getConversations();
        HashMap<String, Integer> map = new HashMap<>();
        for (Integer id : convoIDs) {
            map.put(getStringRep(id), id);
        }
        return map;
    }

    /**
     * Send a message with the given text in the given conversation
     *
     * @param conversationID ID of the conversation
     * @param messageText text of the message
     * @param senderID ID of the sender
     * @param MM Messages Manager
     * @param um User Manager
     */
    public void sendMessage(int conversationID, String messageText, int senderID, MessagesManager MM,
                            UserManager um) {
        Conversation conversation = getConversation(conversationID);
        if (conversation != null) {
            if (currentlyLoggedInCanMessage(conversationID, senderID, um)) {
                int ID = MM.sendMessage(messageText, senderID);
                conversation.addMessage(ID);
            }
        } else {
            System.out.println("Conversation does not exist.");
        }
    }

    /**
     * Returns true iff the user can message in the given conversation.
     *
     * @param userID user ID
     * @param conversationID the conversation to be messaged in
     * @param um the user manager
     * @return true iff the user can message in the given conversation
     */
    public boolean currentlyLoggedInCanMessage(int conversationID, int userID, UserManager um) {
        Conversation conversation = getConversation(conversationID);
        if (conversation == null) {
            return false; // conversation does not exist
        }
        if (conversation.getParticipants().contains(userID)) {
            User user = um.getUser(userID);
            if (conversation.isOneway()) {
                return user.getPermission() > 0;
            }
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the recent archived OR unarchived messages in the conversation with the given ID
     * @param conversationID ID of the conversation
     * @param mm MessagesManager
     * @param archived true if the archived messages in the conversation are required
     *                 false if the unarchived messages in the conversation are required
     * @param numOfMessages number of recent messages required
     * @return HashMap of the last numOfMessages messages in this conversation such that
     *         K: the message content and,
     *         V: the message ID
     */
    public HashMap<String, Integer> getRecentMessagesDict(int conversationID, int numOfMessages,
                                                          MessagesManager mm, boolean archived) {
        Conversation conversation = getConversation(conversationID);
        if (conversation == null) {
            return new HashMap<>();
        }
        ArrayList<Integer> messages = conversation.getMessages();
        int messageNumber = Math.min(numOfMessages, messages.size());
        List<Integer> recentMessageIDs = messages.subList(messages.size() - messageNumber, messages.size());
        HashMap<String, Integer> recentMessages = new HashMap<>();
        for (Integer ID: recentMessageIDs) {
            Message message = mm.getMessage(ID);
            if (message == null) {
                continue;
            }
            if (message.getMessageArchived() == archived) {
                recentMessages.put(message.getContent(), message.getMessageID());
            }
        }
        return recentMessages;
    }

    /**
     * Get a hashmap for all conversations in the system
     * @author Tian
     * @return hashMap such that
     *         K: the name of the conversation
     *         V: the ID of the conversation
     */
    public HashMap<String, Integer> showAllConversation(){
        HashMap<String, Integer> conversationMap = new HashMap<>();
        for (Conversation c: conversations){
            conversationMap.put(c.getName(), c.getID());
        }
        return conversationMap;
    }

    /**
     * Delete a conversation with the ID
     * @param conversationID the ID of the conversation
     */
    public void deleteConversation(int conversationID){
        conversations.remove(getConversation(conversationID));
    }
}


