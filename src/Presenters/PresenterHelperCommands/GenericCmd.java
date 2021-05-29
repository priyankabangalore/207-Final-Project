/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands;

/**
 * A generic command used a placeholder. Has no functionality.
 */
public class GenericCmd extends Command {

    /**
     * Constructor for GenericCmd (passes title of command)
     */
    public GenericCmd() {
        super("Generic");
    }

    /**
     * Gets location of execution of GenericCmd
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        throw new UnsupportedOperationException("Cannot execute a generic command");
    }

}
