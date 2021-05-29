/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands;

import Controllers.Main;

import java.util.Optional;

/**
 * A command that logs out the current user.
 */
public class LogOutCmd extends Presenters.PresenterHelperCommands.Command {

    /**
     * Constructor for LogOutCmd (passes through title of command)
     */
    public LogOutCmd() {
        super("Log Out");
    }

    /**
     * Gets location of execution of LogOutCmd
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "Main";
    }

    /**
     * Executes a command within this class.
     * @param main the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(Main main){
        main.logOut();
        return Optional.empty();
    }
}
