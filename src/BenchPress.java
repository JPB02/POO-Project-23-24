import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class BenchPress extends Activity implements Serializable {

    private int reps;
    private int sets;
    private double weight;

    public BenchPress() {
        super();
        this.reps = 0;
        this.sets = 0;
        this.weight = 0;
    }

    public BenchPress(int reps, int sets, int weight) {
        super();
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
    }

    public BenchPress(BenchPress other) {
        super(other);
        this.reps = other.getReps();
        this.sets = other.getSets();
        this.weight = other.getSets();
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public double calories() {
        long age =  ChronoUnit.YEARS.between(getUser().getDateOfBirth(),LocalDate.now());
        double calories = ((reps/10)*sets*age)/2; // FAZER FÒRMULA
        return calories;
    }

    @Override
    public BenchPress clone() {
        return new BenchPress(this);
    }

    @Override
    public String toString() {
        return "Bench Press{" +
                super.toString()+
                ", sets=" + sets +
                ", reps=" + reps +
                ", weight=" + weight +
                ", calories=" + calories() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BenchPress bench = (BenchPress) o;
        return this.sets == bench.getSets()
                && this.reps == bench.getReps()
                && Double.compare(this.weight, bench.getWeight()) == 0;
    }
}

