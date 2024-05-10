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
        this.pace = 0.0;
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
    public MountainBike(String activityID, String type, LocalDate date, int duration, double distance, double altitude, double pace){
        super(activityID, type, date, duration, distance, altitude);
        if (distance < 0 || altitude < 0) {
            throw new IllegalArgumentException("Distance and altitude must be non-negative.");
        }
        this.pace = pace;
    }

    /**
     * Construtor cópia
     *
     * @param other Recebe uma objeto da classe
     */
    public MountainBike(MountainBike other){
        super(other);
        this.pace = other.getPace();
    }

    // ----------------------------Getter and setter methods----------------------------------------------------------

    public double getPace() {
        return pace;
    }

    public void setPace(double pace) {
        this.pace = pace;
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
        return "MountainBike" +
                super.toString()+
                "\nPace: " + pace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MountainBike mountainBike = (MountainBike) o;
        return Double.compare(mountainBike.getPace(), getPace()) == 0;
    }

}

