/*
@layer: Entity
 */

package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private final String content;
    private final int senderID;
    private final LocalDateTime time;
    private final int messageID;
    private boolean messageRead; //indicates whether the message has been read or not
    private boolean archived = false;

    /**
     * Constructs a Message object.
     * @param content The text contained in the Message body
     * @param senderID The ID of the User sending the message
     *                 (the author)
     * @param messageID The unique ID for this message
     */
    public Message(String content, int senderID, int messageID) {
        this.content = content;
        this.senderID = senderID;
        this.time = LocalDateTime.now();
        this.messageID = messageID;
        this.messageRead = false;
    }

    /**
     * Gets the text contained in message body.
     * @return content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the ID of the User who authored the message.
     * @return sender, the author's ID.
     */
    public int getSenderID() {
        return senderID;
    }

    /**
     * Returns a String representation of the time of day
     * when the message was sent.
     * @return String at which message is created.
     */
    public String getTime() {
        String hoursAndMinutes = time.getHour() + ":" + time.getMinute();
        return hoursAndMinutes;
    }

    /**
     * Gets the ID of the message
     * @return ID of the message
     */
    public int getMessageID(){
        return this.messageID;
    }

    /**
     * Return whether this message has been read
     * @return true iff the message has been read
     */
    public boolean getMessageRead() { return this.messageRead;}

    /**
     * Return whether or not the message is archived
     * @return true iff the message is archived
     */
    public boolean getMessageArchived() {
        return this.archived;
    }

    /**
     * Change whether this message has been read
     * @param readStatus true if the message has been read, false if the message is being marked as unread
     */
    public void setMessageRead(boolean readStatus) { this.messageRead = readStatus;}

    /**
     * Change the status of whether or not this message has been archived
     * @param archiveStatus true if message is being archived
     *                      false if message is being unarchived
     */
    public void setArchived(boolean archiveStatus) {
        this.archived = archiveStatus;
    }
}

