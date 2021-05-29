/*
@layer: Use Case
@collaborators: Date Entity
 */

package UseCases;
import Entities.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DateManager {

    private final ArrayList<Date> dates;

    /**
     * Constructor for date manager
     *
     * @param days the days that the conference runs
     * @author: steph
     */
    public DateManager(ArrayList<Date> days) {
        this.dates = days;
    }

    /**
     * Get all the dates stored here
     *
     * @return a list of Dates
     */
    public ArrayList<Date> getDates() {
        return dates;
    }

    /**
     * Creates a new date
     *
     * @param date the date to be added
     * @author: steph
     */
    public boolean NewDate(int date) {
        if (validateDateNotExist(date)) {
            Date newDay = new Date(date);
            dates.add(newDay);
            return true;
        }
        return false;
    }

    /**
     * Get the Date object
     *
     * @param day the date in question
     * @return the date object
     * @author: steph
     */
    public Date getDateObject(int day) {
        for (Date d : dates) {
            if (d.getDate() == day) {
                return d;
            }
        }
        return null;
    }

    /**
     * Add an event to a date
     *
     * @param day   the date an event takes place at
     * @param event the event that is taking place
     * @author: steph
     */
    public void addEvent(int day, int event) {
        if (validateEventNotSet(event)) {
            Date d = getDateObject(day);
            d.addEvent(event);
        }
    }

    /**
     * Reschedule events for different days
     *
     * @param day   the day the event is being scheduled to
     * @param event the event that is being rescheduled
     * @author: steph
     */
    public void rescheduleEvent(int day, int event) {
        if (!validateEventNotSet(event)) {
            Date oldDate = getDateOfEvent(event);
            Date newDate = getDateObject(day);
            newDate.addEvent(event);
            oldDate.removeEvent(event);
        }
    }

    /**
     * Delete an event
     * @param eventID the event ID
     */
    public void deleteEvent(Integer eventID) {
        for (Date date : dates) {
            date.removeEvent(eventID);
        }
    }

    /**
     * Validate that the event is not already taking place on a different day
     *
     * @param eventID The event being looked at
     * @return True if the event is not already set to a room
     * @author: steph
     */
    public boolean validateEventNotSet(int eventID) {
        for (Date d : dates) {
            if (d.getEventIds().contains(eventID)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Returns True is the date does not exist yet, False if it does
     *
     * @param date the date being looked at
     * @return Whether or not the date exists
     * @author: steph
     */
    public boolean validateDateNotExist(int date) {
        for (Date d : dates) {
            if (d.getDate() == date) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the date of the event using the event info
     *
     * @param event the event ID of the event being looked for
     * @return the Date object of the date of the given event
     * @author: steph
     */
    public Date getDateOfEvent(int event) {
        for (Date d : dates) {
            if (d.getEventIds().contains(event)) {
                return d;
            }
        }
        return null;
    }

    /**
     * Delete the last day from the conference. WIll only delete the date, if there are no events scheduled
     * @return True if the event has been deleted, False if it has not
     * @author: Steph
     */
    public boolean deleteDay() {
        if (dates.size() > 0) {
            dates.remove(dates.get(dates.size() -1));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get a Hashmap of all the dates that have already been created for the conference
     *
     * @return hashmap such that,
     * K: the date in string form
     * V: the date in integer form
     * @author: Steph
     */
    public LinkedHashMap<String, Integer> getDatesDic() {
        LinkedHashMap<String, Integer> dict = new LinkedHashMap<>();
        for (Date d : dates) {
            String info = dateToString(d.getDate());
            dict.put(info, d.getDate());
        }
        return dict;
    }

    /**
     * Convert the 4 digit date to a string date.
     *
     * @param date the date
     * @return A string with the date (ex: September 14)
     * @author: steph
     */
    public String dateToString(int date) {
        String day = Integer.toString(date);
        if (day.length() == 3) {
            day = "0" + day;
        }
        String mont = day.substring(0,2);
        String dat = day.substring(2,4);
        switch (mont) {
            case "01":
                mont = "January ";
                break;
            case "02":
                mont = "February ";
                break;
            case "03":
                mont = "March ";
                break;
            case "04":
                mont = "April ";
                break;
            case "05":
                mont = "May ";
                break;
            case "06":
                mont = "June ";
                break;
            case "07":
                mont = "July ";
                break;
            case "08":
                mont = "August ";
                break;
            case "09":
                mont = "September ";
                break;
            case "10":
                mont = "October ";
                break;
            case "11":
                mont = "November ";
                break;
            case "12":
                mont = "December ";
                break;
            default:
                break;

        }
        return mont + dat;
    }

    /**
     * Creates the next day of the event
     * @return the int version of the new date that has been added
     */
    public int nextDay() {
        Date lastDay = dates.get(dates.size() - 1);
        int nextDate = createNextDay(lastDay.getDate());
        NewDate(nextDate);
        return nextDate;
    }

    /**
     * Creates the next day of the conference
     * @param lastDate the current last day of the conference
     * @return the integer of the new date of the conference
     */
    public int createNextDay(int lastDate) {
        String day = Integer.toString(lastDate);
        if (day.length() == 3) {
            day = "0" + day;
        }

        int oldMont = Integer.parseInt(day.substring(0,2));
        int oldDay = Integer.parseInt(day.substring(2,4));
        int newMonth = oldMont;
        int newDay = oldDay +1;

        if (oldMont == 1 || oldMont == 3 || oldMont == 5 || oldMont == 7 || oldMont == 8 || oldMont == 10 || oldMont == 12) {
            if (newDay == 32) {
                newDay = 1;
                newMonth = oldMont + 1;

            }
        } else if (newMonth == 2 && newDay == 29) {
            newMonth = 3;
            newDay = 1;
        } else {
            if (newDay == 31) {
                newMonth = oldMont + 1;
                newDay = 1;
            }
        }
        if (newMonth == 13) {
            newMonth = 1;
        }
        String m = Integer.toString(newMonth);
        String d = Integer.toString(newDay);
        if (m.length() == 1) {
            m = "0" + m;
        }
        if (d.length() == 1) {
            d = "0" +  d;
        }
        String stringDate = m + d;
        return Integer.parseInt(stringDate);
    }
}