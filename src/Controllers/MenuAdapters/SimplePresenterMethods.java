/*
@layer: Presenter - Menu
*/

package Controllers.MenuAdapters;

import Controllers.*;
import Presenters.MenuPresenter;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ManagementCommands.ValidCmd;

import java.util.*;

/**
 * A class responsible for the handling of simple, non-menu oriented user-UI interactions.
 * Displays the set of Menus that do not require complex handling, differs dynamic interactions to ComplexBuilder or
 * SimplePresenterMethods
 */
public class SimplePresenterMethods {

    /** The MenuPresenter that employs this SimplePresenterMethods class.*/
    private final MenuPresenter menuPresenter;

    private final Scanner scanner;

    private final Main main;

    /**
     * Constructor for SimplePresenterMethods.
     * @param menuPresenter type MenuPresenter
     */
    public SimplePresenterMethods(MenuPresenter menuPresenter){
        this.menuPresenter = menuPresenter;
        scanner = menuPresenter.getScanner();
        main = menuPresenter.getMain();
    }

    /**
     * Executes the given command.
     * @param command the command to be executed
     * @return A build command to be executed by main.
     */
    public Optional<Command> execute(Command command){
        return command.execute(this);
    }

    /**
     * Runs the Login sequence for a User (validates UserID
     * inputted username and password
     */
    public boolean runLogin(){
        boolean loginSuccess = false;
        LoginController loginController = main.getLoginController();
        int userID;
        String password;
        String email;
        menuPresenter.showPrompt("Enter \"Back\" to go back");
        while(!loginSuccess) {
            menuPresenter.showPrompt("Enter your User ID code:");
            String input = scanner.nextLine();
            boolean validID;
            try {
                userID = Integer.parseInt(input);
                validID = loginController.isValidUserID(userID);
            } catch (NumberFormatException exception) {
                if(input.equals("Back")){
                    break;
                }
                menuPresenter.showPrompt("User ID can only be a number");
                continue;
            }
            if (!validID) {
                menuPresenter.showPrompt("This user does not exist");
                continue;
            }
            if(loginController.isBanned(userID)){
                menuPresenter.showPrompt("You are banned ಠ╭╮ಠ please contact the admin.");
                continue;
            }
            menuPresenter.showPrompt("Enter your password:");
            password = scanner.nextLine();
            if(password.equals("Back")){
                break;
            }

            email = loginController.getEmail(userID);

            boolean login = loginController.isValidLogin(userID, password, email);
            if (login) {
                menuPresenter.showPrompt("Welcome " + loginController.getUsername() + ", UserID: " + userID);
                menuPresenter.showPrompt("You have permission level " + loginController.getPermissionLvl());
                loginSuccess = true;
            } else {
                menuPresenter.showPrompt("Invalid password!");
            }
        }
        return loginSuccess;
    }

    /**
     * Runs the registration sequence for a user. Validates username
     * and password provided by the User.
     * @return bool
     */
    public boolean runSignUp(int userType) {
        boolean signUpSuccess = false;
        SignUpController signUpController = main.getSignUpController();
        LoginController loginController = main.getLoginController();

        while (!signUpSuccess) {
            menuPresenter.showPrompt("Enter \"Back\" to go back");
            menuPresenter.showPrompt("Enter a username: ");
            menuPresenter.showPrompt("Note: this will be visible to all users. ");
            String username = scanner.nextLine();
            if(username.equals("Back")){
                break;
            }
            boolean validUsername = signUpController.isValidUsername(username);
            if (!validUsername) {
                menuPresenter.showPrompt("Not a valid username!");
                continue;
            }
            menuPresenter.showPrompt("Enter your email:");
            String email = scanner.nextLine();
            if(email.equals("Back")){
                break;
            }
            boolean validEmail = signUpController.isValidEmail(email);
            if (!validEmail) {
                menuPresenter.showPrompt("Not a valid email!");
                continue;
            }
            menuPresenter.showPrompt("Enter an alphanumeric password that contains at least 9 characters: ");
            String password = scanner.nextLine();
            if(password.equals("Back")){
                break;
            }
            boolean validPW = signUpController.isValidPass(password);
            if (!validPW) {
                menuPresenter.showPrompt("Not a valid password!");
                continue;
            }

            int registeredUserID = signUpController.registerUser(username, password, userType, email);
            // automatically log in the User after registration
            loginController.isValidLogin(registeredUserID, password, email);
            menuPresenter.showPrompt("Registered as " + username + ", UserID: " + registeredUserID + " ◉‿◉");
            menuPresenter.showPrompt("Logged in as " + username + ", UserID: " + registeredUserID + " ◉‿◉");
            menuPresenter.showPrompt("Permission level 0");
            signUpSuccess = true;
        }
        return signUpSuccess;
    }

