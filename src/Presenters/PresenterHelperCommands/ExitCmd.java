/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands;

import Controllers.MenuAdapters.EventManager;

import java.util.Optional;

/**
 * A command that signals an exist from a set of menus.
 */
public class ExitCmd extends Presenters.PresenterHelperCommands.Command {

    /**
     * Constructor for ExitCmd (passes title of command)
     */
    public ExitCmd() {
        super("Exit");
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Anywhere";
    }

    /**
     * * Executes a command within this class.
     * @param builder the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(EventManager builder){
        return Optional.empty();
    }
}
