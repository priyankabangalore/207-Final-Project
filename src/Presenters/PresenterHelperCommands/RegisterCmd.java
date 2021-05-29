/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands;

import Controllers.Main;

import java.util.Optional;

/**
 * A command that registers a new Attendee user.
 */
public class RegisterCmd extends Presenters.PresenterHelperCommands.Command {

    /**
     * Constructor for RegisterCmd (passes through title of command)
     */
    public RegisterCmd() {
        super("Register");
    }

    /**
     * Gets location of execution of RegisterCmd
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
    public Optional<Command> execute(Main main) {
        //does nothing when executed
        main.runSignUp();
        return Optional.empty();
    }
}
