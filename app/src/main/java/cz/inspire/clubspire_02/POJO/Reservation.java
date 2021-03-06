package cz.inspire.clubspire_02.POJO;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michal on 5/22/15.
 */
public class Reservation {
    private String instructorId;
    private String sportId;
    private String objectId;
    private String note;
    private int personCount;
    private long startTime;
    private long endTime;
    private int emailNotificationBeforeMinutes;
    private int smsNotificationBeforeMinutes;

    public String getInstructorId() {
        return instructorId;
    }

    public Reservation setInstructorId(String instructorId) {
        this.instructorId = instructorId;
        return this;
    }

    public String getSportId() {
        return sportId;
    }

    public Reservation setSportId(String sportId) {
        this.sportId = sportId;
        return this;
    }

    public String getObjectId() {
        return objectId;
    }

    public Reservation setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getNote() {
        return note;
    }

    public Reservation setNote(String note) {
        this.note = note;
        return this;
    }

    public int getPersonCount() {
        return personCount;
    }

    public Reservation setPersonCount(int personCount) {
        this.personCount = personCount;
        return this;
    }

    public long getStartTime() {
        return startTime;
    }

    public Reservation setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long getEndTime() {
        return endTime;
    }

    public Reservation setEndTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

    public int getEmailNotificationBeforeMinutes() {
        return emailNotificationBeforeMinutes;
    }

    public Reservation setEmailNotificationBeforeMinutes(int emailNotificationBeforeMinutes) {
        this.emailNotificationBeforeMinutes = emailNotificationBeforeMinutes;
        return this;
    }

    public int getSmsNotificationBeforeMinutes() {
        return smsNotificationBeforeMinutes;
    }

    public Reservation setSmsNotificationBeforeMinutes(int smsNotificationBeforeMinutes) {
        this.smsNotificationBeforeMinutes = smsNotificationBeforeMinutes;
        return this;
    }

}



