/*
@layer: Presenter - Menu
 */

package Presenters;

import Controllers.Main;
import Controllers.MenuAdapters.SimplePresenterMethods;
import Presenters.PresenterHelperCommands.Command;
import Presenters.Menus.MenuTreeBuilders.MenuFactory;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

/**
 * The central UI class that delegates commands, displays the main menu systems, and stores the ComplexBuilder and
 * SimplePresenterMethods classes.
 */
public class MenuPresenter {
    /** The main menu of the UI */
    private Presenters.Menu mainMenu;

    /** The current menu being viewed by the user */
    private Presenters.Menu curMenu;

    /** The CommandBuilderUI used by this MenuPresenter.*/
    private final SimplePresenterMethods simplePresenterMethods;

    /** The ComplexBuilderUI used by this MenuPresenter*/
    private final DynamicMenuOperatorHub dynamicMenuOperatorHub;

    /** The main class*/
    private final Main main;


    /**
     * Constructor for MenuPresenter
     * @param mainMenu type Menu
     * @param main type Main
     */
    public MenuPresenter(Menu mainMenu, Main main){
        this.mainMenu = mainMenu;
        this.curMenu = mainMenu;
        this.main = main;
        this.simplePresenterMethods = new SimplePresenterMethods(this);
        this.dynamicMenuOperatorHub = new DynamicMenuOperatorHub(this);

    }

    /**
     * Sets a new current Menu
     * @param newMenu the menu to be set as the current menu
     */
    public void setCurMenu(Presenters.Menu newMenu){
        this.curMenu = newMenu;
    }

    /**
     * Goes back a menu
     */
    public void goBack(){
        this.curMenu = curMenu.getParent();
    }

    /**
     * Gets main menu
     */
    public Main getMain(){return this.main;}

    /**
     * Prints out a given menu's options alongside numbers representing a choice for each option, with the numbers
     * shifted upwards a given amount.
     * @param menu the menu to be printed.
     * @param shift the amount to shift the number of the option
     * @return the options within the menu
     */
    public ArrayList<Command> displayMenu(Presenters.Menu menu, int shift){
        System.out.println(menu.getOptionStrings(shift));
        return menu.getOptions();
    }

    /**
     * Prints out a given menu's options alongside numbers representing a choice for each option.
     * @param menu the menu to be printed.
     * @return the options within the menu
     */
    public ArrayList<Command> displayMenu(Presenters.Menu menu) {
        return displayMenu(menu, 0);
    }

    /**
     * Gets the scanner used by main.
     * @return Scanner of info
     */
    public Scanner getScanner(){return main.getScanner();}

    /**
     * Builds the menu system required for this user.
     * @param permissionLvl permission level of user
     */
    public void buildMenus(int permissionLvl){
        MenuFactory builder = new MenuFactory(main);
        this.mainMenu = builder.buildMenu(permissionLvl);
        this.curMenu = mainMenu;
    }

    /**
     * Shows a prompt to the user.
     * @param prompt the string to be printed
     */
    public void showPrompt(String prompt){
        System.out.println(prompt);
    }

    /**
     * Displays the menu currently being viewed by the user.
     * @return The options being presented.
     */
    public ArrayList<Command> displayCurMenu(){
        return displayMenu(curMenu);
    }

    /**
     * Runs attendee login
     * @return login
     */
    public boolean runLogin() {
        return simplePresenterMethods.runLogin();
    }

    /**
     * Runs an attendee sign up.
     * @return sign up
     */
    public boolean runSignUp() {return simplePresenterMethods.runSignUp(0);}

    /**
     * Attempts to handle a Command locally, if unable to, passes the command to another location.
     * @param command the Command to be handled
     * @return null iff handled, Command if Main is required to execute the Command
     */
    public Optional<Command> allocate(Command command){

        String location = command.executionLocation();
        switch (location) {
            case "MenuPresenter":
                assert this.getClass() == MenuPresenter.class;
                command.execute(this);
                return Optional.empty();
            case "Main":
                return Optional.of(command);
            case "Simple Presenter Methods":
                return simplePresenterMethods.execute(command);
            case "Dynamic Menu Operator Hub":
                return dynamicMenuOperatorHub.execute((command));
            default:
                throw new NoClassDefFoundError("Command specifies a location that does not exist:\""
                        + location + "\"");
        }
    }

    /**
     * Executes a command locally.
     * @param command command to be executed
     */
    public void execute(Command command){
        command.execute(this);
    }

    /**
     * Tests to see if the input given is valid for the current menu.
     * @param input input to be tested.
     * @return whether the input is valid.
     */
    public boolean validInput(String input){
        try {
            int cmdNum = Integer.parseInt(input);
            if(!(0 < cmdNum && cmdNum <= curMenu.size())){
                System.out.println("Invalid input. Input a number from 1 - " + curMenu.size());
                return false;
            }
            return true;
        }
        catch(NumberFormatException error){
            System.out.println("Invalid input. Input a number from 1 - " + curMenu.size());
            return false;
        }
    }


    /**
     * Tests to see if the input given is valid for a given menu.
     * @param input input to be tested.
     * @param menu the menu to which this input should be valid.
     * @param shift the amount of extra options added.
     * @return whether the input is valid.
     */
    private boolean validInput(String input, Presenters.Menu menu, int shift){
        try {
            int cmdNum = Integer.parseInt(input);
            if(!(0 < cmdNum && cmdNum <= menu.size() + shift)){
                System.out.println("Invalid input. Input a number from 1 - " + menu.size());
                return false;
            }
            return true;
        }
        catch(NumberFormatException error){
            System.out.println("Invalid input. Input a number from 1 - " + menu.size());
            return false;
        }
    }

    /**
     * Tests to see if the input given is valid for a given menu.
     * @param input input to be tested.
     * @param menu the menu to which this input should be valid.
     * @return whether the input is valid.
     */
    public boolean validInput(String input, Menu menu){
        try {
            int cmdNum = Integer.parseInt(input);
            if(!(0 < cmdNum && cmdNum <= menu.size())){
                System.out.println("Invalid input. Input a number from 1 - " + menu.size());
                return false;
            }
            return true;
        }
        catch(NumberFormatException error){
            System.out.println("Invalid input. Input a number from 1 - " + menu.size());
            return false;
        }
    }
}