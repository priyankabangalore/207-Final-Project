/*
@layer: Use Case
@collaborators: Speaker Entity, UserManager, EventManager
 */

package UseCases;

import Entities.Attendee;
import Entities.Event;
import Entities.Speaker;

import java.util.*;

public class SpeakerManager {

    private final ArrayList<Speaker> speakers;
    private final ArrayList<Integer> IDs;

    /**
     * Constructor for SpeakerManager
     * @param speakers: list of all Speakers in system
     * @param ids: list of Ids of all Speakers in system
     */
    public SpeakerManager(ArrayList<Speaker> speakers, ArrayList<Integer> ids) {
        this.speakers = speakers;
        this.IDs = ids;
    }

    //getters
    /**
     * Gets ALL users currently registered as speakers in the program
     *
     * @return list of all speakers
     */
    public ArrayList<Speaker> getSpeakerObjects() {
        return speakers;
    }

    /**
     * Gets a map of information pertaining to ALL speakers in the system.
     * @author Priyanka
     * @return Hashmap(K, V) where K: speaker's name, V: speaker's ID.
     */
    public HashMap<String, Integer> getSpeakers (){
        HashMap<String, Integer> speakerMap = new HashMap<>();
        for (Speaker s : this.speakers) {
            int ID = s.getID();
            speakerMap.put(s.getUsername(), ID);
        }
        return speakerMap;
    }

    /**
     * Gets a string containing all event names for the events in which a speaker is enrolled.
     * @param speaker the speaker who's information is sought
     * @param eventManager EventManager
     * @return string representation of all events for which the speaker is enrolled
     */
    private String getEventInformation(Speaker speaker, EventManager eventManager) {
        ArrayList<Integer> speakerEvents = getEvents(speaker.getID());
        StringBuilder info = new StringBuilder();
        for (Integer eventID: speakerEvents) {
            info.append(eventManager.getEventName(eventID)).append(", ");
        }
        return info.substring(0, info.length() - 3);
    }

    /**
     * Get a SINGLE speaker object using the speaker's ID
     * @author Priyanka
     * @param speakerID: the id of the speaker
     * @return the speaker object (entity)
     */
    public Speaker getSpeakerObject(int speakerID) {
        for (Speaker s : speakers) {
            if (s.getID() == speakerID) {
                return s;
            }
        }
        return null;
    }

    /**
     * Gets a hashmap of the speaker's schedule
     * @author Priyanka
     * @param speakerID : ID of the speaker
     * @return Hashmap(K,V), where K: time and V:eventID.
     */
    public LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> getSpeakerSchedule(int speakerID) {
        Speaker s = getSpeakerObject(speakerID);
        if (s == null) {
            return new LinkedHashMap<>();
        }
        return s.getSchedule();
    }

    /**
     * Gets the hashmap (attendee username: attendee id) of the attendees in each event
     * the speaker is speaking in.
     * @author Priyanka
     * @param speakerID: ID of the speaker
     * @param AM AttendeeManager
     * @return all attendees that are signed up for events hosted by the current speaker as a hashmap(K,V),
     *         K: attendee username,
     *         V: attendee ID
     */
    public HashMap<String, Integer> getAttendees(int speakerID, AttendeeManager AM) {
        HashMap<String, Integer> attendeeMap = new HashMap<>();
        ArrayList<Integer> eventIDs = getEvents(speakerID);
        ArrayList<Attendee> attendeesList = AM.getAttendeeObjects();
        for (Integer eventID : eventIDs) {
            for (Attendee a : attendeesList) {
                if (a.getEvents().contains(eventID)) {
                    attendeeMap.put(a.getUsername(), a.getID());
                }
            }
        }
        return attendeeMap;
    }

    /**
     * Get the events for which a speaker with the given ID is registered
     * @param speakerID the ID for the speaker who's events are needed
     * @return events for which the speaker with the given ID has registered
     */
    public ArrayList<Integer> getEvents(Integer speakerID) {
        Speaker speaker = getSpeakerObject(speakerID);
        if (speaker == null) {
            return new ArrayList<>();
        }
        return speaker.getAllEvents();
    }

    /**
     * Get the IDs of all speakers
     * @author Priyanka
     * @return list of integers of speaker IDs
     */
    public ArrayList<Integer> getIDs() {
        return IDs;
    }

    /**
     * Add this speaker to the list of all speakers
     *
     * @param speaker the speaker that needs to be added
     */
    private void addSpeaker(Speaker speaker) {
        this.speakers.add(speaker);
        this.IDs.add(speaker.getID());
    }

