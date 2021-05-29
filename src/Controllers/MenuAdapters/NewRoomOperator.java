/*
@layer: Controller
 */

package Controllers.MenuAdapters;

import Controllers.Main;
import Presenters.Menus.DynamicMenus.ManagementMenus.AddProperties;
import Presenters.PresenterHelperCommands.Command;

import java.util.ArrayList;
import java.util.Optional;

public class NewRoomOperator extends DynamicMenuOperator {

    private final ArrayList<String> properties = new ArrayList<>();

    /**
     * Constructor for DynamicMenuOperator
     * @param main : type Main
     */
    public NewRoomOperator(Main main) {
        super(main);
    }

    /**
     * Creates a new room.
     */
    public void newRoom(){
        String roomName = (new SimplePresenterMethods(main.getMenuPresenter())).newRoom();
        ArrayList<String> conditions = new ArrayList<>();
        menuPresenter.showPrompt("Please choose room properties");
        conditions.add("Back");
        conditions.add("Continue");
        Optional<Command> cmd = dynamicRun(new AddProperties(main),conditions);
        if (cmd.isPresent() && cmd.get().getTitle().equals("Continue")) {

            main.getVenueController().newRoom(roomName, properties);
            }
    }

    /**
     * Adds property to properties iff property not in properties, else removes properties.
     * @param property name of the property
     */
    public void addRemoveProperty(String property) {
        addRemove(property, properties);
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return a command to be executed in another part of the program
     */
    @Override
    public Optional<Command> executeCmd(Command command){return command.execute(this);}
}
