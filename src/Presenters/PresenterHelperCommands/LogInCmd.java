/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands;

import Controllers.Main;

import java.util.Optional;

/**
 * A command that initiates the login process.
 */
public class LogInCmd extends Presenters.PresenterHelperCommands.Command {

    /**
     * Constructor for LogInCmd (passes through command title)
     */
    public LogInCmd() {
        super("Log In");
    }

    /**
     * Gets location of execution of LogInCmd
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
    //Does nothing when executed
    public Optional<Command> execute(Main main) {
        main.runLogin();
        return Optional.empty();
    }
}
