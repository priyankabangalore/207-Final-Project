/*
@layer: Use Case
@collaborators: Attendee entity, Event entity
*/

package UseCases;

import Entities.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendeeManager {

    private final ArrayList<Attendee> attendees;

    /**
     * Constructor for AttendeeManager
     * @param attendees : list of all Attendees in system
     */
    public AttendeeManager(ArrayList<Attendee> attendees) {
        this.attendees = attendees;
    }

    /**
     * Get a list of all attendees in the program
     *
     * @return list of all registered attendees
     */
    public ArrayList<Attendee> getAttendeeObjects() {
        return attendees;
    }

    /**
     * Gets a dictionary of all of the attendees in the system.
     * @author Priyanka
     * @return Hashmap(K, V) where K: attendee's username, V: attendee's ID.
     */
    public HashMap<String, Integer> getAttendees() {
        HashMap<String, Integer> attendeeMap = new HashMap<>();
        for (Attendee a : this.attendees) {
            int ID = a.getID();
            attendeeMap.put(a.getUsername(), ID);
        }
        return attendeeMap;
    }

    /**
     * Gets attendee's ID
     * @author Priyanka
     * @param a: attendee of Attendee type (entity)
     * @return Integer
     */
    public Integer getAttendeeID(Attendee a)
    {
        return a.getID();
    }

    /**
     * Get the Attendee with the given id
     *
     * @param attendeeID id of an attendee
     * @return return the attendee who matches the id
     */
    public Attendee getAttendee(Integer attendeeID) {
        for (Attendee attendee : attendees) {
            if (attendee.getID() == attendeeID) {
                return attendee;
            }
        }
        return null;
    }

    /**
     * Gets all attendees that aren't the currently logged in users' friends.
     * @author Priyanka
     * @param attendeeID: ID of the attendee.
     * @return Hashmap(K,V), where K: the attendees' name, V: the attendees' ID.
     */
    public HashMap<String, Integer> getNonFriendAttendees(int attendeeID){
        HashMap<String, Integer> nonFriendList = new HashMap<>();
        ArrayList<Integer> friendIDs = getFriends(attendeeID);
        for(Attendee attendee : attendees) {
            Integer attID = getAttendeeID(attendee);
            if (!friendIDs.contains(attID)) {
                if (attID == attendeeID) {
                    continue;
                }
                nonFriendList.put(attendee.getUsername(), attendee.getID());
            }
        }
        return nonFriendList;
    }

    /**
     * Determines if the user ID is an Attendee ID
     *
     * @author Stephanie
     * @param userID the ID being checked
     * @return True if the user is an attendee
     */
    public boolean isAttendees(int userID){
        for (Attendee attendee : attendees){
            if (attendee.getID() == userID){
                return true;
            }
        }
        return false;
    }


    /**
     * Get a list of all friends (IDs) for the attendee with the given ID
     *
     * @param attendeeID the ID for the attendee who's friends list is requested
     * @return list of IDs for all friends for this particular attendee
     */
    public ArrayList<Integer> getFriends(Integer attendeeID) {
        Attendee attendee = getAttendee(attendeeID);
        if (attendee == null) {
            return new ArrayList<>();
        }
        return attendee.getFriends();
    }

    /**
     * Getter for events in which the attendee with the given ID is enrolled
     *
     * @param attendeeID ID for the attendee who's enrolled events are requested
     * @param userManager UserManager
     * @return list of IDs for events in which the attendee with the given ID is enrolled
     */
    public ArrayList<Integer> getEvents(Integer attendeeID, UserManager userManager) {
        Attendee attendee = (Attendee) userManager.getUser(attendeeID);
        if (attendee == null) {
            return new ArrayList<>();
        }
        return attendee.getEvents();
    }


    /**Add the attendee to the list of all attendees
     *
     * @param attendee the attendee that needs to be added
     */
    private void addAttendee(Attendee attendee) {
        this.attendees.add(attendee);
    }

    /**
     * Create a new attendee
     *
     * @param username the username for the new attendee
     * @param password the password for the new attendee
     * @param userManager UserManager
     * @param email the email address for the new email
     */
    public int createAttendee(String username, String password, UserManager userManager, String email) {
        int id = userManager.generateID();
        Attendee attendee = new Attendee(username, password, id, email);
        addAttendee(attendee);
        userManager.addUser(attendee);
        return id;
    }

    /**
     * Add event with the given ID to the given attendee's list of events
     *
     * @param eventID ID for the event that is being added to the event list of the attendee
     * @param attendeeID ID for the attendee
     * @param userManager UserManager instantiation
     * @param eventManager EventManager
     */
    public void addEventToAttendee(Integer eventID, Integer attendeeID, UserManager userManager, EventManager eventManager) {
        Attendee attendee = (Attendee) userManager.getUser(attendeeID);
        if (checkAttendeeIsAvailableForEvent(eventID, attendeeID, userManager, eventManager)) {
            if (attendee != null) {
                attendee.addEvent(eventID);
            }
        }
    }

    /**
     * Checks if attendee is available at the time of the event/panel/party
     * @author Priyanka
     * @param eventID id of event
     * @param attendeeID ID for the attendee
     * @param um UserManager instantiation
     * @param e EventManager instantiation
     * @return true iff attendee is available at the time
     */
    public boolean checkAttendeeIsAvailableForEvent(int eventID, Integer attendeeID, UserManager um, EventManager e) {
        boolean x = false;
        Event event = e.getEvent(eventID);
        ArrayList<Integer> eventTimes = new ArrayList<>();
        eventTimes.add(event.getStartTime()); //don't need to add end time
        Integer span = event.getEndTime()-event.getStartTime();
        if(span==1) //talk (10-11pm)
        {
            eventTimes.add(event.getStartTime()); }
        else if(span==2) //panel (10-12pm)
        {
            eventTimes.add(event.getStartTime()+1); }
        else if(span==3) //panel (9-12pm)
        {
            eventTimes.add(event.getStartTime()+1);
            eventTimes.add(event.getStartTime()+2); }
        else if(span==4) //party (10-14pm)
        {
            eventTimes.add(event.getStartTime()+1);
            eventTimes.add(event.getStartTime()+2);
            eventTimes.add(event.getStartTime()+3); }
        else if(span==5) //party (10-15pm)
        {
            eventTimes.add(event.getStartTime()+1);
            eventTimes.add(event.getStartTime()+2);
            eventTimes.add(event.getStartTime()+3);
            eventTimes.add(event.getStartTime()+4); }
        for(int time : eventTimes) {
            if(!getEvents(attendeeID, um).contains(time))
            {
                x = true; }
        }return x;
    }

    /**
     * Remove event with the given ID from the given attendee's list of events
     *
     * @param eventID ID for the event that is being removed from the event list of the attendee
     * @param attendeeID ID for the attendee
     */
    public void removeEventFromAttendee(Integer eventID, Integer attendeeID) {
        Attendee attendee = getAttendee(attendeeID);
        if (attendee != null) {
            attendee.removeEvent(eventID);
        }
    }


    /**
     * Add a friend to a attendee
     * @param friendID id of the friend to be add
     * @param attendeeID attendee who wants to add friend
     */
    public void addNewFriend(int friendID, int attendeeID) {
        getAttendee(attendeeID).addFriend(friendID);
    }

    /**
     * Remove an attendee's friend
     * @author Priyanka
     * @param friendID ID of the friend to be removed
     * @param attendeeID ID of the attendee
     */
    public void removeFriend(int friendID, int attendeeID) {
        Attendee attendee = getAttendee(attendeeID);
        if (attendee != null) {
            attendee.removeFriend(friendID);
        }
    }

    /**
     * Add a new request to this user's list of requests
     * @param userID ID of the attendee
     * @param requestID ID of the request
     */
    public void addRequest(int userID, int requestID) {
        Attendee user = getAttendee(userID);
        if (user != null) {
            user.addRequest(requestID);
        }
    }

    /**
     * Get a list of the IDs of all requests that this user has made
     * @param userID ID of the attendee
     * @return list of IDs of all requests made by this user
     */
    public ArrayList<Integer> getUserRequests(int userID) {
        Attendee user = getAttendee(userID);
        if (user == null) {
            return new ArrayList<>();
        }
        return user.getRequests();
    }

    /**
     * Remove the request from this user's list of requests
     * @param userID ID of the attendee
     * @param requestID ID of the request
     */
    public void removeRequest(int userID, int requestID) {
        Attendee user = getAttendee(userID);
        if (user != null) {
            user.removeRequest(requestID);
        }
    }

    /**
     * Gets all ratings this user has made
     * @author Priyanka
     * @param attendeeID ID of the attendee
     * @return Hashmap(K,V), where K: Speaker ID, V: rating out of 5 given by the attendee
     */
    public HashMap<Integer, Integer> viewRatingsMade(int attendeeID)
    {
        Attendee attendee = getAttendee(attendeeID);
        if (attendee == null) {
            return new HashMap<>();
        }
        return attendee.getRatings();
    }

    /**
     * Add a new rating to this user's ratings
     * @author Priyanka
     * @param attendeeID ID of the attendee
     * @param speakerID ID of speaker
     * @param rating rating to be added
     */
    public void addRatingtoAttendee(Integer speakerID, int rating, int attendeeID) {
        getAttendee(attendeeID).addRating(speakerID, rating);
    }

    /**
     * Deletes an event from all related attendees
     * @param eventID the event ID to be deleted
     */
    public void deleteEvent(Integer eventID) {
        for (Attendee attendee : attendees) {
            attendee.removeEvent(eventID);
        }
    }
}

