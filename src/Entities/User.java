/*
@layer: Entity
 */

package Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An abstract class for a user of this system.
 */
public abstract class User implements Serializable {
    private String username;
    private String password;
    private final int ID;
    private String email;
    private final ArrayList<Integer> conversations = new ArrayList<>();
    private boolean isBanned = false;

    /**
     * Represents a user in general
     * @param username User's username
     * @param password User's password
     * @param id User's unique ID
     * @param email User's email address
     */
    public User(String username, String password, int id, String email){
        this.username = username;
        this.password = password;
        this.ID = id;
        this.email = email;
    }

    // getters
    /**
     * Getter for the username of this user
     * @return username of this user
     */
    public String getUsername(){
        return username;
    }

    /**
     * Getter for the email of this user
     * @author Priyanka
     * @return email of this user
     */
    public String getEmail(){
        return email;
    }

    /**
     * Getter for the password of this user
     * @return password of this user
     */
    public String getPassword(){
        return password;
    }

    /**
     * Getter for the id of this user
     * @return id of this user
     */
    public int getID(){
        return ID;
    }

    /**
     * Get all conversations this user has
     * @return list of conversation ids
     */
    public ArrayList<Integer> getConversations() {
        return conversations;
    }

    /**
     * Get the permission level of this user
     * @return permission lvl
     */
    public abstract int getPermission();
    // getters end

    // setters
    /**
     * Set the username of this user
     * @param newUser new username
     */
    public void setUsername(String newUser){
        this.username = newUser;
    }

    /**
     * Set the password of this user
     * @param newPass new password
     */
    public void setPassword(String newPass){
        this.password = newPass;
    }

    /**
     * Set the email of this user
     * @author priyanka
     * @param newEmail new email
     */
    public void setEmail(String newEmail){
        this.email = newEmail;
    }
    // setters end

    /**
     * Add a conversation to this User's conversations list.
     * @param conversationID id of the Conversation to be added
    */
    public void addConversation(int conversationID) {
        conversations.add(conversationID);
    }

    /**
     * Removes a conversation from this User's conversations list.
     * @param conversationID id of the Conversation to be removed
     */
    public void removeConversation(int conversationID){ conversations.remove(conversationID);}

    /**
     * Ban a user, disable to log in
     */
    public void blockUser(){ isBanned = true;}

    /**
     * Unblock  a user, make it able to log in
     */
    public void unblockUser(){ isBanned = false; }

    /**
     * Check if the user is banned
     * @return if the user is banned
     */
    public boolean isBanned(){ return isBanned; }

}



