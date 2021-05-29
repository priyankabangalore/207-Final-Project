/*
@layer: Presenter - Menu
 */


package Presenters;

import Presenters.PresenterHelperCommands.Command;

import java.util.ArrayList;
import java.util.Optional;

/**
 * A class designed to store a system of menus and commands.
 */
public class Menu extends Command {
    /*
     * ___Representation Invariants___
     * optionString.size() == options.size()
     */

    /** The title of this menu. */
    private final String title;

    /** The parent menu of this menu. */
     private final Menu parent;

    /** The String representations of each option within the Menu. */
    protected ArrayList<String> titles;

    /** The options this menu offers */
    protected ArrayList<Command> options;

    /** The header of this menu*/
    protected String header = "";

    /**
     * A smaller constructor that assumes the user will instantiate options later.
     * @param title the title of this menu.
     */
    public Menu(String title){
        super(title);
        this.title = title;
        this.parent = this;
        titles = new ArrayList<>();
        options = new ArrayList<>();
    }


    /**
     * A smaller constructor that assumes the user will instantiate options later.
     * @param title the title of this menu.
     * @param parent type Menu
     */
    public Menu(String title, Menu parent){
        super(title);
        this.title = title;
        this.parent = parent;
        titles = new ArrayList<>();
        options = new ArrayList<>();
    }

    /**
     * A smaller constructor that assumes the user will instantiate options later.
     * @param title the title of this menu.
     * @param parent type Menu
     */
    public Menu(String title, Menu parent, String header){
        super(title);
        this.title = title;
        this.parent = parent;
        titles = new ArrayList<>();
        options = new ArrayList<>();
        this.header = header;
    }

    /**
     * Instantiates a new Menu, takes parameters must obey representation invariant
     * @param optionStrings a list of string representation of options.
     * @param options a list of menu options.
     * @param parent type Menu
     * @param title title of menu
     */
    public Menu(String title, Menu parent, ArrayList<String> optionStrings, ArrayList<Command> options){
        super(title);
        if(optionStrings.size() != options.size()){
            throw new IllegalArgumentException("There must be an equal number of options and optionStrings");
        }
        this.title = title;
        this.parent = parent;
        this.titles = optionStrings;
        this.options = options;
    }

    /**
     * Gets all numeric options displayed as strings
     * @param shift: int
     * @return String of the option numbers
     */
    public String getOptionStrings(int shift){
        int numOptions = size();
        StringBuilder str = new StringBuilder();
        str.append(header);
        for(int i = numOptions + shift; i > shift; i --){
            str.append("\n" + "<").append(numOptions - i + 1).append(">").append(getOptionTitle(i - shift -1));
        }
        return str.toString();
    }


    /**
     * Gets the string representation of an option offered by this menu.
     * @param index The index of the command within this Menu.
     * @return Title of command at a certain index.
     */
    public String getOptionTitle(int index){
        return titles.get(index);
    }

    /**
     * Gets a menu option.
     * @param index index of option.
     * @return the option.
     */
    public Command getOption(int index){
        return options.get(index);
    }

    /**
     * Gets a list of menu options.
     * @return a list of options.
     */
    public ArrayList<Command> getOptions(){

        ArrayList<Command> commands = new ArrayList<Command>();
        for(int optionID = options.size() - 1; optionID >= 0; optionID--){
            commands.add(getOption(optionID));
        }
        return commands;
    }

    /**
     * Gets this menus' parent menu.
     * @return parent menu
     */
    public Menu getParent(){
        return parent;
    }

    /**
     * Gets the length of the menu
     * @return length of menu.
     */
    public int size(){
        return options.size();
    }

    /**
     * Adds an option to this menu
     * @param option the Command to be added.
     */
    public void addOption(Command option){
        titles.add(option.getTitle());
        options.add(option);
    }

    /**
     * Adds an Menu option to this menu
     * @param menu the Menu to be added.
     */
    public void addOption(Menu menu){
        titles.add(((Command) menu).getTitle());
        options.add(menu);
    }

    /**
     * Returns whether this is a Menu.
     * @return String "MenuPresenter".
     */
    @Override
    public String executionLocation() {
        return "MenuPresenter";
    }

    /**
     * Gets the title of this Menu.
     * @return this menu's title.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Sets the header of this menu to a new string
     * @param newHeader the new header
     */
    public void setHeader(String newHeader){
        this.header = newHeader;
    }

    /**
     * Executes a menu within this class.
     * @param target: type MenuPresenter
     * @return null optional
     */
    public Optional<Command> execute(MenuPresenter target) {
        target.setCurMenu(this);
        return Optional.empty();
    }
}
