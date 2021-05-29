/*
@layer: Controller
 */

package Controllers.MenuAdapters;

import Controllers.Main;
import Presenters.Menus.DynamicMenus.AttendeeMenus.RemoveFriendMenu;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

import Presenters.Menu;
import Presenters.MenuPresenter;

/**
 * A class designed to handle commands that require back and forth communication with the controllers classes during
 * its execution/construction alongside the iteration between within dynamic menus.
 */
public class DynamicMenuOperatorHub {

    private final Main main;

    /**
     * Constructor for ComplexBuilder
     * @param menuPresenter: type MenuPresenter
     */
    public DynamicMenuOperatorHub(MenuPresenter menuPresenter){
        this.main = menuPresenter.getMain();
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return a command to be executed in another part of the program
     */
    public Optional<Command> execute(Command command){
        return command.execute(this);
    }


    /**
     * Build conversation using the options in the given menu.
     * @param menu the menu with which to build the conversation
     * @return a command to build a conversation.
     */
    public Optional<Command> buildConversation(Menu menu){
        Controllers.MenuAdapters.ConversationBuilder builder = new ConversationBuilder(menu, main);
        return builder.buildConversation();
    }

    /**
     * Opens a menu that allows users to select a conversation that they'd like to read and send messages to.
     * @param menu the menu containing the conversation options
     * @return empty Optional
     */
    public Optional<Command> viewConversations(Menu menu){
        Controllers.MenuAdapters.ConversationViewer viewer = new ConversationViewer(menu, main);
        viewer.viewConversations();
        return Optional.empty();
    }

    /**
     * Runs the build event command, iterating through a set of dynamic menus that present options for the
     * construction of a new event.
     * @return iff user constructs a new event, then return a BuildEvent command.
     */
    public Optional<Command> openEventManagement(){
        Controllers.MenuAdapters.EventManager buildEvent = new EventManager(main);
        return buildEvent.openEventManagement();
    }

    /**
     * Opens a menu that allows the user to enroll & dis-enrol from events offered by the venue.
     * @param menu the menu providing the options for enrollment
     * @return empty Optional
     */
    public Optional<Command> openEventManager(Menu menu){
        Controllers.MenuAdapters.EventEnroller eventEnroller = new EventEnroller(main, menu);
        eventEnroller.showEventOptions();
        return Optional.empty();
    }

    /**
     * Adds new friends to an attendees' list of friends, either through a menu or direct ID input.
     * @return empty Optional
     */
    public Optional<Command> addNewFriends(){
        Controllers.MenuAdapters.AddNewFriendOperator addNewFriendOperator = new AddNewFriendOperator(main);
        addNewFriendOperator.addNewFriends();
        return Optional.empty();
    }

    /**
     * Removes a friend from an attendees' list of friends
     * @return empty Optional
     */
    public Optional<Command> removeFriends(RemoveFriendMenu removeFriendsMenu){
        Controllers.MenuAdapters.RemoveFriendOperator removeFriendOperator =
                new RemoveFriendOperator(main, removeFriendsMenu);
        removeFriendOperator.removeFriends();
        return Optional.empty();
    }

    /**
     * Creates a new room
     * @return empty Optional
     */
    public Optional<Command> newRoom(){
        NewRoomOperator newRoomOperator = new NewRoomOperator(main);
        newRoomOperator.newRoom();
        return Optional.empty();
    }

    /**
     * Runs the mark as unread function.
     * @return empty optional
     */
    public Optional<Command> runMarkUnread() {
        ConversationViewer conversationViewer = new ConversationViewer(main);
        conversationViewer.runMarkUnread();
        return Optional.empty();
    }

    /**
     * Runs the archive message menus.
     * @return empty optional
     */
    public Optional<Command> runArchive() {
        ConversationViewer conversationViewer = new ConversationViewer(main);
        conversationViewer.runArchive();
        return Optional.empty();
    }

    /**
     * Runs the unarchive menus
     * @return empty optional
     */
    public Optional<Command> runUnarchive() {
        ConversationViewer conversationViewer = new ConversationViewer(main);
        conversationViewer.runUnarchive();
        return Optional.empty();
    }

    /**
     * Runs the delete conversation menus.
     * @return empty optional
     */
    public Optional<Command> runDeleteConversation() {
        DeleteConversationOperator deleteConversationOperator = new DeleteConversationOperator(main);
        deleteConversationOperator.runDeleteMenu();
        return Optional.empty();
    }
    /**
     * Runs the unblock user menus.
     * @return empty optional
     */
    public Optional<Command> runUnblockUser(){
        BlockAndUnblockUserOperator blockAndUnblockUserOperator = new BlockAndUnblockUserOperator(main);
        blockAndUnblockUserOperator.runUnblockMenu();
        return Optional.empty();
    }
    /**
     * Runs the ban user menus.
     * @return empty optional
     */
    public Optional<Command> runBlockUser(){
        BlockAndUnblockUserOperator blockAndUnblockUserOperator = new BlockAndUnblockUserOperator(main);
        blockAndUnblockUserOperator.runBlockMenu();
        return Optional.empty();
    }

    /**
     * Runs a new menu to mark a request  as addressed.
     * @return empty optional
     */
    public Optional<Command> runMarkAddressed() {
        RequestMenuOperator requestMenuOperator = new RequestMenuOperator(main);
        requestMenuOperator.runMarkAddressed();
        return Optional.empty();
    }

    /**
     * Runs a new menu to mark a request as pending.
     * @return empty optional
     */
    public Optional<Command> runMarkPending() {
        RequestMenuOperator requestMenuOperator = new RequestMenuOperator(main);
        requestMenuOperator.runMarkPending();
        return Optional.empty();
    }

    /**
     * Runs a new menu to create a new request for the user that is currently logged in.
     * @return empty optional
     */
    public Optional<Command> runCreateRequest() {
        RequestMenuOperator requestMenuOperator = new RequestMenuOperator(main);
        requestMenuOperator.runCreateRequest();
        return Optional.empty();
    }

    /**
     * Runs a menu to cancel the a request.
     * @return empty optional
     */
    public Optional<Command> runCancelRequest() {
        RequestMenuOperator requestMenuOperator = new RequestMenuOperator(main);
        requestMenuOperator.runCancelRequest();
        return Optional.empty();
    }

    /**
     * Runs a menu that allows the user to choose an event using filters
     * @param filterEventsMenu the menu containing the filter strategies
     * @return empty optional
     */
    public Optional<Command> runFilterEventsMenu(Menu filterEventsMenu){
        FilterEventsOperator filterEventsOperator = new FilterEventsOperator(main);
        filterEventsOperator.showFilterMenu(filterEventsMenu);
        return Optional.empty();
    }

    /**
     * Runs a menu that allows the user to manage the time span of the events being handled
     * @return empty optional
     */
    public Optional<Command> showVenueManagement(){
        VenueMenuOperator venueMenuOperator = new VenueMenuOperator(main);
        venueMenuOperator.setAndRunVenueMenu();
        return Optional.empty();
    }

    /**
     * Runs a menu that allows the user to leave ratings
     * @return empty optional
     */
    public Optional<Command> runLeaveRatingMenu(){
        ReviewMenuOperator reviewMenuOperator = new ReviewMenuOperator(main);
        reviewMenuOperator.runLeaveRatingMenu();
        return Optional.empty();
    }

    /**
     * Runs a menu that allows the user to view all ratings
     * @return empty optional
     */
    public Optional<Command> showAllReviews(){
        ReviewMenuOperator reviewMenuOperator = new ReviewMenuOperator(main);
        reviewMenuOperator.getReviews();
        return Optional.empty();
    }
}
