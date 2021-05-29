/*
@layer: Use Case
@collaborators: Request Entity, DietaryRequest Entity, AccessibilityRequest Entity
 */

package UseCases;

import Entities.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RequestManager {
    private final ArrayList<Request> requests;
    private final ArrayList<Integer> requestIDs;
    private final ArrayList<String> dietList = new ArrayList<>(Arrays.asList("Vegetarian", "Halal", "Kosher",
        "Gluten-Free"));
    private final ArrayList<String> accessibilityList = new ArrayList<>(Arrays.asList("WheelChair", "Service Animal"));

    /**
     * Constructor for RequestManager
     * @param requestList list of requests
     * @param requestIDs list of request IDs
     */
    public RequestManager(ArrayList<Request> requestList, ArrayList<Integer> requestIDs) {
        this.requests = requestList;
        this.requestIDs = requestIDs;
    }

    // getters start
    /**
     * Get the list of all requests made.
     * @return list of all requests made in the system
     */
    public ArrayList<Request> getRequestList() {
        return this.requests;
    }

    /**
     * Get the list of dietary requests that can be made by the user
     * @return list of dietary requests
     */
    public ArrayList<String> getDietList(){
        return this.dietList;
    }

    /**
     * Get the list of accessibility requests that can be made by the user
     * @return list of accessibility requests
     */
    public ArrayList<String> getAccessibilityList(){
        return this.accessibilityList;
    }

    /**
     * Get the list of IDs of all requests made.
     * @return list of all request IDs in the system
     */
    public ArrayList<Integer> getRequestIDs() {
        return this.requestIDs;
    }

    /**
     * Return the request object
     * @param requestID ID of the request
     * @return Request object with given ID, iff it exists, otherwise return null
     */
    private Request getRequest(int requestID) {
        for (Request request: this.requests) {
            if (request.getID() == requestID) {
                return request;
            }
        }
        return null;
    }

    /**
     * Return whether this request is pending or not
     * @param requestID ID of the request
     * @return true iff request has been addressed, false otherwise
     */
    public boolean getRequestStatus(int requestID) {
        Request request = getRequest(requestID);
        if (request == null) {
            return false;
        }
        return request.getIsPending();
    }

    /**
     * Return all requests made according to the status requested
     * @param requiredStatus true if pending requests required
     *                       false if addressed requests required
     * @return Array list of required requests
     */
    private ArrayList<Request> getRequestsByStatus(boolean requiredStatus) {
        ArrayList<Request> addressedRequests = new ArrayList<>();
        for (Request request: this.requests) {
            if (request.getIsPending() == requiredStatus) {
                addressedRequests.add(request);
            }
        }
        return addressedRequests;
    }

    /**
     * Get a hashmap of information pertaining to all required requests
     * @param requiredStatus true if pending requests are required
     *                       false if addressed requests are required
     * @return Hashmap<K, V>, where
     *         K is the ID of the request and
     *         V is the string representation of that particular request
     */
    public HashMap<Integer, String> getRequestsByStatusHashMap(boolean requiredStatus) {
        HashMap<Integer, String> requests = new HashMap<>();
        ArrayList<Request> requiredRequests = getRequestsByStatus(requiredStatus);
        for (Request request: requiredRequests) {
            Integer reqID = request.getID();
            String req = request.getReq();
            requests.put(reqID, req);
        }
        return requests;
    }

    /**
     * Get the string name for this request
     * @param requestID ID of this request
     * @return String representation for this request, for e.g. "Halal"
     */
    public String getRequestName(int requestID) {
        Request request = getRequest(requestID);
        if (request == null) {
            return "This request does not exist.";
        }
        return request.getReq();
    }

    /**
     * Return the list of possible requests that the user can make, given the requests
     * that this user has already made
     * @param attendeeManager AttendeeManager
     * @param userID ID of the user
     * @return list of requests that this user can make
     */
    public ArrayList<String> getPossibleRequests(AttendeeManager attendeeManager, int userID) {
        ArrayList<String> possibleRequests = new ArrayList<>();
        ArrayList<Integer> userRequests = attendeeManager.getUserRequests(userID);
        for (Integer reqID: userRequests) {
            String reqName = getRequestName(reqID);
            if (!this.dietList.contains(reqName) || this.accessibilityList.contains(reqName)) {
                possibleRequests.add(reqName);
            }
        }
        return possibleRequests;
    }

    /**
     * Return the list of possible requests that the user can make, given the requests
     * that this user has already made
     * @param speakerManager SpeakerManager
     * @param userID ID of the user
     * @return list of requests that this user can make
     */
    public ArrayList<String> getPossibleRequests(SpeakerManager speakerManager, int userID) {
        ArrayList<String> possibleRequests = new ArrayList<>();
        ArrayList<Integer> userRequests = speakerManager.getUserRequests(userID);
        for (Integer reqID: userRequests) {
            String reqName = getRequestName(reqID);
            if (!this.dietList.contains(reqName) || this.accessibilityList.contains(reqName)) {
                possibleRequests.add(reqName);
            }
        }
        return possibleRequests;
    }

    /**
     * Return a hashmap of the various user requests and their pending status
     * @param userID ID of the user
     * @param am AttendeeManager
     * @return hashmap where
     *         K is the request string representation, as well as its pending status
     *         V is the request ID and
     */
    public HashMap<String, Integer> getAttendeeRequests(int userID, AttendeeManager am) {
        ArrayList<Integer> userRequests = am.getUserRequests(userID);
        HashMap<String, Integer> userReqInfo = new HashMap<>();
        for (Integer reqID: userRequests) {
            String reqName = getRequestName(reqID);
            if (getRequestStatus(reqID)) {
                reqName += ", (Pending)";
            }
            else {
                reqName += ", (Addressed)";
            }
            userReqInfo.put(reqName, reqID);
        }
        return userReqInfo;
    }

    /**
     * Return a hashmap of the various user requests and their pending status
     * @param userID ID of the user
     * @param sm SpeakerManager
     * @return hashmap where
     *         K is the request string representation, as well as its pending status
     *         V is the request ID and
     */
    public HashMap<String, Integer> getSpeakerRequests(int userID, SpeakerManager sm) {
        ArrayList<Integer> userRequests = sm.getUserRequests(userID);
        HashMap<String, Integer> userReqInfo = new HashMap<>();
        for (Integer reqID: userRequests) {
            String reqName = getRequestName(reqID);
            if (getRequestStatus(reqID)) {
                reqName += ", (Pending)";
            }
            else {
                reqName += ", (Addressed)";
            }
            userReqInfo.put(reqName, reqID);
        }
        return userReqInfo;
    }
    // end getters

    /**
     * Mark the request with the given ID as having been addressed
     * @param requestID ID of the request
     */
    public void markAddressed(int requestID) {
        Request request = getRequest(requestID);
        if (request != null) {
            request.changePending(false);
        }
    }

    /**
     * Mark the request with the given ID as pending
     * @param requestID ID of the request
     */
    public void markPending(int requestID) {
        Request request = getRequest(requestID);
        if (request != null) {
            request.changePending(true);
        }
    }

    /**
     * Generate a unique ID for this request
     * @return the unique ID
     */
    public int generateID() {
        int ID = this.requestIDs.size() + 1;
        this.requestIDs.add(ID);
        return ID;
    }

    /**
     * Create a new request
     * @param req the request being made
     * @param userID ID of the user who has made the request
     * @return the ID of this newly formed request
     */
    public int createRequest(String req, int userID) {
        int ID = generateID();
        Request request = new Request(req, ID, userID);
        this.requests.add(request);
        return request.getID();
    }

    /**
     * Delete the given request
     * @param requestID ID of the request
     */
    public void deleteRequest(int requestID) {
        Request request = getRequest(requestID);
        if (request != null) {
            this.requests.remove(requestID);
        }
    }
}
