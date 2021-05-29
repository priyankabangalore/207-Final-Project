package Controllers.MenuAdapters;

import Controllers.AdminUserController;
import Controllers.Main;
import Presenters.Menu;
import Presenters.PresenterHelperCommands.BackCmd;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ManagementCommands.AddDayToVenue;
import Presenters.PresenterHelperCommands.ManagementCommands.SelectStartDayCmd;
import Presenters.PresenterHelperCommands.ManagementCommands.ViewCurrentDays;

import java.util.LinkedHashMap;
import java.util.Optional;

public class VenueMenuOperator extends DynamicMenuOperator {

    /**
     * Constructor for DynamicMenuOperator
     *
     * @param main : type Main
     */
    public VenueMenuOperator(Main main) {
        super(main);
    }

    /**
     * Allows user to create the first day of the conference
     */
    public void selectStartDay(){
        AdminUserController AUC = main.getAdminUserController();
        menuPresenter.showPrompt("Enter the first date of the conference: ");
        boolean validDate = false;
        while (!validDate) {
            int month = selectMonth();
            int day;
            try {
                day = selectDay(month);
                String digit = AUC.intToDoubleDigit(month) + AUC.intToDoubleDigit(day);
                int date = Integer.parseInt(digit);
                AUC.newDay(date);
                menuPresenter.showPrompt("Date " + AUC.datetoString(date) + " was added!");
                validDate = true;
            } catch (NumberFormatException e) {
                menuPresenter.showPrompt("Invalid input.");
            }
        }
    }

    /**
     * Allows the user to add a new day to the conference (will add it to the next day after the first)
     */
    public void addNewDay(){
        AdminUserController AUC = main.getAdminUserController();
        String next = AUC.nextDay();
        menuPresenter.showPrompt(next +" was created!");
        menuPresenter.showPrompt("Your current dates are:");
        viewDays();
    }

//    /**
//     * Allows user to delete the last day of the conference, assuming there are no events scheduled on it
//     */
//    public void removeDay(){
//        AdminUserController AUC = main.getAdminUserController();
//
//        AUC.deleteDay();
//        menuPresenter.showPrompt("Last day of conference was deleted.");
//        menuPresenter.showPrompt("Press Enter to continue.");
//        scanner.nextLine();
//
//    }

    /**
     * Allows the user to choose the month of the first day of the conference
     * @return A int with the selected month
     */
    public int selectMonth(){
        menuPresenter.showPrompt("Choose Month:");
        menuPresenter.showPrompt("enter Month from 1-12 (ex: January = 1, February = 2, etc)");
        while (true) {
            try {
                String input = scanner.nextLine();
                int month = Integer.parseInt(input);
                if (month > 12 || month < 1) {
                    menuPresenter.showPrompt("This is not a valid month.");
                    continue;
                }
                return month;
            } catch (NumberFormatException e) {
                menuPresenter.showPrompt("Invalid input.");
            }
        }
    }

    /**
     * Allows the user to select the first date of the month of the conference
     * @param month the month that was selected for the first day of the conference
     * @return an integer with the selected day
     */
    public int selectDay(int month){
        AdminUserController AUC = main.getAdminUserController();
        menuPresenter.showPrompt("Enter the Date (a number):");
        while (true) {
            try {
                String input = scanner.nextLine();
                int day = Integer.parseInt(input);
                if (!AUC.validateDays(month, day)) {
                    menuPresenter.showPrompt("This is not a valid date.");
                    continue;
                }
                return day;
            } catch (NumberFormatException e) {
                menuPresenter.showPrompt("Invalid input.");
            }
        }
    }

    /**
     * Allows the user to view all the dates that have already been date
     */
    public void viewDays(){
        AdminUserController AUC = main.getAdminUserController();
        LinkedHashMap<String, Integer> viewDays = AUC.viewDaysDict();
        for (String day : viewDays.keySet()) {
            menuPresenter.showPrompt(day);
        }
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
    }


    /**
     * Runs the Venue menu
     */
    public void setAndRunVenueMenu(){
        Menu vMenu = new Menu("");
        if (main.getAdminUserController().areDates()) {
            vMenu.addOption(new AddDayToVenue());
            vMenu.addOption(new ViewCurrentDays());
        }
        else{
            vMenu.addOption(new SelectStartDayCmd());
        }
        vMenu.addOption(new BackCmd());
        run(vMenu, "Back");
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> executeCmd(Command command) {

        return command.execute(this);
    }
}
