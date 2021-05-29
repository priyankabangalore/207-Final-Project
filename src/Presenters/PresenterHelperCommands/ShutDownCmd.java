/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands;

import Controllers.Main;

import java.util.Optional;

/**
 * A command that begins the shutdown process.
 */
public class ShutDownCmd extends Presenters.PresenterHelperCommands.Command {

    /**
     * Constructor for ShutDownCmd (passes through title of command)
     */
    public ShutDownCmd() {
        super("Shut Down");
    }

    /**
     * Gets location of execution of ShutDownCmd
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
        main.shutdown();
        return Optional.empty();
    }
}
