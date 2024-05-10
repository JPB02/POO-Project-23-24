import java.io.Serializable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Representa a atividade mountain bike e os seus parametros
 */
public class DistanceAltitude extends Activity implements Serializable{
    private double distance;
    private double altitude;


    /**
     * Construtor por omissao
     */
    public DistanceAltitude(){
        super();
        this.distance = 0;
        this.altitude = 0;
    }

    /**
     * Construtor parametrizado
     *
     * @param activityID Identificador único da atividade
     * @param type Tipo da atividade
     * @param date Data em que a atividade foi realizada
     * @param duration Duracao da atividade
     * @param distance Distancia percorrida
     * @param altitude Altitude ganha na atividade
     */
    public DistanceAltitude(String activityID, String type, LocalDate date, int duration, double distance, double altitude){
        super(activityID, type, date, duration);
        if (distance < 0 || altitude < 0) {
            throw new IllegalArgumentException("Distance and altitude must be non-negative.");
        }
        this.distance = distance;
        this.altitude = altitude;
    }

    /**
     * Construtor cópia
     *
     * @param other Recebe uma objeto da classe
     */
    public DistanceAltitude(DistanceAltitude other){
        super(other);
        this.distance = other.getDistance();
        this.altitude = other.getAltitude();
    }

    // ----------------------------Getter and setter methods----------------------------------------------------------

    public double getDistance(){
        if (distance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative.");
        }
        return distance;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    public double getAltitude(){
        if (altitude < 0) {
            throw new IllegalArgumentException("Altitude cannot be negative.");
        }
        return altitude;
    }

    public void setAltitude(double altitude){
        this.altitude = altitude;
    }


    // ----------------------------END OF ------Getter and setter methods----------------------------------------------------------

    @Override
    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = user.caloriesFactor()*1; // FAZER FÒRMULA
        return calories;
    }

    @Override
    public Activity clone() {
        return new DistanceAltitude(this);
    }

    @Override
    public String toString() {
        return "Distance & Altitude" +
                super.toString()+
                "\nDistance: " + distance +
                "\nAltitude: " + altitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DistanceAltitude distanceAltitude = (DistanceAltitude) o;
        return Double.compare(distanceAltitude.getDistance(), getDistance()) == 0
                && Double.compare(distanceAltitude.getAltitude(), getAltitude()) == 0;
    }


}

