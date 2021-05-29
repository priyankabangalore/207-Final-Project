/*
@layer: Controller
@collaborators: UserManager, VenueManager, EventManager
 */

package Controllers;

import UseCases.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AdminUserController extends OrganizerController{
    final ConversationManager CM;
    final AdminUserManager AUM;


    /**
     * The AdminUser Constructor
     * @param AM Attendee Manager
     * @param SM Speaker Manager
     * @param OM Organizer Manager
     * @param UM User Manager
     * @param EM Event Manager
     * @param VM Venue Manager
     * @param RM Request Manager
     * @param DM Date Manager
     * @param AUM AdminUserManager
     * @param CM ConversationManager
     */
    public AdminUserController(AttendeeManager AM, SpeakerManager SM, OrganizerManager OM,
                               UserManager UM, EventManager EM, VenueManager VM, RequestManager RM, DateManager DM,
                               ConversationManager CM, AdminUserManager AUM) {
        super(AM, SM, OM, UM, EM, VM, RM, DM);
        this.CM = CM;
        this.AUM = AUM;
        if (AUM.getNumberOfAdmins() == 0) {
            AUM.createAdminUser("admin", "adminDefaultPW", UM, "admin@email.com");
        }
    }

    /**
     * Delete a conversation
     * @param conversationID the conversation to be deleted
     */
    public void deleteConversation(int conversationID){
        CM.deleteConversation(conversationID);
    }

    /**
     * Add a new day of conference
     * @author: Stephanie
     * @param day the new date to be added
     */
    public void newDay(int day){
        if (DM.NewDate(day)) {
            SM.updateEmptySchedule(day);
            VM.updateEmptySchedule(day);
        }
    }

    /**
     * Delete a day from the conference, can only be done if there are no events scheduled
     * @author: Stephanie
     * @return True if the day has been deleted, false if the day has not (depends on whether events have been scheduled)
     */
    public boolean deleteDay(){
        return DM.deleteDay();
    }

    /**
     * Gets a list of all set Dates as a Hashmap with the string name of the date and the integer version of the date
     * @author: Steph
     * @return See above for details
     */
    public LinkedHashMap<String, Integer> viewDaysDict(){
        return DM.getDatesDic();
    }

    /**
     * allow adminUser to view all conversation with its name
     * @return hashmap of all of the conversation
     */
    public HashMap<String, Integer> showAllConversation(){return CM.showAllConversation();}

    /**
     * ban a user
     * @param userID userID for user that will be baned
     */
    public void blockUser(int userID){UM.blockUser(userID);}

    /**
     * unban a user
     * @param userID userId of the User that will be unblocked
     */
    public void unblockUser(int userID){UM.unblockUser(userID);}


    /**
     * get a hashmap include blocked user with all username and userID
     * @return hashmap<UserName, UserID>
     */
    public HashMap<String, Integer> getBlockedUser(){
        return UM.getBlockedUser();
    }
    /**
     * get a hashmap include unblocked user with all username and userID
     * @return hashmap<UserName, UserID>
     */
    public HashMap<String, Integer> getUnblockedUser(){
        return UM.getUnblockedUser();
    }

    /**
     * Returns whether or not dates have been added to the system.
     * @return True - there are dates added, False - there are no dates added
     */
    public boolean areDates() {
        return DM.getDates().size() != 0;
    }

    /**
     * Validate that the chosen chosen date is validate for the month. Ex: user cannot choose Feb 29th
     * @param month the chosen date
     * @param date teh chosen month
     * @return True: if the date is valid for the month
     */
    public boolean validateDays(int month, int date) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            return date <= 31 && date > 0;
        } else if (month == 2){
            return date <= 28 && date > 0;
        } else {
            return date <= 30 && date > 0;
        }
    }

    /**
     * Turns a single digit into a double. Will add a 0 infront of single digits.
     * @param i the given digit
     * @return the double digit as a string
     */
    public String intToDoubleDigit(Integer i) {
        String s = i.toString();
        if (s.length() == 1) {
            return "0" + s;
        }
        return s;
    }

    /**
     * Converts the date integer into the string version of it
     * @param date the integer date
     * @return the string version of the integer date
     */
    public String datetoString(int date){
        return DM.dateToString(date);
    }

    /**
     * Creates a new day for the conference. Adds it onto the previous date
     * @return the int version of the date
     */
    public String nextDay(){
        Integer newDate = DM.nextDay();
        SM.updateEmptySchedule(newDate);
        VM.updateEmptySchedule(newDate);
        return datetoString(newDate);
    }
}