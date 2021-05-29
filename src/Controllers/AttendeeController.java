/*
@layer: Controller
@collaborators: AttendeeManager, UserManager, EventManager
 */

package Controllers;

import UseCases.*;

import java.util.ArrayList;
import java.util.HashMap;


public class AttendeeController extends UserController {

    /**
     * Constructor for AttendeeController
     * @param AM: AttendeeManager instance
     * @param SM: SpeakerManager instance
     * @param OM: OrganizerManager instance
     * @param UM: UserManager instance
     * @param EM: EventManager instance
     * @param VM: VenueManager instance
     * @param RM: RequestManager instance
     * @param DM: DateManager instance
     */
    public AttendeeController(AttendeeManager AM, SpeakerManager SM, OrganizerManager OM,
                              UserManager UM, EventManager EM, VenueManager VM, RequestManager RM, DateManager DM) {
        super(AM, SM, OM, UM, EM, VM, RM, DM);
    }

    /**
     * Enrol current attendee with the given ID into the event with the given ID
     * @param eventID ID for the event in which the attendee is being enrolled
     * @param attendeeID: ID of attendee
     */
    public void enrolInEvent(Integer eventID, Integer attendeeID) {
        AM.addEventToAttendee(eventID, attendeeID, UM, EM);
        EM.addUserToEvent(attendeeID, eventID);
    }

    /**
     * Un-enrol attendee with the given ID from the event with the given ID
     *
     * @param eventID ID for the event from which the attendee is being un-enrolled
     * @param attendeeID ID for the attendee who is being un-enrolled from the given event
     */
    public void disEnrolInEvent(Integer eventID, Integer attendeeID) {
        AM.removeEventFromAttendee(eventID, attendeeID);
        EM.removeUserFromEvent(attendeeID, eventID);
    }

    /**
     * Gets a dictionary of all of the currently logged in users' friends.
     * @author Priyanka
     * @param attendeeID: ID of attendee
     * @return Hashmap where K: friend's username, V: friend's ID.
     */
    public HashMap<String, Integer> getFriendsDict(int attendeeID){
        HashMap<String, Integer> friends = new HashMap<>();
        ArrayList<Integer> attendeeFriends = AM.getFriends(attendeeID);
        for (int aFriends : attendeeFriends) {
            friends.put(UM.getUserName(aFriends), aFriends);
        }
        return friends;
    }

    /**
     * Gets a dictionary of all of the events the attendee is signed up for.
     * @author Priyanka
     * @param attendeeID: ID of attendee
     * @return Hashmap where K: event name, V: event ID.
     */
    public HashMap<String, Integer> getEventsSignedUp(int attendeeID){
        HashMap<String, Integer> eventsSignedUp = new HashMap<>();
        ArrayList<Integer> allEventIDs = AM.getEvents(attendeeID, UM);
        for (Integer eventID : allEventIDs) {
            eventsSignedUp.put(EM.getEventName(eventID), eventID);
        }
        return eventsSignedUp;
    }

    /**
     * Gets a list of the events an attendee's friends are signed up to attend.
     * @author Priyanka
     * @param attendeeID: ID of attendee
     * @return List of event names
     */
    public ArrayList<String> getFriendsEvents(int attendeeID) {
        ArrayList<String> friendEvents = new ArrayList<>();
        ArrayList<Integer> attendeeFriends = AM.getFriends(attendeeID);

        for (int friend : attendeeFriends) {
            ArrayList<Integer> events = AM.getEvents(friend, UM);
            for (int eventID : events) {
                friendEvents.add(EM.getEventName(eventID));
            }
        }
        return friendEvents;
    }

    /**
     * Add a new friend to the currently logged in users' list of friends.
     * @author Priyanka
     * @param friendID: the friend to be added
     * @param attendeeID: ID of the attendee
     * @return true iff successfully added
     */
    public boolean addNewFriend(int friendID, int attendeeID) {
        if (isAttendee(friendID) && !getFriendsDict(attendeeID).containsValue(friendID)){
            AM.addNewFriend(friendID, attendeeID);
            return true;
        }
        return false;
    }

    /**
     * Remove a friend from the currently logged in users' list of friends.
     * @author Priyanka
     * @param friendID : the friend to be removed
     * @param attendeeID : ID of the attendee
     */
    public void removeFriend(int friendID, int attendeeID) {
        AM.removeFriend(friendID, attendeeID);
    }

    /**
     * Tests to see if a potential userID refers to an attendee.
     * @param userID the userid of the user
     * @return true iff userID refers to an attendee.
     */
    public boolean isAttendee(int userID) {
        return AM.isAttendees(userID);
    }

    /**
     * Gets all attendees that aren't the currently logged in users friends.
     * @author Priyanka
     * @param attendeeID: ID of attendee
     * @return K: the attendees' name, V: the attendees ID
     */
    public HashMap<String, Integer> getNonFriendAttendees(int attendeeID){

        return AM.getNonFriendAttendees(attendeeID);
    }

    /**
     * Add a new rating to this user's ratings after checking that the value is valid (less than or
     * equal to 5) & the speaker hasn't
     * already been reviewed by the user
     * @author Priyanka
     * @param attendeeID ID of the attendee
     * @param speakerID ID of speaker
     * @param rating rating to be added
     */
    public void addRating(Integer speakerID, int rating, int attendeeID) {
        if(rating<=5 && !AM.viewRatingsMade(attendeeID).containsKey(speakerID)) //rating cannot be greater than 5
        {
            AM.addRatingtoAttendee(speakerID, rating, attendeeID);
            SM.addRatingtoSpeaker(rating, speakerID);
        }
    }

    /**
     * View all ratings this user has made
     * @author Priyanka
     * @param attendeeID ID of the attendee
     * @return Hashmap(K,V), where K: Speaker ID, V: rating out of 5 given by the attendee
     */
    public HashMap<String, String> viewRatingsMade(int attendeeID) {
        HashMap<String, String> ratingMap = new HashMap<>();
        HashMap<Integer, Integer> ogmap = AM.viewRatingsMade(attendeeID);
        for(Integer speakerID : ogmap.keySet()) {
            String newKey = getUsername(speakerID) + "(ID" + speakerID.toString() + ")";
            ratingMap.put(newKey, ogmap.get(speakerID).toString() + " / 5");
        }
        return ratingMap;
    }
}