    /**
     * Create a new Speaker
     *
     * @param username the username for the new speaker
     * @param password the password for the new speaker
     * @param email email address for the new speaker
     * @param userManager UserManager
     * @return the unique ID generated for the new speaker
     */
    public int createSpeaker(String username, String password, UserManager userManager, String email, Collection<Integer> existingDays) {
        int id = userManager.generateID();
        Speaker speaker = new Speaker(username, password, id, email, existingDays);
        addSpeaker(speaker);
        userManager.addUser(speaker);
        return id;
    }

    /**
     * Checks if speaker is available for an event
     * @author Priyanka
     * @param eventID ID of event
     * @param speakerID ID of the speaker
     * @param em EventManager
     * @return true iff speaker is available at the time
     */
    public boolean checkSpeakerIsAvailable(int eventID, Integer speakerID, EventManager em) {
        Event e = em.getEvent(eventID);
        LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> schedule = getSpeakerSchedule(speakerID);
        if (schedule.isEmpty()) {
            return false;
        }
        LinkedHashMap<Integer, Integer> daySchedule = schedule.get(e.getDate());
        for (int time = e.getStartTime(); time <= e.getEndTime(); time++) {
            if (daySchedule.get(time) != null) {
                return false;
            }
        }
        return true;
    }


    /**
     * Checks if speakers are available at the given time before adding them to a panel event
     * @author Priyanka
     * @param eventID ID of the event
     * @param speakerIDs IDs of the speakers
     * @param em EventManager
     * @return true iff speakers are available at the time
     */
    public boolean checkSpeakersAreAvailable(int eventID, ArrayList<Integer> speakerIDs, EventManager em) {
        for (Integer speakerID : speakerIDs) {
            if (!checkSpeakerIsAvailable(eventID, speakerID, em)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Add an Talk event to the given speaker's list of events
     *
     * @param speakerID the speaker ID
     * @param eventID the ID for the event that is being added
     * @param date the date at which the speaker is being booked
     * @param em EventManager
     */
    public void addTalkEventToSpeaker(Integer speakerID, Integer eventID, EventManager em, int date) {
        Speaker speaker = getSpeakerObject(speakerID);
        if (checkSpeakerIsAvailable(eventID, speakerID, em)) {
            if (speaker != null) {
                speaker.addOneSpeakerEvent(eventID, em.getEventStartTime(eventID), em.getEventEndTime(eventID), date);
            }
        }
    }

    /**
     * Add a panel to the given list of speakers' lists of events
     * @author Priyanka
     * @param speakerIDs the Ids of the speakers
     * @param eventID the ID for the event that is being added
     * @param date the date at which the speaker is being booked
     * @param em EventManager
     */
    public void addPanelEventToSpeakers(ArrayList<Integer> speakerIDs, Integer eventID, EventManager em, int date) {
        if (checkSpeakersAreAvailable(eventID, speakerIDs, em)) {
            for (Integer speakerID : speakerIDs) {
                Speaker speaker = getSpeakerObject(speakerID);
                if (speaker != null) {
                    speaker.addPanelEvent(eventID, em.getEventStartTime(eventID), em.getEventEndTime(eventID), date);
                }
            }
        }
    }

    /**
     * Deletes an event from all related speakers
     * @param eventID the event ID to be deleted
     */
    public void deleteEvent(Integer eventID) {
        for (Speaker speaker : speakers) {
            speaker.cancelEvent(eventID);
        }
    }

    /**
     * Add a new request to this user's list of requests
     * @param userID ID of the speaker
     * @param requestID ID of the request
     */
    public void addRequest(int userID, int requestID) {
        Speaker user = getSpeakerObject(userID);
        if (user != null) {
            user.addRequest(requestID);
        }
    }

    /**
     * Get a list of the IDs of all requests that this user has made
     * @param userID ID of the speaker
     * @return list of IDs of all requests made by this user
     */
    public ArrayList<Integer> getUserRequests(int userID) {
        Speaker user = getSpeakerObject(userID);
        if (user == null) {
            return new ArrayList<>();
        }
        return user.getRequests();
    }

    /**
     * Remove the request from this user's list of requests
     * @param userID ID of the speaker
     * @param requestID ID of the request
     */
    public void removeRequest(int userID, int requestID) {
        Speaker user = getSpeakerObject(userID);
        if (user != null) {
            user.removeRequest(requestID);
        }
    }

    /**
     * Gets the average rating (out of 5) the user has
     * @author Priyanka
     * @param speakerID ID of speaker
     * @return String of rating out of 5
     */
    public String viewRatings(int speakerID) {
       String avg = getSpeakerObject(speakerID).getAverageRating();
       if(avg.equals("0.0"))
       {
           return "You have not received any ratings.";
       }
       return avg + "✿ / 5✿"; //returns something of the form 3.2/5
    }

    /**
     * Add a new rating to this speaker's ratings
     * @author Priyanka
     * @param speakerID ID of the speaker
     * @param rating rating to be added
     */
    public void addRatingtoSpeaker(int rating, int speakerID) {
        getSpeakerObject(speakerID).addRating(rating);
    }

    /**
     * Properly formats the time (am/pm)
     * @param time time as an int
     * @return time in proper format (am/pm)
     */
    public String toTime(int time){
        if (time < 12){
            return time + "am";
        }
        else if(time == 12){
            return 12 + "pm";
        }
        else {
            return time - 12 + "pm";
        }
    }

    /**
     * Gets ALL speakers in the system's schedules
     * @author Priyanka
     * @param DM DateManager
     * @return hashmap(K,V), where K: String with Speaker username, event name, event time. V: speaker ID
     */
    public LinkedHashMap<String, Integer> getSpeakerSchedules(DateManager DM){
        LinkedHashMap<String, Integer> Schedule = new LinkedHashMap<>();
        for (Speaker speaker: this.speakers) {
            LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> speakerSchedule =
                    getSpeakerSchedule(speaker.getID());
            String speakerName = speaker.getUsername();
            StringBuilder info =  new StringBuilder(speakerName + "'s Schedule:" + "\n");
            for(Map.Entry<Integer, LinkedHashMap<Integer, Integer>> speakerInfo: speakerSchedule.entrySet()) {
                int thisDate = speakerInfo.getKey();
                info.append(DM.dateToString(thisDate)).append("\n");
                for(Map.Entry<Integer, Integer> oneDayInfo: speakerInfo.getValue().entrySet()) {
                    info.append("    ").append(toTime(oneDayInfo.getKey()));
                    if (oneDayInfo.getValue() == null) {
                        info.append(": Available \n");
                    } else {
                        info.append(": Booked \n");
                    }
                }
            }
            Schedule.put(info.toString(), speaker.getID());
        }
        return Schedule;
    }

    /**
     * Updates the speaker's empty schedule
     * @param day day to add
     */
    public void updateEmptySchedule(Integer day){
        for (Speaker speaker : speakers){
            speaker.addDate(day);
        }
    }

    /**
     * Check if the speaker with the given ID is free at the given date and time
     *
     * @param speakerID : ID for the speaker who's availability is being checked
     * @param time : the time at which availability of the given speaker is being checked
     * @param date : the date at which availability of the given speaker is being checked
     * @return true iff the speaker is not scheduled for any other event at the given time
     */
    public boolean checkSpeakerIsFree(Integer speakerID, int time, int date) {
        Speaker speaker = getSpeakerObject(speakerID);
        if (speaker == null) {
            return false;
        }
        return speaker.validateTime(time, date);
    }

    /**
     * For multiple-speaker event (panel)
     * Check if the speakers with the given ID are all free at the given time
     * @author Priyanka
     * @param speakerIDs : ID for the speakers whose availabilities are being checked
     * @param time : Time at which availability of the given speaker is being checked
     * @param date : the date at which the availability of the given speaker is being checked
     * @return true iff the speakers are not scheduled for any other events at the given times
     */
    public boolean checkSpeakersAreFree(ArrayList<Integer> speakerIDs, int time, int date) {
        for (int speakerID : speakerIDs) {
            if (!checkSpeakerIsFree(speakerID, time, date)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Event rescheduling functionality
     * @param spks : IDs of speakers at event
     * @param eventID : ID of event to reschedule
     * @param day : the date at which the event is being rescheduled to
     * @param startTime start time of event
     * @param endTime end time of event
     */
    public void rescheduleEvent(ArrayList<Integer> spks, int eventID, int startTime, int endTime, int day) {
        deleteEvent(eventID);
        if (endTime - startTime > 1) {
            for (Integer spkid : spks) {
                Speaker spk = getSpeakerObject(spkid);
                if (spk != null) {
                    spk.addPanelEvent(eventID, startTime, endTime, day);
                }
            }
        }
        for (Integer spkid : spks) {
            Speaker spk = getSpeakerObject(spkid);
            if (spk != null) {
                spk.addOneSpeakerEvent(eventID, startTime, endTime, day);
            }
        }
    }
}
