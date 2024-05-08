import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class BicepCurl extends Activity implements Serializable {

    private int reps;
    private int sets;
    private double weight;

    public BicepCurl() {
        super();
        this.reps = 0;
        this.sets = 0;
        this.weight = 0;
    }

    public BicepCurl(int reps, int sets, int weight) {
        super();
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
    }

    public BicepCurl(BenchPress other) {
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
    public BicepCurl clone() {
        return new BicepCurl(this);
    }

    @Override
    public String toString() {
        return "Bicep Curl{" +
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
        BicepCurl curl = (BicepCurl) o;
        return this.sets == curl.getSets()
                && this.reps == curl.getReps()
                && Double.compare(this.weight, curl.getWeight()) == 0;
    }
}