    /**
     * Runs the registration sequence for a user. Validates username
     * and password provided by the User.
     */
    public boolean runSignUpNoLogin(int userType) {
        boolean signUpSuccess = false;
        SignUpController signUpController = main.getSignUpController();
        LoginController loginController = main.getLoginController();

        while (!signUpSuccess) {
            menuPresenter.showPrompt("Enter \"Back\" to go back");
            menuPresenter.showPrompt("Enter a username: ");
            menuPresenter.showPrompt("Note: this will be visible to all users. ");
            String username = scanner.nextLine();
            if(username.equals("Back")){
                break;
            }
            boolean validUsername = signUpController.isValidUsername(username);
            if (!validUsername) {
                menuPresenter.showPrompt("Not a valid username!");
                continue;
            }

            menuPresenter.showPrompt("Enter an email: ");
            String email = scanner.nextLine();
            if(email.equals("Back")){
                break;
            }

            boolean validEmail = signUpController.isValidEmail(email);
            if (!validEmail) {
                menuPresenter.showPrompt("Not a valid email!");
                continue;
            }

            menuPresenter.showPrompt("Enter an alphanumeric password that contains at least 9 characters: ");
            String password = scanner.nextLine();
            if(password.equals("Back")){
                break;
            }

            boolean validPW = signUpController.isValidPass(password);
            if (!validPW) {
                menuPresenter.showPrompt("Not a valid password!");
                continue;
            }

            int registeredUserID = signUpController.registerUser(username, password, userType, email);
            menuPresenter.showPrompt("Registered as " + username + ", UserID: " + registeredUserID + " ◉‿◉ ");
            signUpSuccess = true;
        }
        return signUpSuccess;
    }


    /**
     * Displays all the events the currently logged in speaker is speaking at.
     * @return null optional
     */
    public Optional<Command> displaySpeakerEvents(){
        int currentUserID = main.getLoginController().getCurrentUserID();
        Set<String> eventNames  = main.getSpeakerController().getSchedule(currentUserID).keySet();

        for (String event : eventNames){
            menuPresenter.showPrompt(event);
        }
        if(eventNames.isEmpty())
        {
            menuPresenter.showPrompt("You are not set to speak at any events.");
        }
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
        return Optional.empty();
    }

    /**
     * Displays all the events that the logged in attendee's friends are attending
     * @author Priyanka
     * @return null optional
     */
    public Optional<Command> displayFriendsEvents(){
        int currentUserID = main.getLoginController().getCurrentUserID(); //gets current user
        ArrayList<String> friendEventNames  = main.getAttendeeController().getFriendsEvents(currentUserID); //gets friends' events
        if (friendEventNames.isEmpty()) {
            menuPresenter.showPrompt("There are no applicable events.");
        }
        for (String friendEvent : friendEventNames){
            menuPresenter.showPrompt(friendEvent); //prints friends' events
        }
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
        return Optional.empty();
    }

    /**
     * Displays all the events the currently logged in attendee is attending at.
     */
    public void displayAttendeeEvents() {
        Set<String> eventNames = (main.getAttendeeController().getEventsSignedUp(main.getLoginController().
                getCurrentUserID()).keySet());
        for (String event : eventNames) {
            menuPresenter.showPrompt(event);
        }
        if(eventNames.isEmpty())
        {
            menuPresenter.showPrompt("You are not signed up for any events.");
        }
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
    }

    /**
     * Displays the creation of a new room being added.
     * @return null optional
     */
    public String newRoom(){
        menuPresenter.showPrompt("Enter the name of the new room");
        String input = scanner.nextLine();
        ValidCmd test = new ValidCmd();
        test.valid(input);
        String roomName = input;
        /*
        while (true) {
            menuPresenter.showPrompt("Enter the capacity of the new room");
            input = scanner.nextLine();
            if (input.matches("\\d+")){
                break;
            }
            menuPresenter.showPrompt("Enter a number greater than 1");
        }
        */
        menuPresenter.showPrompt(roomName + " added to the system.");

        return input;
    }

    /**
     * Changes users username
     * @author Priyanka
     */
    public void changeUsername()
    {
        int currentUserID = main.getLoginController().getCurrentUserID();
        SignUpController sc = main.getSignUpController();
        menuPresenter.showPrompt("Enter your new username");
        String u = scanner.nextLine();
        if(sc.isValidUsername(u))
        {
            main.getLoginController().setUsername(currentUserID,u);
            menuPresenter.showPrompt("Your new username is: " + u);
        }
        else{
            menuPresenter.showPrompt("Not a valid username!");
        }
    }

