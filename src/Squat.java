import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents a squat exercise activity with specific attributes like reps and sets.
 */
public class Squat extends Bodyweight implements Serializable {

    private int RPE; // rate of perceived exertion
    /**
     * Default constructor that initializes the squat activity with default values.
     */
    public Squat() {
        super();
        this.RPE = 0; // 1 to 10
    }

    /**
     * Constructor that initializes a Squat activity with specific details.
     *
     * @param activityID Unique identifier for the activity.
     * @param type Type of the activity.
     * @param date Date of the activity.
     * @param duration Duration of the activity in minutes.
     * @param reps Number of repetitions per set.
     * @param sets Number of sets.
     */
    public Squat(String activityID, String type, LocalDate date, int duration,int reps, int sets, boolean isHard, int RPE) {
        super(activityID, type, date, duration, reps, sets, isHard);
        if (reps < 0 || sets < 0) {
            throw new IllegalArgumentException("Reps and sets must be non-negative.");
        }
        this.RPE = RPE;
    }

    /**
     * Copy constructor to create a new instance as a deep copy of another Squat instance.
     *
     * @param other The other Squat instance to copy.
     */
    public Squat(Squat other) {
        super(other);
        this.RPE = other.getRPE();
    }

    // ----------------------------Getter and setter methods----------------------------------------------------------

    public int getRPE() {
        return this.RPE;
    }

    public void setRPE(int RPE) {
        this.RPE = RPE;
    }


    // ----------------------------END OF ------Getter and setter methods----------------------------------------------------------

    public int calculateMETSquat() {
        int MET = 2;
        if(this.RPE >=1 && this.RPE < 5) {
            MET = 4;
        }
        if(this.RPE >=5 && this.RPE < 7) {
            MET = 6;
        }
        if(this.RPE >=7) {
            MET = 8;
        }
        return MET;
    }

    @Override
    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = calculateMETSquat() * getDuration() * user.getWeight()*user.caloriesFactor()*1;
        return calories/70;
    }

    @Override
    public Activity clone() {
        return new Squat(this);
    }

    @Override
    public String toString() {
        return "Squat: \n" +
                super.toString()+
                "\nRPE: " + this.RPE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Squat squat = (Squat) o;
        return this.RPE == squat.getRPE();
    }
}

