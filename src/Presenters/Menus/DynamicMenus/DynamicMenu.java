/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus;

import Controllers.Main;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.BackCmd;
import Presenters.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * A menu with variable options, dependent on the current state of the use cases.
 */
public abstract class DynamicMenu extends Menu {
    /*
     * ___Representation Invariants___
     * optionString.size() == options.size()
     */

    protected Main main = null;

    /**
     * A smaller constructor that assumes the user will instantiate options later.
     * @param title the title of this menu.
     */
    public DynamicMenu(String title){
        super(title);
        header = "";
    }

    /**
     * A smaller constructor that assumes the user will instantiate options later.
     * @param title the title of this menu.
     * @param main the main class
     */
    public DynamicMenu(String title, Main main){
        super(title);
        header = "";
        this.main = main;
    }

    /**
     * A smaller constructor that assumes the user will instantiate options later.
     * @param title the title of this menu.
     * @param main the main class
     */
    public DynamicMenu(String title, Main main, String header){
        super(title);
        this.main = main;
        this.header = header;
    }

    /**
     * A smaller constructor that assumes the user will instantiate options later.
     * @param title the title of this menu.
     */
    public DynamicMenu(String title, Menu parent){
        super(title, parent, "");
    }

    /**
     * Instantiates a new Menu, takes parameters must obey representation invariant
     * @param optionStrings a list of string representation of options.
     * @param options a list of menu options.
     */
    public DynamicMenu(String title, Menu parent, ArrayList<String> optionStrings, ArrayList<Command> options){
        super(title, parent, optionStrings, options);
        if(optionStrings.size() != options.size()){
            throw new IllegalArgumentException("There must be an equal number of options and optionStrings");
        }
    }

    /**
     * Constructs a set of commands, determined by buildCommand, from a hashmap
     * @param hashMap the hashmap containing the attributes of the new command
     */
    public void buildOptionsFromHashMap(HashMap<String, Integer> hashMap){
        Set<String> titles = hashMap.keySet();

        clearOptions();

        for(String title: titles){
            addOption(buildCommand(title, hashMap.get(title)));
        }
        addOption(new BackCmd());
    }

    /** Builds a command, meant to be overwritten with a unique command */
    public abstract Command buildCommand(String title, int ID);

    /**
     * Clears the options within this menu.
     */
    public void clearOptions(){
        titles.clear();
        options.clear();
    }

    /**
     * Updates this class' options.
     */
    public abstract void update();

    /**
     * May have a non-MenuPresenter execution location.
     * @return the location in which this should be executed.
     */
    @Override
    public abstract String executionLocation();
}
