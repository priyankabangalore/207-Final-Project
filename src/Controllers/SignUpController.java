/*
@layer: Controller
@collaborators: AttendeeManager, SpeakerManager, OrganizerManager, UserManager
 */

package Controllers;

import UseCases.*;

public class SignUpController {
    private final AttendeeManager am;
    private final OrganizerManager om;
    private final SpeakerManager sm;
    private final UserManager um;
    private final DateManager dm;

    /**
     * Constructor for SignUpController
     * @param am: AttendeeManager instance
     * @param sm: SpeakerManager instance
     * @param om: OrganizerManager instance
     * @param um: UserManager instance
     */
    public SignUpController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, UserManager um, DateManager dm) {
        this.am = am;
        this.sm = sm;
        this.um = um;
        this.om = om;
        this.dm = dm;
    }

    /**
     * Check if password is acceptable, greater than 8 characters
     * @author Priyanka
     * @param pass String of password
     * @return true iff password is greater than 8 characters
     */
    public boolean isValidPass(String pass) {
        return pass.length() > 8 && isValidPassCharacters(pass);
    }

    /**
     * Check if password is acceptable, only letters and numbers
     * @author Priyanka
     * @param pass String of password
     * @return true iff password contains on alphanumeric characters
     */
    public boolean isValidPassCharacters(String pass)
    {
        return pass.matches("[a-zA-Z0-9]*");
    }

    /**
     * Check that username has at least one String character
     * @author Priyanka
     * @param username String of username
     * @return true iff username is not blank
     */
    public boolean isValidUsername(String username) {
        //Username cant be blank
        return username.length() > 0;
    }

    /**
     * Check that email is valid
     * @author Priyanka
     * @param email String of email
     * @return true iff email is valid
     */
    public boolean isValidEmail(String email) {
        return um.validateEmail(email);
    }

    /**
     * Create and register a new User
     *
     * @param username username of the user
     * @param password password of the user
     * @param userType permission level of user
     * @param email email of user
     * @return Integer ID of user
     */
    public Integer registerUser(String username, String password, int userType, String email) {
        if (isValidPass(password) && isValidUsername(username)) {
            if (userType == 0) {
                return am.createAttendee(username, password, um, email);
            } else if (userType == 1) {
                return sm.createSpeaker(username, password, um, email, dm.getDatesDic().values());
            } else if (userType == 2) {
                return om.createOrganizer(username, password, um, email);
            }
        }
        return null;
    }
}