    /**
     * Changes users password
     * @author Priyanka
     */
    public void changePassword()
    {
        int currentUserID = main.getLoginController().getCurrentUserID();
        SignUpController sc = main.getSignUpController();
        menuPresenter.showPrompt("Enter your new password");
        String pw = scanner.nextLine();
        if(sc.isValidPass(pw))
        {
            main.getLoginController().setPassword(currentUserID,pw);
            menuPresenter.showPrompt("Your new password is: " + pw);
        }
        else{
            menuPresenter.showPrompt("Not a valid password!");
        }
    }

    /**
     * Changes users email
     * @author Priyanka
     */
    public void changeEmail()
    {
        int currentUserID = main.getLoginController().getCurrentUserID();
        SignUpController sc = main.getSignUpController();
        menuPresenter.showPrompt("Enter your new email");
        String e = scanner.nextLine();
        if(sc.isValidEmail(e))
        {
            main.getLoginController().setEmail(currentUserID,e);
            menuPresenter.showPrompt("Your new email is: " + e);
        }
        else{
            menuPresenter.showPrompt("Not a valid email!");
        }
    }

    /**
     * Displays attendee rules
     * @author Priyanka
     */
    public void viewRulesAttendee()
    {
        menuPresenter.showPrompt("1. Show up 10 minutes early to events.");
        menuPresenter.showPrompt("2. Bring a valid form of identification to an event organizer.");
        menuPresenter.showPrompt("3. Come prepared (pen, paper, book, etc.)");
        menuPresenter.showPrompt("4. Follow COVID-19 precautions: wear a mask and remain 2 meters apart from others.");
        menuPresenter.showPrompt("5. If you have tested positive for COVID within 2 weeks of an event, let the event organizer know and please stay home.");
        menuPresenter.showPrompt("6. Using the system: view events, filter for what you're looking for, and add friends.");
        menuPresenter.showPrompt("7. After attending an event, leave ratings for speaker(s), which will update their average rating. You can also view all ratings you have made and for who.");
        menuPresenter.showPrompt("8. Have fun ◉‿◉");
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
    }

    /**
     * Displays speaker rules
     * @author Priyanka
     */
    public void viewRulesSpeaker()
    {
        menuPresenter.showPrompt("1. Show up 30 minutes early to events for set-up.");
        menuPresenter.showPrompt("2. Ensure that you let an organizer know what technology requirements you need prior to the event.");
        menuPresenter.showPrompt("3. Bring a valid form of identification to an event organizer.");
        menuPresenter.showPrompt("4. Come prepared with personal presentation materials.");
        menuPresenter.showPrompt("5. Follow COVID-19 precautions: wear a mask and remain 2 meters apart from others.");
        menuPresenter.showPrompt("6. If you have tested positive for COVID within 2 weeks of an event, let the event organizer know and please stay home.");
        menuPresenter.showPrompt("7. Using the system: view events you're speaking at and add friends.");
        menuPresenter.showPrompt("8. View your average rating, and see if you can improve in any way.");
        menuPresenter.showPrompt("9. Have fun ◉‿◉");
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
    }

    /**
     * Displays attendees' given ratings
     * @author Priyanka
     */
    public void ViewRatingsAttendee()
    {
        int currentUserID = main.getLoginController().getCurrentUserID();
        AttendeeController am = main.getAttendeeController();
        HashMap<String, String> ratings = am.viewRatingsMade(currentUserID);
        if (ratings.isEmpty()) {
            menuPresenter.showPrompt("You have not rated any speakers.");
        } else {
            for (Map.Entry<String, String> rating : ratings.entrySet()) {
                menuPresenter.showPrompt(rating.getKey() + " : " + rating.getValue());
            }
        }
    }

    /**
     * Displays attendees' given ratings
     * @author Priyanka
     */
    public void ViewRatingsSpeaker()
    {
        int currentUserID = main.getLoginController().getCurrentUserID();
        SpeakerController sm = main.getSpeakerController();
        menuPresenter.showPrompt(sm.viewRatings(currentUserID));
    }

    /**
     * Displays attendees' friends
     * @author Priyanka
     */
    public void viewFriends()
    {
        AttendeeController am = main.getAttendeeController();
        int currentUserID = main.getLoginController().getCurrentUserID();
        HashMap<String, Integer> map = am.getFriendsDict(currentUserID);
        if(map.isEmpty())
        {
            menuPresenter.showPrompt("You have not added any users.");
        }
        else {
            for (Map.Entry<String, Integer> friend : map.entrySet()) {
                menuPresenter.showPrompt(friend.getKey() + " : " + friend.getValue());

            }
        }
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
    }
}

