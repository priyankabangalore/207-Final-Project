/*
@layer: Entity
 */


//Request entity, can be tagged as either pending or addressed.
//Contains the following:
//dietary requirements
//accessibility requirements: includes service animals(add extra seat), wheelchairs(each room has set number of seats),

package Entities;

public class Request {
    private final String req;
    private boolean pending;
    private final int ID;
    private final int userID;

    /**
     * Contains Arraylist containing diet requirements and accessibility requirements of User.
     * Pending status is true until Organizer changes it to false, to mean "addressed".
     * @param req the request
     * @param ID the ID for this request
     * @param userID ID of the user who has made the request
     */
    public Request(String req, int ID, int userID){
        this.req = req;
        pending = true;
        this.ID = ID;
        this.userID = userID;
    }

    /**
     * Return the ID for this request
     * @return the ID for this request
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Return the ID for the user who made the request
     * @return ID for user who made the request
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Change the status of this request as specified
     * @param newStatus true if the request has not been addressed,
     *                  false if the request has been addressed
     */
    public void changePending(boolean newStatus) {
        this.pending = newStatus;
    }

    /**
     * Return all the requests stored in this request
     * @return request in string form
     */
    public String getReq(){
        return this.req;
    }


    /**
     * Return whether this request is pending or not
     * @return true iff the request has not been addressed
     */
    public boolean getIsPending(){
        return this.pending;
    }

}