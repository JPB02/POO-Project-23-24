import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
/**
 * Representa a atividade de BenchPress com detalhes sobre o numero de sets
 *reps e peso levantado.
 */
public class Weightlifting extends Activity implements Serializable {

    private int reps;
    private int sets;
    private double weight;

    /**
     * Construtor por omissao
     */
    public Weightlifting() {
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
    public Weightlifting(String activityID, String type, LocalDate date, int duration,boolean isHard, int reps, int sets, double weight) {
        super(activityID, type, date, duration, isHard);
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
    public Weightlifting(Weightlifting other) {
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
        return this.reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return this.sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    // ----------------------------END OF ------Getter and setter methods----------------------------------------------------------

    public int calculateMETWeightlifting() {
        int MET = 2;
        if(this.reps > 0 && this.sets > 0 && this.weight > 0 && this.reps < 6 && this.sets < 2) {
            return MET;
        } else if (this.reps >= 6 && this.sets >= 2 && this.reps < 12 && this.sets < 4) {
            MET = 4;
        }
        else if(this.reps >=12 && this.sets >=4){
            MET = 6;
        }
        return MET;
    }


    // Calcula as calorias gastas na atividade
    @Override
    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = calculateMETWeightlifting() * getDuration() * user.getWeight()*user.caloriesFactor()*1;
        return calories/70;
    }

    @Override
    public Activity clone() {
        return new Weightlifting(this);
    }

    // Creating DecimalFormat object with two decimal place pattern
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public String toString() {
        String formattedWeight = df.format(this.weight);
        return "Weightlifting" +
                super.toString()+
                "\nSets: " + this.sets +
                "\nReps: " + this.reps +
                "\nWeight: " + formattedWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Weightlifting weightlifting = (Weightlifting) o;
        return this.sets == weightlifting.getSets()
                && this.reps == weightlifting.getReps()
                && Double.compare(this.weight, weightlifting.getWeight()) == 0;
    }
}

