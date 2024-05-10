import java.io.Serializable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Representa a atividade mountain bike e os seus parametros
 */
public class MountainBike extends DistanceAltitude implements Serializable{
        private double pace;

    /**
     * Construtor por omissao
     */
    public MountainBike(){
        super();

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
    public MountainBike(String activityID, String type, LocalDate date, int duration, double distance, double altitude){
        super(activityID, type, date, duration, distance, altitude);
        if (distance < 0 || altitude < 0) {
            throw new IllegalArgumentException("Distance and altitude must be non-negative.");
        }

    }

    /**
     * Construtor cópia
     *
     * @param other Recebe uma objeto da classe
     */
    public MountainBike(MountainBike other){
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
        return new MountainBike(this);
    }

    @Override
    public String toString() {
        return "MountainBike{" +
                super.toString()+
                "distance=" + distance +
                ", altitude=" + altitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MountainBike mountainBike = (MountainBike) o;
        return Double.compare(mountainBike.getDistance(), getDistance()) == 0
                && Double.compare(mountainBike.getAltitude(), getAltitude()) == 0;
    }


}

