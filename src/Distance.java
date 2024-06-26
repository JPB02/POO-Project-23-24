import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Distance extends Activity implements Serializable {

    private double distance;

    /**
     * Construtor por omissao
     */
    public Distance(){
        super();
        this.distance = 0.0;
    }

    /**
     * Construtor parametrizado
     *
     * @param activityID Identificador único da atividade
     * @param type Tipo da atividade
     * @param date Data em que a atividade foi realizada
     * @param duration Duracao da atividade
     * @param distance Distancia percorrida
     */
    public Distance(String activityID, String type, LocalDate date, int duration, double distance, boolean isHard){
        super(activityID, type, date, duration, isHard);
        this.distance = distance;
    }

    /**
     * Construtor cópia
     *
     * @param other Recebe uma objeto da classe
     */
    public Distance(Distance other){
        super(other);
        this.distance = other.getDistance();
    }
    // ----------------------------Getter and setter methods----------------------------------------------------------

    public double getDistance(){
        return this.distance;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    public int calculateMETDistance() {
        int MET = 8;
        if(this.distance >= 0 && this.distance<=2 ) {
            return MET;
        } else if (this.distance >2 && this.distance<=5) {
            MET = 10;
        }
        else if(this.distance>5) {
            MET = 12;
        }
        return MET;
    }

    // ----------------------------END OF ------Getter and setter methods----------------------------------------------------------

    @Override
    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = calculateMETDistance() * getDuration() * user.getWeight()*user.caloriesFactor()*1;
        return calories/70;
    }

    @Override
    public Activity clone() {
        return new Distance(this);
    }

    // Creating DecimalFormat object with two decimal place pattern
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public String toString() {
        String formattedDistance = df.format(this.distance);
        return "Distance: \n" +
                super.toString()+
                "\nDistance: " + formattedDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Distance distance = (Distance) o;
        return Double.compare(distance.getDistance(), getDistance()) == 0;
    }


}
