/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands;

import Controllers.Main;
import Controllers.MenuAdapters.*;
import Exceptions.ImproperExecutionException;
import Presenters.MenuPresenter;

import java.util.Optional;

/**
 * A command to be parsed at another location.
 */
public abstract class Command{
    /** The title of the command. */
    private String title;

    /** If this command specifies an entity in its implementation, this will store that entities ID */
    protected int entityID;

    /**
     * Instantiates a command with a title.
     * @param title the title of this command
     */
    public Command(String title){this.title = title;}

    /**
     * Instantiates a command with a title and an entity ID
     * @param title the title of this command
     * @param entityID the entity referenced by this command
     */
    public Command(String title, int entityID){
        this.title = title;
        this.entityID = entityID;
    }

    /**
     * Specifies the class that should be passed to this command.
     * @return location where this should be executed.
     */
    public abstract String executionLocation();

    /**
     * Gets the title of this command.
     * @return this commands title.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Sets the title of this command.
     */
    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    /**
     * Executes a method within a given object.
     * A command must be designed with its own overloaded execute command that takes the target class.
     * @param target the object containing the target method.
     */
    public Optional<Command> execute(MenuPresenter target)  { return improperExecutionStringError(target); }
    public Optional<Command> execute(DynamicMenuOperatorHub target){
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(Controllers.MenuAdapters.ConversationViewer target){
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(Controllers.MenuAdapters.ConversationBuilder target){
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(Main target){
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(SimplePresenterMethods target){
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(Controllers.MenuAdapters.EventManager target){
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(Controllers.MenuAdapters.DynamicMenuOperator target){
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(Controllers.MenuAdapters.EventEnroller target){
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(AddNewFriendOperator target){
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(RequestMenuOperator target) {
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(DeleteConversationOperator target) {
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(BlockAndUnblockUserOperator target) {
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(FilterEventsOperator target) {
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(NewRoomOperator target){
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(VenueMenuOperator target){
        return improperExecutionStringError(target);
    }
    public Optional<Command> execute(ReviewMenuOperator target){
        return improperExecutionStringError(target);
    }
    // I know this is not the greatest nor most extensible implementation, looking in to how ot fix.

    /**
     * Crashes the program, allows for easy locating of improper command call
     * @param target the execution location
     * @return empty optional
     */
    public Optional<Command> improperExecutionStringError(Object target){
        new ImproperExecutionException(executionLocation(), target.toString()).printStackTrace();
        return Optional.empty();
    }
}
