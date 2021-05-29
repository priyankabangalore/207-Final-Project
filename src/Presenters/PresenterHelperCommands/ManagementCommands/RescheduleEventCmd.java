/*
@layer: Presenter - Command
*/

package Presenters.PresenterHelperCommands.ManagementCommands;

import Controllers.MenuAdapters.EventManager;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ExitCmd;

import java.util.Optional;

public class RescheduleEventCmd extends Command {

    int eventID;
    int startTime;
    int endTime;
    int day;

    /**
     * Constructor for RescheduleEventCmd
     * @param title title of command
     * @param eventID ID of event
     * @param startTime start time of event
     * @param endTime end time of event
     */
    public RescheduleEventCmd(String title, int eventID, int startTime, int endTime, int day) {
        super(title);
        this.eventID = eventID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Event Manager";
    }

    /**
     * Executes a command within this class.
     * @param eventManager the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(EventManager eventManager){
        eventManager.rescheduleEvent(eventID, startTime, endTime, day);
        return Optional.of(new ExitCmd());
    }
}
