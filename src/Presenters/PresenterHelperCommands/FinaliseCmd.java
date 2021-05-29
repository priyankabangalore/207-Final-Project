/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands;

import Controllers.MenuAdapters.EventManager;
import Controllers.MenuAdapters.NewRoomOperator;

import java.util.Optional;

/**
 * A command that signifies the user has completed some process.
 */
public class FinaliseCmd extends Presenters.PresenterHelperCommands.Command {


    /**
     * Constructor for ExitCmd
     */
    public FinaliseCmd(String title){
        super(title);
    }

    /**
     * Constructor for FinaliseCmd (passes title of command)
     */
    public FinaliseCmd() {
        super("Finalise / Exit");
    }

    /**
     * Gets execution location of command
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return null;
    }

    /**
     * Executes a command within this class.
     * @param builder the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(EventManager builder){
        builder.setComplete(true);
        return Optional.of(new ExitCmd());
    }

    @Override
    public Optional<Command> execute(NewRoomOperator builder){
        return Optional.of(new FinaliseCmd("Continue"));
    }
}
