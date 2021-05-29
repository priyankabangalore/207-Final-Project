/*
@layer: Use Case
@collaborators: User entity
 */

package UseCases;

import Entities.*;

import java.util.ArrayList;
import java.util.HashMap;

public class UserManager {

    private final ArrayList<User> users;
    private final ArrayList<Integer> userIDs;
    private final ArrayList<String> emails;

    /**
     * Constructor for UserManager
     * @param users: list of all Users in system
     * @param ids: list of all Ids of Users in system
     */
    public UserManager(ArrayList<User> users, ArrayList<Integer> ids) {
        this.users = users;
        this.userIDs = ids;
        this.emails = new ArrayList<>();
        for (User u : users) {
            emails.add(u.getEmail());
        }
    }

    /**
     * Gets IDs of all users in system.
     * @return List of all user IDs
     */
    public ArrayList<Integer> getUserIDs() {
        return userIDs;
    }

    /**
     * Get the user with the given id
     *
     * @param userID id of a user
     * @return return the User who matches the id
     */
    public User getUser(Integer userID) {
        for (User user : users) {
            if (user.getID() == userID) {
                return user;
            }
        }
        return null;
    }

    /**
     * Add the user to the list of all users
     *
     * @param user the new user that needs to be added
     */
    public void addUser(User user) {
        this.users.add(user);
        this.userIDs.add(user.getID());
    }

    /**
     * Gets user's username
     *
     * @param userID: the user itself
     * @return String of user's username
     */
    public String getUserName(Integer userID) {
        User user = getUser(userID);
        if (user == null) {
            return "This user does not exist.";
        }
        return user.getUsername();
    }

    /**
     * Sets user's username when changed
     * @author Priyanka
     * @param userID: the user itself
     * @return true iff set
     */
    public boolean setUserNameWhenChanged(Integer userID, String newUserName) {
        User user = getUser(userID);
        if (user != null) {
            user.setUsername(newUserName);
            return true;
        }
        return false;
    }

    /**
     * Sets user's password when changed
     * @author Priyanka
     * @param userID: the user itself
     * @return true iff set
     */
    public boolean setPasswordWhenChanged(Integer userID, String newPW) {
        User user = getUser(userID);
        if (user != null) {
            user.setPassword(newPW);
            return true;
        }
        return false;
    }

    /**
     * Sets user's email when changed
     * @author Priyanka
     * @param userID: the user itself
     * @return true iff set
     */
    public boolean setEmailWhenChanged(Integer userID, String newEmail) {
        User user = getUser(userID);
        if (user != null) {
            user.setEmail(newEmail);
            return true;
        }
        return false;
    }

    /**
     * Gets user's email given their ID
     * @author Priyanka
     * @param userID: the user itself
     * @return String of user's email
     */
    public String getEmail(Integer userID) {
        User user = getUser(userID);
        if (user == null) {
            return "";
        }
        return user.getEmail();
    }

    /**
     * Generate a unique ID for a new user being created
     *
     * @return unique ID of type Integer
     */
    public int generateID() {
        return this.userIDs.size();
    }

    /**
     * Add a conversation to some users' conversation list
     * @param userIDs list of user ids
     * @param convoID id of the conversation to be added
     */
    public void addConversation(ArrayList<Integer> userIDs, Integer convoID) {
        for (Integer userid : userIDs) {
            User user = getUser(userid);
            if (user != null) {
                user.addConversation(convoID);
            }
        }
    }

    /**
     * Gets the permission level of the user (attendee, organizer, speaker)
     *
     * @param userID id of the user
     * @return int of the permission level
     */
    public int getPermissionLvl(Integer userID) {
        User user = getUser(userID);
        if (user == null) {
            return -1;
        }
        return user.getPermission();
    }

    /**
     * Validate the id and password
     * @param userID id of a user
     * @param PW password
     * @return true if userid and password match
     */
    public boolean validatePW(Integer userID, String PW) {
        User user = getUser(userID);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(PW);
    }

    /**
     * Validate the email
     * @author Priyanka
     * @param email email provided
     * @return true iff email contains necessary symbols
     */
    public boolean validateEmail(String email) {
        return email.contains("@") && email.contains(".com") || email.contains("@") && email.contains(".ca");
    }

    /**
     * Removes a conversation from a user's conversation list
     * @author Priyanka
     * @param userID ID of user
     * @param conversationID id of the conversation to be removed
     */
    public void deleteConversationFromUser(int conversationID, int userID) {
        User user = getUser(userID);
        if (user != null) {
            user.removeConversation(conversationID);
        }
    }

    /**
     * ban a user
     * @param userID the user ID that will be banned
     */
    public void blockUser(int userID){
        if (userIDs.contains(userID)){
            getUser(userID).blockUser();
        }
    }
    /**
     * unblock a user
     * @param userID the user ID that will be unblocked
     */
    public void unblockUser(int userID){
        if (userIDs.contains(userID)){
            getUser(userID).unblockUser();
        }
    }

    /**
     * Get all users stored in the program
     * @return hashmap of the form
     *         K: username,
     *         V: userID
     */
    public HashMap<String, Integer> getAllUser(){
        HashMap<String, Integer> usermap = new HashMap<>();
        for(int userID: userIDs){
            usermap.put(getUserName(userID), userID);
        }
        return usermap;
    }

    /**
     * get all unblocked user
     * @return hashmap of the form
     *          k: username
     *          V: userID
     */
    public HashMap<String, Integer> getUnblockedUser() {
        HashMap<String, Integer> usermap = new HashMap<>();
        for (int userID : userIDs) {
            if (!getUser(userID).isBanned()) {
                usermap.put(getUserName(userID), userID);
            }
        }
        return usermap;
    }
    /**
     * get all blocked user
     * @return hashmap of the form
     *          k: username
     *          V: userID
     */
    public HashMap<String, Integer> getBlockedUser() {
        HashMap<String, Integer> usermap = new HashMap<>();
        for (int userID : userIDs) {
            if (getUser(userID).isBanned()) {
                usermap.put(getUserName(userID), userID);
            }
        }
        return usermap;
    }

    /**
     * Get the status of the user, i.e. whether the user is banned or not
     * @param userID the ID of the user you want to check
     * @return true iff the user is banned and false if the user is not banned
     */
    public boolean isBanned(int userID){
        return getUser(userID).isBanned();
    }
}

