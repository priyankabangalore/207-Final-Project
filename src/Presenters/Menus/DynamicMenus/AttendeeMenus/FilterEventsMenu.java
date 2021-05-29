package Presenters.Menus.DynamicMenus.AttendeeMenus;

import Controllers.Main;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.Menu;
import Presenters.PresenterHelperCommands.BackCmd;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.FilterStrats.DateFilterStrat;
import Presenters.PresenterHelperCommands.FilterStrats.ShowEnrollableEvents;
import Presenters.PresenterHelperCommands.FilterStrats.SpeakerFilterStrat;
import Presenters.PresenterHelperCommands.FilterStrats.TimeFilterStrat;

import java.util.Optional;

public class FilterEventsMenu extends Menu {

    /**
     * Constructor for FilterEventsMenu
     * @param main type Main
     * @param title title of menu
     */
    public FilterEventsMenu(String title, Main main) {
        super(title);
        addOption(new SpeakerFilterStrat(main));
        addOption(new DateFilterStrat(main));
        addOption(new TimeFilterStrat(main));
        addOption(new ShowEnrollableEvents(main));
        addOption(new BackCmd());
    }

    /**
     * Gets execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "Dynamic Menu Operator Hub";
    }

    /**
     * Executes a menu within this class.
     * @param target the menu to be executed.
     * @return optional null
     */
    @Override
    public Optional<Command> execute(DynamicMenuOperatorHub target){
        target.runFilterEventsMenu(this);
        return Optional.empty();
    }
}
