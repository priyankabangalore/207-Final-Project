/*
@layer: Controller
 */

package Controllers.MenuAdapters;

import Controllers.Main;
import Controllers.SpeakerController;
import Controllers.VenueController;
import Presenters.Menu;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.Menus.DynamicMenus.ManagementMenus.DeleteEventMenu;
import Presenters.Menus.DynamicMenus.ManagementMenus.RescheduleEventMenu;
import Presenters.Menus.DynamicMenus.ManagementMenus.SelectProperties;
import Presenters.PresenterHelperCommands.BackCmd;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ManagementCommands.NewEventCmd;

import java.util.ArrayList;
import java.util.Optional;

/**
 * A type of operator that operates a set of dynamic menus that allow the user to create and delete new events.
 */
public class EventManager extends DynamicMenuOperator {

    /**
     * The attributes of the event to be instantiated.
     */
    private int startTime;
    private int endTime;
    private final ArrayList<Integer> speakerIDs;
    private final ArrayList<Integer> attendeeIDs;
    private int roomID;
    private final DynamicMenu firstMenu;
    private boolean complete = false;
    private final ArrayList<String> properties;
    private int capacity;

    public EventManager(Main main) {
        super(main);
        this.firstMenu = new SelectProperties("Select Properties", main);
        attendeeIDs = new ArrayList<>();
        speakerIDs = new ArrayList<>();
        properties = new ArrayList<>();

        // Select properties --> Set Size --> Select room --> Select Speaker --> Select Start Time --> Select End Time
        // --> Set Name --> Add description
    }

    /**
     * Opens the event management menu.
     * @return empty optional.
     */
    public Optional<Command> openEventManagement(){
        Menu eventOptions = new Menu("Select an option");
        eventOptions.addOption(new BackCmd());
        eventOptions.addOption(new DeleteEventMenu(main));
        eventOptions.addOption(new RescheduleEventMenu(main));
        eventOptions.addOption(new NewEventCmd());
        run(eventOptions, "Back");
        return Optional.empty();
    }

    /**
     * Starts the event building process.
     * @return empty optional
     */
    public Optional<Command> getBuildEventCommand() {
        if (!(main.getOrganizerController().getRoomSchedules().size() >= 1 &&
                main.getAdminUserController().areDates())){
            showPrompt("Create Rooms and in order to make a new event. \nAlso make sure that the admin has" +
                    " selected a start date for your conference.");
            return Optional.empty();
        }
        Optional<Command> cmd = setProperties();
//        if (cmd.isPresent()) {
//            if (cmd.get().getTitle().equals("Back")) {
//                return Optional.empty();
//            }
//            ArrayList<String> conditions = new ArrayList<>();
//            conditions.add("Back");
//            conditions.add("Continue");
//            dynamicRun((DynamicMenu) cmd.get(), conditions);
//        } else {
//            return Optional.empty();
//        }
        if (complete) {
            showPrompt("Enter a name for the event");
            String name = scanner.nextLine();
            showPrompt("Enter a description of the event");
            String description = scanner.nextLine();
            while(!main.getEventRegisterController().validateDescription(description)) {
                showPrompt("Invalid description.");
                showPrompt("Description cannot be blank, and must be less than 250 characters.");
                showPrompt("Enter a description of the event");
                description = scanner.nextLine();
            }
            SpeakerController sc = main.getSpeakerController();
            VenueController vc = main.getVenueController();
            String fullDate = Integer.toString(endTime);
            if (fullDate.length() == 7) {
                fullDate = "0" + fullDate;
            }
            int day = Integer.parseInt(fullDate.substring(0, 4));
            int start = Integer.parseInt(fullDate.substring(4,6));
            int end = Integer.parseInt(fullDate.substring(6,8));
            main.getEventRegisterController().chooseEventToMake(name, roomID, start, end, speakerIDs,
                     attendeeIDs, sc, vc, properties, capacity, description, day);
        }
        return Optional.empty();

    }

