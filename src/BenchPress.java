import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
/**
 * Representa a atividade de BenchPress com detalhes sobre o numero de sets
 *reps e peso levantado.
 */
public class BenchPress extends Activity implements Serializable {

    private int reps;
    private int sets;
    private double weight;
    /**
     * Construtor por omissao
     */
    public BenchPress() {
        super();
        this.reps = 0;
        this.sets = 0;
        this.weight = 0;
    }

    /**
     * Construtor parametrizado
     *
     * @param activityID ID da atividade
     * @param type Tipo da atividade
     * @param date Data da atividade
     * @param duration Duração da atividade
     * @param reps Numero de reps por set
     * @param sets Numero de sets realizados
     * @param weight Peso em kg.
     * @throws IllegalArgumentException se os valores forem negativos
     */
    public BenchPress(String activityID, String type, LocalDate date, int duration, int reps, int sets, double weight, boolean isHard) {
        super(activityID, type, date, duration, isHard);
        if (reps < 0 || sets < 0 || weight < 0) {
            throw new IllegalArgumentException("Reps, sets, and weight must not be negative.");
        }
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
    }

    /**
     * Construtor cópia
     *
     * @param other Recebe um objeto da classe para copiar
     * @throws NullPointerException se o objeto for vazio
     */
    public BenchPress(BenchPress other) {
        super(other);
        if (other == null) {
            throw new NullPointerException("Other BenchPress object cannot be null");
        }
        this.reps = other.getReps();
        this.sets = other.getSets();
        this.weight = other.getSets();
    }
    // ----------------------------Getter and setter methods----------------------------------------------------------
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
    // ----------------------------END OF ------Getter and setter methods----------------------------------------------------------

    // Calcula as calorias gastas na atividade
    @Override
    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = user.caloriesFactor()*1; // FAZER FÒRMULA
        return calories;
    }

    @Override
    public Activity clone() {
        return new BenchPress(this);
    }

    @Override
    public String toString() {
        return "Bench Press{" +
                super.toString()+
                ", sets=" + sets +
                ", reps=" + reps +
                ", weight=" + weight +
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

