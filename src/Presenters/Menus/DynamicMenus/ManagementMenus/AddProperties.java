/*
@layer: Presenter - Menu
*/

package Presenters.Menus.DynamicMenus.ManagementMenus;

import Controllers.Main;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.FinaliseCmd;
import Presenters.PresenterHelperCommands.ManagementCommands.AddRemovePropertiesCommand;

/**
 * A Dynamic menu that allows the user to add options to their new room.
 */
public class AddProperties extends DynamicMenu {


    public AddProperties(Main main) {
        super("Add New Properties", main);
    }

    /**
     * builds a new AddRemovePropertyCommand.
     * @param title title of the new property
     * @param ID this does not matter
     * @return and AddRemoveProperty command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new AddRemovePropertiesCommand(title);
    }

    /**
     * Updates properties
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(main.getVenueController().getAllPropertiesDict());
        addOption(new FinaliseCmd("Continue"));
    }

    /**
     * Gets execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "NewRoomOperator";
    }
}
