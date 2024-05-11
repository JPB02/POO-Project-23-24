import java.io.Serializable;

import java.text.DecimalFormat;
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
    public DistanceAltitude(String activityID, String type, LocalDate date, int duration, double distance, double altitude, boolean isHard){
        super(activityID, type, date, duration, isHard);
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
        if (this.distance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative.");
        }
        return this.distance;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    public double getAltitude(){
        if (this.altitude < 0) {
            throw new IllegalArgumentException("Altitude cannot be negative.");
        }
        return this.altitude;
    }

    public void setAltitude(double altitude){
        this.altitude = altitude;
    }

    public int calculateMETDistanceAltitude() {
        int MET = 8;
        if((this.altitude >=0 && this.altitude<=100) || (this.distance >= 0 && this.distance<=2 )) {
            return MET;
        } else if ((this.altitude >100 && this.altitude<=500) || (this.distance >2 && this.distance<=5)) {
            MET = 10;
        }
        else if(this.altitude>500 || this.distance>5) {
            MET = 12;
        }
        return MET;
    }

    // ----------------------------END OF ------Getter and setter methods----------------------------------------------------------

    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = calculateMETDistanceAltitude() * getDuration() * user.getWeight()*user.caloriesFactor()*1;
        return calories/70;
    }

    @Override
    public Activity clone() {
        return new DistanceAltitude(this);
    }

    // Creating DecimalFormat object with two decimal place pattern
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public String toString() {
        String formattedDistance = df.format(this.distance);
        String formattedAltitude = df.format(this.altitude);
        return "Distance & Altitude" +
                super.toString()+
                "\nDistance: " + formattedDistance +
                "\nAltitude: " + formattedAltitude;
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