    //Getters & setters

    /**
     * Sets event start time
     * @param time: type Main
     */
    public void setStartTime(int time){this.startTime = time;}

    /**
     * Sets event end time
     * @param time: type Main
     */
    public void setEndTime(int time){this.endTime = time;}

    /**
     * Prompts the user to set the capacity of the event
     * @return the capacity of the event
     */
    public int userSetCapacity() {
        String input;
        while (true) {
            showPrompt("Enter Desired Capacity for Event");
            input = scanner.nextLine();
            if (input.matches("^\\d+$")) {
                int cap = Integer.parseInt(input);
                if (cap > 0){
                    this.capacity = cap;
                    showPrompt("Capacity set to " + input);
                    break;
                }
            }
            showPrompt("Invalid input - Enter a positive number");
        }

        return Integer.parseInt(input);
    }

    public Optional<Command> setProperties() {
//        ArrayList<String> conditions = new ArrayList<>();
//        conditions.add("Back");
//        conditions.add("Continue");
        return recurssiveRun(Optional.of(firstMenu));
    }

    public Optional<Command> recurssiveRun(Optional<Command> cmd) {
        if (cmd.isPresent()) {
            if (cmd.get().getTitle().equals("Back") || cmd.get().getTitle().equals("Finalise / Exit") ) {
                return Optional.empty();
            }
            if (!cmd.get().getTitle().equals("Continue")) {
                return executeCmd(cmd.get());
            }
            ArrayList<String> conditions = new ArrayList<>();
            conditions.add("Back");
            conditions.add("Continue");
            return recurssiveRun(dynamicRun((DynamicMenu) cmd.get(), conditions));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Adds speaker to speakers iff speaker not in speakers, else removes speaker.
     * @param speakerID: ID of the speaker
     */
    public void addRemoveSpeaker(int speakerID) {
        addRemove(speakerID, speakerIDs);
    }

    /**
     * Adds property to properties iff property not in properties, else removes properties.
     * @param property name of the property
     */
    public void addRemoveProperty(String property) {
        addRemove(property, properties);
    }

    /**
     * Adds attendee to event
     * @param attendeeID: attendee's ID
     */
    public void addAttendee(int attendeeID){
        if (attendeeIDs.contains(attendeeID)) {
            showPrompt("User Already Added");
        }
        else {
            showPrompt("User Added");
            this.attendeeIDs.add(attendeeID);
        }
    }

    /**
     * Sets event room
     * @param roomID: ID of the room
     */
    public void setRoom(int roomID){this.roomID = roomID;}

    /**
     * Checks event set-up
     * @param isComplete: true iff event set-up is complete
     */
    public void setComplete(boolean isComplete){this.complete = isComplete;}

    /**
     * Gets event end time
     * @author Priyanka
     * @return end time of event
     */
    public int getEndTime(){return endTime;}

    /**
     * Gets the current properties
     * @return properties
     */
    public ArrayList<String> getProperties(){
        return properties;
    }

    /**
     * Gets panel speakers
     * @author Priyanka
     * @return speakers of the panel
     */
    public ArrayList<Integer> getSpeakers(){return speakerIDs;}

    /**
     * Gets event room
     * @return room of event
     */
    public int getRoom(){return roomID;}

    /**
     * Deletes an event.
     * @param eventID the event to be deleted.
     */
    public void deleteEvent(int eventID){
        main.getOrganizerController().cancelEvent(eventID);
    }

    /**
     * Reschedules an event.
     * @param endTime end time
     * @param day day
     * @param startTime start time
     * @param eventID the event to be deleted.
     *
     */
    public void rescheduleEvent(int eventID, int startTime, int endTime, int day){
        main.getOrganizerController().rescheduleEvent(eventID, startTime, endTime, day, roomID);
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return a command to be executed in another part of the program
     */
    @Override
    public Optional<Command> executeCmd(Command command){return command.execute(this);}
}
