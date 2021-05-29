/*
@layer: Controller
 */

package Controllers.MenuAdapters;

import Controllers.Main;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ExitCmd;
import Presenters.PresenterHelperCommands.GenericCmd;
import Presenters.Menu;
import Presenters.MenuPresenter;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

/**
 * A type of class that allows for the displaying of dynamic Menus, and adapting the commands in them into cammands that
 * can be used in the Controllers.
 */
public abstract class DynamicMenuOperator {

    protected Main main;
    protected final Scanner scanner;
    protected final MenuPresenter menuPresenter;

    /**
     * Constructor for DynamicMenuOperator
     * @param main: type Main
     */
    public DynamicMenuOperator(Main main){
        this.main = main;
        this.menuPresenter = main.getMenuPresenter();
        this.scanner = menuPresenter.getScanner();
    }

    /**
     * Gets a valid command from the user.
     * @param menu the menu containing the options.
     * @return Command
     */
    public Command getValidCommand(Menu menu){
        boolean valid = false;
        ArrayList<Command> options = new ArrayList<>();
        String input = "";

        while(!valid){
            options = menuPresenter.displayMenu(menu);
            input = scanner.nextLine();
            if(menuPresenter.validInput(input, menu)) {
                valid = true;
            }
        }

        return options.get(Integer.parseInt(input) - 1);
    }

    /**
     * Runs a given <menu> until a command with a title == <condition> is called.
     * @param menu the menu to be shown
     * @param condition the name of the command that breaks the run
     */
    public Optional<Command> run(Menu menu, String condition){
        Command command = new GenericCmd();
        while (!command.getTitle().equals(condition)) {
            command = getValidCommand(menu);
            if(command.getTitle().equals(condition)){
                break;
            }
            executeCmd(command);
        }
        return Optional.of(command);
    }

    /**
     * Runs a given <menu> until a command with a title == <condition> is called.
     * @param menu the menu to be shown
     * @param conditions the names of the commands that break the run
     */
    public Optional<Command> run(Menu menu, ArrayList<String> conditions){
        Command command = new GenericCmd();
        boolean equalsCondition = false;
        while (!equalsCondition) {
            command = getValidCommand(menu);
            for (String condition : conditions){
                if (condition.equals(command.getTitle())){
                    equalsCondition = true;
                }
            }
            if(equalsCondition){
                break;
            }
            executeCmd(command);
        }
        return Optional.of(command);
    }

    public abstract Optional<Command> executeCmd(Command command);

    /**
     * Runs and updates a given <menu> until a command with a title == <condition> is called.
     * @param menu the menu to be shown
     * @param condition the name of the command that breaks the run
     */
    public Optional<Command> dynamicRun(DynamicMenu menu, String condition){
        Command command = new GenericCmd();
        while (!command.getTitle().equals(condition)) {
            menu.update();
            command = getValidCommand(menu);
            if(command.getTitle().equals(condition)){
                break;
            }
            executeCmd(command);
        }
        return Optional.of(command);
    }

    /**
     * Runs and updates a given <menu> until a command with a title == <condition> is called. Breaks the run if the
     * command received from execute is an exit command.
     * @param menu the menu to be shown
     * @param condition the name of the command that breaks the run
     */
    public Optional<Command> dynamicRunWithExit(DynamicMenu menu, String condition){
        Command command = new GenericCmd();
        while (!command.getTitle().equals(condition) && !command.getTitle().equals("Exit")) {
            menu.update();
            command = getValidCommand(menu);
            if(command.getTitle().equals(condition) || command.getTitle().equals("Exit")){
                break;
            }
            Optional<Command> newCmd = executeCmd(command);
            if (newCmd.isPresent()){
                if (newCmd.get().getTitle().equals("Exit")){
                    command = new ExitCmd();
                    break;
                } else if (newCmd.get().getTitle().equals("Finalise / Exit")) {
                    newCmd = executeCmd(command);
                    return newCmd;
                }
            }
        }
        return Optional.of(command);
    }

    /**
     * Continually runs the menu until exit command is reached (either through completion or abandoning
     * construction) allows exit command to cascade through recursive calls to this command, exiting the event
     * builder submenu entirely.
     * @param menu the DynamicMenu to be run
     * @return an Exit command, iff found
     */
    public Optional<Command> runUntilBackOrExit(DynamicMenu menu){
        Optional<Command> command;
        menu.update();
        command = dynamicRunWithExit(menu, "Back");
        if (command.isPresent() && command.get().getTitle().equals("Exit")){
            return command;
        } else if (command.isPresent() && command.get().getTitle().equals("Finalise / Exit")) {
            return executeCmd(command.get());
        }
        return Optional.empty();
    }

    /**
     * Runs and updates a given <menu> until a command with a title == <condition> is called.
     * @param menu the menu to be shown
     * @param conditions the names of the commands that break the run
     */
    public Optional<Command> dynamicRun(DynamicMenu menu, ArrayList<String> conditions){
        Command command;
        while (true) {
            menu.update();
            command = getValidCommand(menu);

            if (conditions.contains(command.getTitle())) {
                return Optional.of(command);
            }

            executeCmd(command);
        }
    }

    /**
     * Adds speaker to entitiesList iff entity not in entitiesList, else removes entity.
     * @param entityID: ID of the entity
     */
    public void addRemove(Integer entityID, ArrayList<Integer> entitiesList) {
        if (entitiesList.contains(entityID)){
            entitiesList.remove(entityID);
            showPrompt("Removed \n");
        }
        else {
            entitiesList.add(entityID);
            showPrompt("Added \n");
        }
    }

    /**
     * Adds speaker to entitiesList iff entity not in entitiesList, else removes entity.
     * @param entityName name of the entity
     */
    public void addRemove(String entityName, ArrayList<String> entitiesList) {
        if (entitiesList.contains(entityName)){
            entitiesList.remove(entityName);
            showPrompt(entityName + " removed");
        }
        else {
            entitiesList.add(entityName);
            showPrompt(entityName + " added");
        }
    }

    /**
     * Shows a prompt to the user.
     */
    protected void showPrompt(String prompt){
        menuPresenter.showPrompt(prompt);
    }
}
