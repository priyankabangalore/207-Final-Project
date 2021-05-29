/*
@layer: Controller
@collaborators: EventManager, UserManager, AttendeeManager, ConversationManager, OrganizerManager
                SpeakerManager, VenueManager, ConversationFile, EventFile, RoomFile, UserFile, PanelEvent File,
                PartyEvent File, LoginController, ConversationController, SpeakerController, OrganizerController,
                AttendeeController, EventRegisterController, SignUpController, VenueController, rest of the controllers,
                and gateways
 */

package Controllers;


import Gateways.*;
import Presenters.PresenterHelperCommands.Command;
import Presenters.Menu;
import Presenters.MenuPresenter;
import Presenters.Menus.LoginMenu;
import UseCases.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Main {

    // Gateways!
    private final ConversationFile cf = new ConversationFile();
    private final EventFile ef = new EventFile();
    private final RoomFile rf = new RoomFile();
    private final UserFile uf = new UserFile();
    private final MessageFile mf = new MessageFile();
    private final RequestFile reqf = new RequestFile();
    private final DateFile df = new DateFile();

    //Managers!
    private final RequestManager requestManager = new RequestManager(reqf.load(), reqf.loadID());
    private final MessagesManager messagesManager = new MessagesManager(mf.load(), mf.loadID(), mf.load());
    private final VenueManager venueManager = new VenueManager(rf.load(), rf.loadID());
    private final DateManager dateManager = new DateManager(df.load());
    private final EventManager eventManager = new EventManager(ef.loadTalks(), ef.loadPanels(), ef.loadParties(), ef.loadID());
    private final UserManager userManager = new UserManager(uf.loadUsers(), uf.loadUserIDs());
    private final AttendeeManager attendeeManager = new AttendeeManager(uf.loadAttendees());
    private final ConversationManager conversationManager = new ConversationManager(cf.load());
    private final OrganizerManager organizerManager = new OrganizerManager(uf.loadOrganizers());
    private final SpeakerManager speakerManager = new SpeakerManager(uf.loadSpeakers(), uf.loadSpeakerIDs());
    private final AdminUserManager adminUserManager = new AdminUserManager(uf.loadAdminUser());
    // Controllers!
    private final RequestController requestController = new RequestController(attendeeManager, requestManager, speakerManager, userManager);
    private final LoginController loginController = new LoginController(userManager);
    private final ConversationController conversationController = new ConversationController(eventManager, conversationManager, userManager, venueManager, messagesManager);
    private final SpeakerController speakerController = new SpeakerController(attendeeManager, speakerManager, organizerManager, userManager, eventManager, venueManager, requestManager, dateManager);
    private final OrganizerController organizerController = new OrganizerController(attendeeManager, speakerManager, organizerManager, userManager, eventManager, venueManager, requestManager, dateManager);
    private final AttendeeController attendeeController = new AttendeeController(attendeeManager, speakerManager, organizerManager, userManager, eventManager, venueManager, requestManager, dateManager);
    private final EventRegisterController eventRegisterController = new EventRegisterController(eventManager, venueManager, dateManager);
    private final SignUpController signUpController = new SignUpController(attendeeManager, organizerManager, speakerManager, userManager, dateManager);
    private final VenueController venueController = new VenueController(venueManager, eventManager, dateManager);
    private final AdminUserController adminUserController = new AdminUserController(attendeeManager, speakerManager, organizerManager, userManager, eventManager, venueManager, requestManager, dateManager, conversationManager, adminUserManager);

    // Presenters
    private final MenuPresenter menuPresenter;

    private boolean loggedIn = false;
    //Figured this was ok, as this static variable can only be called by one method.
    private static boolean operating = true;

    // Logger2
    public static Handler handler = null;
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final boolean defaultHandlersRemoved = false;

    // Scanner
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Main program.
     */
    public Main(){
        try {
            handler = new FileHandler("logs");
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.menuPresenter = new MenuPresenter(new Menu("Empty Menu"), this);
    }

    /**
     * Executes a given command.
     * @param command The command to be executed.
     */
    public void execute(Command command){
        command.execute(this);
    }

    /**
     * Removing the default console handlers for the program. Got this from Péter Török on github
     */
    public EventRegisterController getEventRegisterController(){return eventRegisterController;}
    public LoginController getLoginController(){return loginController;}
    public AttendeeController getAttendeeController(){return attendeeController;}
    public SpeakerController getSpeakerController(){return speakerController;}
    public OrganizerController getOrganizerController(){return organizerController;}
    public SignUpController getSignUpController() {return signUpController;}
    public ConversationController getConversationController(){return conversationController;}
    public VenueController getVenueController(){return venueController;}
    public RequestController getRequestController(){return requestController;}
    public AdminUserController getAdminUserController(){return adminUserController;}

    /**
     * gets the MenuPresenter to print the first menu on the screen
     * @return the screen being printed
     */
    public MenuPresenter getMenuPresenter(){return menuPresenter;}

    /**
     * Logs user out
     */
    public void logOut(){
        loginController.logOut();
        loggedIn = false;
    }

    /**
     * Logs users into the program
     */
    public void runLogin(){
        if (menuPresenter.runLogin()){
            loggedIn = true;
            int permissionLvl = loginController.getPermissionLvl();
            menuPresenter.buildMenus(permissionLvl);
        }
    }

    /**
     * Run the Sign Up menu for logging back into accounts
     */
    public void runSignUp(){
        if (menuPresenter.runSignUp()) {
            // since the runSignUp method automatically signs the user in after
            // registering them,
            loggedIn = true;
            int permissionLvl = loginController.getPermissionLvl();
            menuPresenter.buildMenus(permissionLvl);
        }
    }

    /**
     * While a user is logged in and the program is supposed to be running, the Menu Presenter runs, iterating through the menu
     * trees.
     */
    public void runUI(){
        Optional<Command> cmd;
        while(loggedIn && operating){
            cmd = getCmdFromMenuPresenter();

            cmd.ifPresent(this::execute);
        }
    }

    /**
     * Gets the menuPresenter to display its menu, and return a command issued by the user. Returns an empty command iff
     * the users input is invalid or if the command doesn't require main for execution.
     * @return optional
     */
    public Optional<Command> getCmdFromMenuPresenter(){
        ArrayList<Command> options = menuPresenter.displayCurMenu();
        String input = scanner.nextLine();
        if(!menuPresenter.validInput(input)) {
            return Optional.empty();
        }
        Command command = options.get(Integer.parseInt(input) - 1);
        return menuPresenter.allocate(command);
    }

    /**
     * Runs the login menu presented to the user upon first opening the program.
     */
    public void runLoginMenu(){
        menuPresenter.setCurMenu(new LoginMenu());
        Optional<Command> cmd;
        while(!loggedIn && operating) {
            cmd = getCmdFromMenuPresenter();
            cmd.ifPresent(this::execute);
        }
    }

    /**
     * Shuts down the program, invalidating all main method while loops.
     */
    public void shutdown(){
        operating = false;
        System.out.println("Shutting Down (⋟﹏⋞)");
    }

    /**
     * Save all entities to file for program shutdown
     * Should **only** be called when shutting down
     */
    private void saveEverything() {
        cf.save(conversationManager.getConversations());

        df.save(dateManager.getDates());

        reqf.save(requestManager.getRequestList());
        reqf.saveID(requestManager.getRequestIDs());

        ef.saveTalks(eventManager.getTalkEvents());
        ef.savePanels(eventManager.getPanelEvents());
        ef.saveParties(eventManager.getPartyEvents());
        ef.saveID(eventManager.getEventIds());

        rf.save(venueManager.getRooms());
        rf.saveID(venueManager.getIds());

        mf.save(messagesManager.getMessages());
        mf.saveID(messagesManager.getMessageIDs());

        uf.saveAttendees(attendeeManager.getAttendeeObjects());
        uf.saveSpeakers(speakerManager.getSpeakerObjects());
        uf.saveSpeakerIDs(speakerManager.getIDs());
        uf.saveOrganizers(organizerManager.getOrganizerObjects());
        uf.saveUserIDs(userManager.getUserIDs());
        uf.saveAdminUser(adminUserManager.getAdminUserObject());

    }

    /**
     * Gets the current scanner.
     * @return Scanner of info
     */
    public Scanner getScanner(){return scanner;}

    /**
     * The main method
     * @param args: collection of Strings
     */
    public static void main(String[] args){
        Main main = new Main();
        while(operating){
            main.runLoginMenu();
            main.runUI();
        }
        main.saveEverything();
    }
}
