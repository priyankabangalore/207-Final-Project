package Controllers.MenuAdapters;

import Controllers.LoginController;
import Controllers.Main;
import Controllers.RequestController;
import Presenters.Menus.DynamicMenus.StrategyDynamicMenu;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.RequestStrategiesAndCommands.CancelRequestStrat;
import Presenters.PresenterHelperCommands.RequestStrategiesAndCommands.CreateRequestStrat;
import Presenters.PresenterHelperCommands.RequestStrategiesAndCommands.MarkAdressedStrat;
import Presenters.PresenterHelperCommands.RequestStrategiesAndCommands.MarkPendingStrat;

import java.util.Optional;

public class RequestMenuOperator extends DynamicMenuOperator {

    private final RequestController RC;
    private final int userID;

    /**
     * Constructor for DynamicMenuOperator
     *
     * @param main : type Main
     */
    public RequestMenuOperator(Main main) {
        super(main);
        RC = main.getRequestController();
        this.userID = main.getLoginController().getCurrentUserID();
    }


    /**
     * Runs a new menu to mark a request  as addressed.
     */
    public void runMarkAddressed() {
        StrategyDynamicMenu strategyDynamicMenu = new StrategyDynamicMenu(main, new MarkAdressedStrat(main), userID);
        runUntilBackOrExit(strategyDynamicMenu);
    }

    /**
     * Runs a new menu to mark a request as pending.
     */
    public void runMarkPending() {
        StrategyDynamicMenu strategyDynamicMenu = new StrategyDynamicMenu(main, new MarkPendingStrat(main), userID);
        runUntilBackOrExit(strategyDynamicMenu);
    }

    /**
     * Runs a new menu to create a new request for the user that is currently logged in.
     */
    public void runCreateRequest() {
        StrategyDynamicMenu strategyDynamicMenu = new StrategyDynamicMenu(main, new CreateRequestStrat(main), userID);
        runUntilBackOrExit(strategyDynamicMenu);
    }

    /**
     * Runs a menu to cancel the a request.
     */
    public void runCancelRequest() {
        StrategyDynamicMenu strategyDynamicMenu = new StrategyDynamicMenu(main, new CancelRequestStrat(main), userID);
        runUntilBackOrExit(strategyDynamicMenu);
    }

    @Override
    public Optional<Command> executeCmd(Command command){return command.execute(this);}

    /**
     * Mark the request with the given ID as addressed
     * @param requestID ID of the request
     */
    public void markAddressed(int requestID) {
        RC.markAddressed(requestID);
        showPrompt("Marked as Resolved");
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
    }

    /**
     * Mark the request with the given ID as pending
     * @param requestID ID of the request
     */
    public void markPending(int requestID) {
        RC.markPending(requestID);
        showPrompt("Marked as Pending");
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
    }

    /**
     * Create a new request for the user that is currently logged in
     * @param request String representation of the request
     * @param LC LoginController
     */
    public void createRequest(String request, LoginController LC) {
        RC.createRequest(request, main.getLoginController());
        showPrompt("Request Sent");
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
    }

    /**
     * Cancel the request with the given ID, given that it exists
     * @param requestID ID of the request being cancelled
     */
    public void cancelRequest(int requestID) {
        RC.cancelRequest(requestID, main.getLoginController());
        showPrompt("Request Cancelled");
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
    }
}
