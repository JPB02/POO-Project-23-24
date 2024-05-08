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
    private String description;
    private LocalDate date;
    private int duration;
    private User user;
    private double calories;

    private String generateNextActivityID() {
        lastAssignedActivityID++; // Increment the last assigned activity ID
        return String.valueOf(lastAssignedActivityID);
    }

    public void addToUserActivities(User user) {
        Map<String, Activity> activities = user.getActivities();
        activities.put(this.getActivityID(), this);
        user.setActivities(activities);
    }

    public Activity() {
        this.activityID = generateNextActivityID();
        this.description = "";
        this.date = LocalDate.EPOCH;
        this.duration = 0;
        this.user = new User();
    }

    public Activity(String activityID, String description, LocalDate date, int duration) {
        this.activityID = activityID;
        this.description = description;
        this.date = date;
        this.duration = duration;
    }

    public Activity(Activity other) {
        this.activityID = other.getActivityID();
        this.description = other.getDescription();
        this.date = other.getDate();
        this.duration = other.getDuration();
        this.user = other.getUser();
        this.calories = other.calories;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Atividade{" +
                "activity ID='" + activityID + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", duration" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return this.duration == activity.getDuration()
                && this.activityID.equals(activity.getActivityID())
                && this.description.equals(activity.getDescription())
                && this.date.equals(activity.getDate());
    }

    public abstract double calories();
    public  abstract  Activity clone( );

}
