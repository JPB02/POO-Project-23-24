import java.io.Serializable;
import java.time.LocalDate;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
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

    // Variavel usada para incrementar o ID
    private static int lastAssignedActivityID = 0;

    private String activityID;
    private String activityType;
    private LocalDate date;
    private int duration;

    /**
     * Gera um ID único de atividade que é incrementado a partir do último
     * @return ID (String)
     */
    private String generateNextActivityID() {
        lastAssignedActivityID++; // Increment the last assigned activity ID
        return String.valueOf(lastAssignedActivityID);
    }

    /**
     * Construtor por omissão que instancia uma atividade com valores default
     */
    public Activity() {
        this.activityID = generateNextActivityID();
        this.activityType = "";
        this.date = LocalDate.EPOCH;
        this.duration = 0;
    }

    /**
     * Construtor Parametrizado
     * @param activityType Tipo da atividade
     * @param date Data da atividade
     * @param duration Duração em minutos de uma atividade
     */
    public Activity(String activityID, String activityType, LocalDate date, int duration) {
        this.activityID = activityID;
        this.activityType = activityType;
        this.date = date;
        this.duration = duration;
    }

    /**
     * Construtor cópia
     * @param other recebe uma atividade
     */
    public Activity(Activity other) {
        if (other == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        this.activityID = other.getActivityID();
        this.activityType = other.getActivityType();
        this.date = other.getDate();
        this.duration = other.getDuration();
    }


    // ----------------------------Getter and setter methods----------------------------------------------------------
    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        if (activityID == null) {
            throw new NullPointerException("Activity ID cannot be null");
        }
        this.activityID = activityID;
    }


    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        if (activityType == null || activityType.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity type cannot be null or empty");
        }
        this.activityType = activityType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            throw new NullPointerException("Date cannot be null");
        }
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
        this.duration = duration;
    }

// ----------------------------END OF ------Getter and setter methods----------------------------------------------------------

    @Override
    public String toString() {
        return "Atividade{" +
                "activity ID='" + activityID + '\'' +
                ", activity type='" + activityType + '\'' +
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
                && this.activityType.equals(activity.getActivityType())
                && this.date.equals(activity.getDate());
    }

    //Métodos que são implementados nas subclasses
    public abstract double calories(User user);
    public  abstract  Activity clone( );

}

