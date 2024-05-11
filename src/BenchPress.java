import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
/**
 * Representa a atividade de BenchPress com detalhes sobre o numero de sets
 *reps e peso levantado.
 */
public class BenchPress extends Weightlifting implements Serializable {

    private boolean incline;

    /**
     * Construtor por omissao
     */
    public BenchPress() {
        super();
        this.incline = false;
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
    public BenchPress(String activityID, String type, LocalDate date, int duration, boolean isHard,  int reps, int sets, double weight, boolean incline) {
        super(activityID, type, date, duration, isHard , reps, sets, weight);
        this.incline = incline;
    }

    /**
     * Construtor cópia
     *
     * @param other Recebe um objeto da classe para copiar
     * @throws NullPointerException se o objeto for vazio
     */
    public BenchPress(BenchPress other) {
        super(other);
        this.incline = other.getIncline();
    }
    // ----------------------------Getter and setter methods----------------------------------------------------------
    public boolean getIncline() {
        return this.incline;
    }

    public void setInclination(boolean inclination) {
        this.incline = inclination;
    }
    // ----------------------------END OF ------Getter and setter methods----------------------------------------------------------

    public int calculateMETBenchPress() {
        int MET = 2;
        if(this.incline) {
            MET = 4;
        }
        return MET;
    }

    // Calcula as calorias gastas na atividade
    @Override
    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = calculateMETBenchPress() * getDuration() * user.getWeight()*user.caloriesFactor()*1;
        return calories/70;
    }

    @Override
    public Activity clone() {
        return new BenchPress(this);
    }

    @Override
    public String toString() {
        return "Bench Press: " +
                super.toString()+
                "\nIncline: " + this.incline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BenchPress bench = (BenchPress) o;
        return this.incline == bench.getIncline();
    }
}

