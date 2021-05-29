/*
@layer: Controller
@collaborators: UserManager
 */

package Controllers;

import UseCases.UserManager;

public class LoginController {
    int currentUserID = -1;
    private final UserManager userManager;

    /**
     * Constructor for LoginController
     * @param userManager: UserManager instance
     */
    public LoginController(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Getter for the Logged in userID
     * @return Logged in UserID
     */
    public int getCurrentUserID(){return currentUserID;}

    /**
     * Checks if userID is valid
     * @param userID, the UserID to check the validity of.
     * @return true if the UserID is a valid ID stored in the userManager
     */
    public boolean isValidUserID(int userID) {
        return this.userManager.getUser(userID) != null;
    }

    /**
     * Checks the validity of the user data entered, iff valid, logs in the specified user.
     * @param userID the userID of the user to be logged in
     * @param password the password of the user to be logged in.
     * @param email users email
     * @return True if the user has been logged in, otherwise, false.
     */
    public boolean isValidLogin(int userID, String password, String email)
    {
        boolean validUserID = isValidUserID(userID);
        if (validUserID) {
            if (userManager.validatePW(userID, password) && userManager.validateEmail(email)) {
                this.currentUserID = userID;
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for the username of the Logged in user
     * @return String of username for logged in user
     */
    public String getUsername()
    {
       return userManager.getUserName(currentUserID);
    }

    /**
     * Getter for the email of the Logged in user
     * @author Priyanka
     * @return String of email for logged in user
     */
    public String getEmail() {
        return userManager.getEmail(currentUserID);
    }

    /**
     * Getter for the email of a specific user
     * @param userID user id
     * @return String of email for this user
     */
    public String getEmail(Integer userID) {
        return userManager.getEmail(userID);
    }

    /**
     * Getter for the permission level of the logged in user
     * @return Permission level of logged in User
     */
    public int getPermissionLvl()
    {
        return userManager.getPermissionLvl(currentUserID);
    }

    /**
     * Logout the currentUser, setting the current userId to -1
     * (indicating logged out)
     */
    public void logOut()
    {
        currentUserID = -1;
    }
    /**
     * return if user is banned
     * @param userID user id
     * @return boolean that shows if user is baned
     */
    public boolean isBanned(int userID){ return userManager.isBanned(userID);}

    /**
     * Sets user's password when changed
     * @author Priyanka
     * @param userID: the user itself
     * @param newPW new password
     */
    public void setPassword(Integer userID, String newPW) {
        userManager.setPasswordWhenChanged(userID,newPW);
    }

    /**
     * Sets user's email when changed
     * @author Priyanka
     * @param userID: the user itself
     * @param newEmail new email
     */
    public void setEmail(Integer userID, String newEmail) {
        userManager.setEmailWhenChanged(userID,newEmail);
    }

    /**
     * Sets user's username when changed
     * @author Priyanka
     * @param userID: the user itself
     * @param newU new username
     */
    public void setUsername(Integer userID, String newU) {
        userManager.setUserNameWhenChanged(userID,newU);
    }
}
