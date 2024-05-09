import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Squat extends Activity implements Serializable {

    private int reps;
    private int sets;

    public Squat() {
        super();
        this.reps = 0;
        this.sets = 0;
    }

    public Squat(String activityID, String type, LocalDate date, int duration,int reps, int sets) {
        super(activityID, type, date, duration);
        this.reps = reps;
        this.sets = sets;
    }

    public Squat(Squat other) {
        super(other);
        this.reps = other.getReps();
        this.sets = other.getSets();
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    @Override
    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = user.caloriesFactor()*1; // FAZER FÒRMULA
        return calories;
    }

    @Override
    public Activity clone() {
        return new Squat(this);
    }

    @Override
    public String toString() {
        return "Squat: \n" +
                super.toString()+
                "\nSets: " + sets +
                "\nReps: " + reps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Squat squat = (Squat) o;
        return this.sets == squat.getSets()
                && this.reps == squat.getReps();
    }
}

