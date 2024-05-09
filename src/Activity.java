import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

public abstract class Activity implements Serializable {

    /*
    this will be an abstract class in order to create new types of activities without altering this class

    4 types of different activities:
        1i) distance & altitude (trail, mountain bike, road running...)
        2i) distance only (running(track), canoeing, ice-skating...)
        3i) repetitions and sets (crunches, stretches, push ups...)
        4i) repetitions and sets with weights (weight lifting, leg extensions...)

     information about activity:
        1i) calories burned (dependent on type of activity)
        2i) final elapsed time
     */

    private static int lastAssignedActivityID = 0; // Keep track of the last assigned activity ID


    private String activityID;
    private String activityType;
    private LocalDate date;
    private int duration;

    private String generateNextActivityID() {
        lastAssignedActivityID++; // Increment the last assigned activity ID
        return String.valueOf(lastAssignedActivityID);
    }

    public Activity() {
        this.activityID = generateNextActivityID();
        this.activityType = "";
        this.date = LocalDate.EPOCH;
        this.duration = 0;
    }

    public Activity(String activityID, String activityType, LocalDate date, int duration) {
        this.activityID = activityID;
        this.activityType = activityType;
        this.date = date;
        this.duration = duration;
    }

    public Activity(Activity other) {
        this.activityID = other.getActivityID();
        this.activityType = other.getActivityType();
        this.date = other.getDate();
        this.duration = other.getDuration();
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "Activity: \n" +
                "\nActivity ID='" + activityID +
                "\nActivity Type='" + activityType +
                "\nDate=" + date +
                "\nDuration" + duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return this.duration == activity.getDuration()
                && this.activityID.equals(activity.getActivityID())
                && this.activityType.equals(activity.getActivityType())
                && this.date.equals(activity.getDate());
    }

    public abstract double calories(User user);
    public  abstract  Activity clone( );

}
