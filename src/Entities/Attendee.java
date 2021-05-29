/*
@layer: Entity
 */


package Entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An attendee of this system
 */
public class Attendee extends User {
    private final ArrayList<Integer> friends;
    private final ArrayList<Integer> events;
    private final ArrayList<Integer> requests;
    private final HashMap<Integer, Integer> ratings = new HashMap<>(); //<speaker ID, rating>

    /**
     * Attendee instance object to represent an attendee
     * @param username Attendees Username
     * @param password Attendees Password
     * @param id Attendees Unique ID Number
     * @param email the email for this user
     */
    public Attendee(String username, String password, int id, String email) {
        super(username, password, id, email);
        this.friends = new ArrayList<>();
        this.events = new ArrayList<>();
        this.requests = new ArrayList<>();
    }

    /**
     * Gets permission level of attendee user
     * @return int of permission level
     */
    @Override
    public int getPermission() {
        return 0;
    }

    // getters
    /**
     * Get all the friends this attendee has
     * @return list of user ids
     */
    public ArrayList<Integer> getFriends() {
        return friends;
    }

    /**
     * Get the events this attendee attends
     * @return list of event ids
     */
    public ArrayList<Integer> getEvents() {
        return events;
    }

    /**
     * Get all requests this user has made
     * @return list of request IDs
     */
    public ArrayList<Integer> getRequests() { return this.requests;}

    /**
     * Get all the ratings the attendee made
     * @author Priyanka
     * @return list of ratings
     */
    public HashMap<Integer, Integer> getRatings() {
        return ratings;
    }

    /**
     * Add a request to this user's requests list
     * @author Priyanka
     * @param speakerID ID of the speaker
     * @param rating ID of the rating to be added
     */
    public void addRating(Integer speakerID, int rating) { this.ratings.put(speakerID,rating); }

    /**
     * Add a request to this user's requests list
     * @param requestID ID of the request to be added
     */
    public void addRequest(int requestID) { this.requests.add(requestID); }

    /**
     * Removes a request from this user's requests list
     * @param requestID ID of the request to be removed
     */
    public void removeRequest(int requestID) {
        this.requests.remove(requestID);
    }

    /**
     * Add a friend to this Attendee
     * @param userID the friend's id
     */
    public void addFriend(Integer userID) {
        if (!friends.contains(userID)) {
            friends.add(userID);
        }
    }

    /**
     * Remove a friend from this Attendee's friends
     * @author Priyanka
     * @param friendID the friend's ID
     */
    public void removeFriend(Integer friendID) {
        friends.remove(friendID);
    }

    /**
     * Add an event to this attendee
     * @param eventID the id of the event to be added
     */
    public void addEvent(Integer eventID) {
        if (!events.contains(eventID)) {
            events.add(eventID);
        }
    }

    /**
     * Removes an event from an attendee's list of events
     * @param eventID: ID of the event
     */
    public void removeEvent(Integer eventID) {
        events.remove(eventID);
    }
}