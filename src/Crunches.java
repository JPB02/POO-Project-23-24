import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Crunches extends Activity implements Serializable {

    private int reps;
    private int sets;

    public Crunches() {
        super();
        this.reps = 0;
        this.sets = 0;
    }

    public Crunches(int reps, int sets) {
        super();
        this.reps = reps;
        this.sets = sets;
    }

    public Crunches(Crunches other) {
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
    public double calories() {
        long age =  ChronoUnit.YEARS.between(getUser().getDateOfBirth(),LocalDate.now());
        double calories = ((reps/10)*sets*age)/2; // FAZER FÒRMULA
        return calories;
    }

    @Override
    public Crunches clone() {
        return new Crunches(this);
    }

    @Override
    public String toString() {
        return "Crunches{" +
                super.toString()+
                ", sets=" + sets +
                ", reps=" + reps +
                ", calories=" + calories() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Crunches crunches = (Crunches) o;
        return this.sets == crunches.getSets()
                && this.reps == crunches.getReps();
    }
}

