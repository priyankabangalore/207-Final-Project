/*
@layer: Controller
 */


package Controllers.MenuAdapters;

import Controllers.Main;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

/**
 * A generic operator that can only run and return commands.
 */
public class GenericMenuOperator extends DynamicMenuOperator {

    /**
     * Constructor for GenericMenuOperator
     * @param main: type Main
     */
    public GenericMenuOperator(Main main) {
        super(main);
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return a command to be executed in another part of the program
     */
    @Override
    public Optional<Command> executeCmd(Command command){return command.execute(this);}
}
