/*
@layer: Gateway
 */

package Gateways;

import Entities.*;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class UserFile implements FileInteractable {

    private final String attendeePath = "file/attendee.ser";
    private final String speakerPath = "file/speaker.ser";
    private final String organizerPath = "file/organizer.ser";
    private final String adminUserPath = "file/adminUser.ser";

    private final String speakerIDPath = "file/speakerID.ser";
    private final String userIDPath = "file/userID.ser";


    /**
     * Save all attendees
     * Should **only** be use when program shutting down
     * @param attendees list of Attendees to be saved
     */
    public void saveAttendees(ArrayList<Attendee> attendees) {
        save(attendees, attendeePath);
    }

    /**
     * save all speaker
     * Should **only** be use when program shutting down
     * @param speakers list of speakers to be saved
     */
    public void saveSpeakers(ArrayList<Speaker> speakers) {
        save(speakers, speakerPath);
    }

    /**
     * save all organizers
     * Should **only** be use when program shutting down
     * @param organizers list of organizers to be saved
     */
    public void saveOrganizers(ArrayList<Organizer> organizers) {
        save(organizers, organizerPath);
    }

    /**
     * load all previously saved attendees
     * @return list of attendees
     */
    public ArrayList<Attendee> loadAttendees() {
        return (ArrayList<Attendee>) loadList(attendeePath);
    }

    /**
     * load all previously saved speakers
     * @return list of speakers
     */
    public ArrayList<Speaker> loadSpeakers() {
        return (ArrayList<Speaker>) loadList(speakerPath);
    }

    /**
     * load all previously saved organizers
     * @return list of organizers
     */
    public ArrayList<Organizer> loadOrganizers() {
        return (ArrayList<Organizer>) loadList(organizerPath);
    }

    /**
     * save all admins
     * Should **only** be use when program shutting down
     * @param adminUsers list of admins to be saved
     */
    public void saveAdminUser(ArrayList<AdminUser> adminUsers) {
        save(adminUsers, adminUserPath);
    }

    /**
     * load all previously saved adminUser
     * @return list of adminUser
     */
    public ArrayList<AdminUser> loadAdminUser() {
        return (ArrayList<AdminUser>) loadList(adminUserPath);
    }

    /**
     * load all previously saved Users
     * @return list of Users
     */
    public ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.addAll(this.loadAttendees());
        users.addAll(this.loadSpeakers());
        users.addAll(this.loadOrganizers());
        users.addAll(this.loadAdminUser());
        return users;
    }

    /**
     * save all UserIDs
     * Should **only** be use when program shutting down
     * @param userIDs list of UserIDs to be saved
     */
    public void saveUserIDs(ArrayList<Integer> userIDs) {
        save(userIDs, userIDPath);
    }

    /**
     * load all previously saved userIDs
     * @return list of userIDs
     */
    public ArrayList<Integer> loadUserIDs() {
        return (ArrayList<Integer>) loadList(userIDPath);
    }

    /**
     * save all speakerIDs
     * Should **only** be use when program shutting down
     * @param speakerIDs list of speakerIDs to be saved
     */
    public void saveSpeakerIDs(ArrayList<Integer> speakerIDs) {
        save(speakerIDs, speakerIDPath);
    }

    /**
     * load all previously saved speakerIDs
     * @return list of speakerIDs
     */
    public ArrayList<Integer> loadSpeakerIDs() {
        return (ArrayList<Integer>) loadList(speakerIDPath);
    }

}
