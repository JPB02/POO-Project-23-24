import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Stretches extends Activity implements Serializable {

    private int reps;
    private int sets;

    public Stretches() {
        super();
        this.reps = 0;
        this.sets = 0;
    }

    public Stretches(int reps, int sets) {
        super();
        this.reps = reps;
        this.sets = sets;
    }

    public Stretches(Stretches other) {
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
    public Stretches clone() {
        return new Stretches(this);
    }

    @Override
    public String toString() {
        return "Stretches{" +
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
        Stretches stretches = (Stretches) o;
        return this.sets == stretches.getSets()
                && this.reps == stretches.getReps();
    }
}

