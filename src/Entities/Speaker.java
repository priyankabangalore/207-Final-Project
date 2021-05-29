/*
@layer: Entity
 */

package Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * A speaker of this system
 */
public class Speaker extends User {

    private final ArrayList<Integer> talkEvents;
    private final ArrayList<Integer> panelEvents;
    private final LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> speakerSchedule;
    private final ArrayList<Integer> requests = new ArrayList<>();
    private final ArrayList<Integer> ratings = new ArrayList<>(); //out of 5
    private final int startWorking = 9;
    private final int stopWorking = 17;

    /**
     * Speaker instance variable to represents a speaker
     * @param username Speaker's username
     * @param password Speaker's password
     * @param id Speaker's unique ID
     * @param email Speaker's email
     */
    public Speaker(String username, String password, int id, String email, Collection<Integer> existingDays){
        super(username, password, id, email);
        this.talkEvents = new ArrayList<>();
        this.panelEvents = new ArrayList<>();
        speakerSchedule = new LinkedHashMap<>();
        for (Integer day : existingDays) {
            addDate(day);
        }
    }

    /**
     * Gets permission level of Speaker user
     * @return int of permission level
     */
    @Override
    public int getPermission() {
        return 1;
    }

    /**
     * Get all the talks this speaker speaks
     * @return list of event ids
     */
    public ArrayList<Integer> getAllEvents() {
        ArrayList<Integer> allEvents = new ArrayList<>();
        allEvents.addAll(talkEvents);
        allEvents.addAll(panelEvents);
        return allEvents;
    }

    /**
     * Get all panel events this speaker speaks at
     * @author Priyanka
     * @return list of event IDs
     */
    public ArrayList<Integer> getPanelEvents() {
        return panelEvents;
    }

    /**
     * Adds to the speakers' ratings
     * @author Priyanka
     * @param rating rating to add
     */
    public void addRating(Integer rating) {
        ratings.add(rating);
    }

    /**
     * Get all the speakers' ratings
     * @author Priyanka
     * @return list of numerical ratings
     */
    public ArrayList<Integer> getRatings() {
        return ratings;
    }

    /**
     * Gets speakers' average rating to one decimal
     * @author Priyanka
     * @return average rating
     */
    public String getAverageRating() {
        float sum = 0;

        for (Integer rating : ratings) {
            sum += rating;
        }
        float mean = sum / ratings.size() * 10;
        float avg = Math.round(mean);
        return Float.toString(avg / 10);
    }

    /**
     * Get all requests this user has made
     * @return list of request IDs
     */
    public ArrayList<Integer> getRequests() { return this.requests;}

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
     * Add a normal event to this speaker
     * @param startTime start time of the event
     * @param endTime end time of the event
     * @param id the id of the event to be added
     * @param day the day of the event
     */
    public void addOneSpeakerEvent(Integer id, Integer startTime, Integer endTime, int day) {
        if (!talkEvents.contains(id)) {
            talkEvents.add(id);
            for (Integer time = startTime; time <= endTime; time++) {
                addEvent(id, time, day);
            }
        }
    }

    /**
     * Add a Panel event to this speaker
     * @param startTime start time of the event
     * @param endTime end time of the event
     * @param id the id of the event to be added
     * @param day the day for the event
     */
    public void addPanelEvent(Integer id, Integer startTime, Integer endTime, int day) {
        if (!panelEvents.contains(id)) {
            panelEvents.add(id);
            for (Integer time = startTime; time <= endTime; time++) {
                addEvent(id, time, day);
            }
        }
    }

    /**
     * Add event to speaker schedule
     * @param day day of event
     * @param time time of event
     * @param id the id of the event to be added
     */
    private void addEvent(Integer id, Integer time, int day) {
        speakerSchedule.get(day).put(time, id);
    }

    /**
     * Cancels an event, removing speaker from it
     * @param eventID the id of the event to be removed
     */
    public void cancelEvent(Integer eventID) {
        talkEvents.remove(eventID);
        panelEvents.remove(eventID);
        for (LinkedHashMap<Integer, Integer> dates : speakerSchedule.values()){
            for (int time : dates.keySet()) {
                if (Objects.equals(dates.get(time), eventID)) {
                    dates.put(time, null);
                }
            }
        }
    }

    /**
     * Gets the speaker's schedule
     * @author Priyanka
     * @return the schedule of the speaker - LinkedHashMap(K,V), where K is eventID, V is event time
     */
    public LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> getSchedule() {
        return speakerSchedule;
    }

    /**
     * Add a new date to the schedule
     * @param date a date
     */
    public void addDate(Integer date) {
        LinkedHashMap<Integer, Integer> daySchedule = new LinkedHashMap<>();
        for (int i = startWorking; i<=stopWorking; i++){
            daySchedule.put(i, null);
        }
        speakerSchedule.put(date, daySchedule);
    }

    /**
     * Validate whether this room is available.
     * @param time the time
     * @param date the date
     * @return true if available
     */
    public boolean validateTime(Integer time, Integer date) {
        if (time < startWorking || time > stopWorking) {
            return false;
        }
        if (speakerSchedule.get(date) == null) {
            return false;
        }
        return speakerSchedule.get(date).get(time) == null;
    }
}
