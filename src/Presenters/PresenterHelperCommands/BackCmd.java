/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands;

import Presenters.MenuPresenter;

import java.util.Optional;

/**
 * A command that signals the menu to display the previous menu.
 */
public class BackCmd extends Presenters.PresenterHelperCommands.Command {

    /**
     * Constructor for BackCmd
     */
    public BackCmd(){
        super("Back");
    }

    /**
     * Gets the execution location of the command
     * @return String "MenuPresenter"
     */
    public String executionLocation(){
        return "MenuPresenter";
    }

    /**
     * Executes a command within this class.
     * @param target: type MenuPresenter
     * @return null optional.
     */
    @Override
    public Optional<Presenters.PresenterHelperCommands.Command> execute(MenuPresenter target) {
        target.goBack();
        return Optional.empty();
    }

    /**
     * Executes a command within this class.
     * @param obj: type Object
     * @return null optional.
     */
    public Optional<Command> execute(Object obj){
        System.out.println("Going Back One Menu");
        return Optional.empty();
    }
}
