package Controllers;

import UseCases.AttendeeManager;
import UseCases.RequestManager;
import UseCases.SpeakerManager;
import UseCases.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestController {
    private final AttendeeManager am;
    private final RequestManager rm;
    private final SpeakerManager sm;
    private final UserManager um;

    public RequestController(AttendeeManager am, RequestManager rm, SpeakerManager sm, UserManager um) {
        this.am = am;
        this.rm = rm;
        this.sm = sm;
        this.um = um;
    }

    // list of possible requests the user can make, given requests that this user has already made
    /**
     * Return the possible requests that the currently logged in user can make,
     * given the requests that this user has already made
     * @param LC LoginController
     * @return list of requests that the currently logged in user can make
     */
    public HashMap<String, Integer> getUserPossibleRequests(LoginController LC) {
        Integer userID = LC.getCurrentUserID();
        int permission = um.getPermissionLvl(userID);
        if (permission == 0) {
            return toMap(rm.getPossibleRequests(am, userID)); // requests if the user is a attendee
            }
        else if (permission == 1) {
            return toMap(rm.getPossibleRequests(sm, userID)); // requests if the user is a speaker
            }
        return null;
    }

    /**
     * Return the information for all requests that the currently logged in user
     * has made
     * @param LC LoginController
     * @return hashmap of request information for currently logged in user where,
     *         K is the ID of the request
     *         V is the request's information and its pending status
     */
    public HashMap<String, Integer> getUserRequestsInformation(LoginController LC) {
        HashMap<String,Integer> temp = new HashMap<>();
        int userID = LC.getCurrentUserID();
        int permission = um.getPermissionLvl(userID);
        if (permission == 0) {
            return rm.getAttendeeRequests(userID, am);
        }
        else if (permission == 1) {
            return rm.getSpeakerRequests(userID, sm);
        }
        return new HashMap<>();
    }

    /**
     * Create a new request for the user that is currently logged in
     * @param request String representation of the request
     * @param LC LoginController
     */
    public void createRequest(String request, LoginController LC) {
        Integer userID = LC.getCurrentUserID();
        int permission = um.getPermissionLvl(userID);
        int ID = rm.createRequest(request, userID);
        if (permission == 0) {
            am.addRequest(userID, ID);
        }
        else if (permission == 1) {
            sm.addRequest(userID, ID);
        }
    }

    /**
     * Cancel the request with the given ID, given that it exists
     * @param requestID ID of the request being cancelled
     */
    public void cancelRequest(int requestID, LoginController LC) {
        int userID = LC.getCurrentUserID();
        int permission = um.getPermissionLvl(userID);
        rm.deleteRequest(requestID);
        if (permission == 0) {
            am.removeRequest(userID, requestID);
        }
        else {
            sm.removeRequest(userID, requestID);
        }
    }

    /**
     * Get all requests in the system that have been addressed
     * @return hashmap where,
     *         V is the ID of the request and
     *         K is the string rep of the request
     */
    public HashMap<String, Integer> getAllAddressedRequests() {
        HashMap<Integer, String> addressedRequests = rm.getRequestsByStatusHashMap(false);
        return flipMap(addressedRequests);
    }

    /**
     * Get all requests in the system that are pending
     * @return hashmap where,
     *         V is the ID of the request and
     *         K is the string rep of the request
     */
    public HashMap<String, Integer> getAllPendingRequests() {
        HashMap<Integer, String> pendingRequests = rm.getRequestsByStatusHashMap(true);
        return flipMap(pendingRequests);
    }

    /**
     * Mark the request with the given ID as addressed
     * @param requestID ID of the request
     */
    public void markAddressed(int requestID) {
        rm.markAddressed(requestID);
    }

    /**
     * Mark the request with the given ID as pending
     * @param requestID ID of the request
     */
    public void markPending(int requestID) {
        rm.markPending(requestID);
    }

    /**
     * Flips a hashmap, with strings becoming keys
     * @param toFlip the hashmap to be flipped
     * @return hashmap where,
     *        K the initial V
     *        V is the initial K
     */
    public HashMap<String, Integer> flipMap(HashMap<Integer, String> toFlip){
        HashMap<String, Integer> flipped = new HashMap<>();
        for (int key : toFlip.keySet()){
            flipped.put(toFlip.get(key), key);
        }
        return flipped;
    }

    /**
     * Helper method to produce a hashmap from a list of possible requests (list of strings)
     * @param list list of strings
     * @return hashmap where,
     *         K: list of strings (or in this case, list of possible requests)
     *         V: the integer 1
     */
    private HashMap<String, Integer> toMap(ArrayList<String> list){
        HashMap<String, Integer> newMap = new HashMap<>();
        for (String str : list){
            newMap.put(str, 1);
        }
        return newMap;
    }
}