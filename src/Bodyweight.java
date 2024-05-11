import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents a squat exercise activity with specific attributes like reps and sets.
 */
public class Bodyweight extends Activity implements Serializable {

    private int reps;
    private int sets;

    /**
     * Default constructor that initializes the squat activity with default values.
     */
    public Bodyweight() {
        super();
        this.reps = 0;
        this.sets = 0;
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
    public Bodyweight(String activityID, String type, LocalDate date, int duration,int reps, int sets, boolean isHard) {
        super(activityID, type, date, duration, isHard);
        if (reps < 0 || sets < 0) {
            throw new IllegalArgumentException("Reps and sets must be non-negative.");
        }
        this.reps = reps;
        this.sets = sets;
    }

    /**
     * Copy constructor to create a new instance as a deep copy of another Squat instance.
     *
     * @param other The other Squat instance to copy.
     */
    public Bodyweight(Bodyweight other) {
        super(other);
        this.reps = other.getReps();
        this.sets = other.getSets();
    }

    // ----------------------------Getter and setter methods----------------------------------------------------------

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        if (reps < 0) {
            throw new IllegalArgumentException("Reps cannot be negative.");
        }
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        if (sets < 0) {
            throw new IllegalArgumentException("Sets cannot be negative.");
        }
        this.sets = sets;
    }

    // ----------------------------END OF ------Getter and setter methods----------------------------------------------------------

    public int calculateMETBodyweight() {
        int MET = 2;
        if(this.sets >= 1 && this.sets < 3) {
            MET = 4;
        }
        if(this.sets >=3) {
            MET = 6;
        }
        return MET;
    }

    @Override
    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = calculateMETBodyweight() * getDuration() * user.getWeight()*user.caloriesFactor()*1;
        return calories/70;
    }

    @Override
    public Activity clone() {
        return new Bodyweight(this);
    }

    @Override
    public String toString() {
        return "Squat: \n" +
                super.toString()+
                "\nSets: " + this.sets +
                "\nReps: " + this.reps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bodyweight bodyweight = (Bodyweight) o;
        return this.sets == bodyweight.getSets()
                && this.reps == bodyweight.getReps();
    }
}

