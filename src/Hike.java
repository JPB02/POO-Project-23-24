import java.io.Serializable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Hike extends Activity implements Serializable{
    private double distance;
    private double altitude;

    public Hike(){
        super();
        this.distance = 0;
        this.altitude = 0;
    }

    public Hike(double distance, double altitude){
        super();
        this.distance = distance;
        this.altitude = altitude;
    }

    public Hike(Hike other){
        super(other);
        this.distance = other.getDistance();
        this.altitude = other.getAltitude();
    }

    public double getDistance(){
        return distance;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    public double getAltitude(){
        return altitude;
    }

    public void setAltitude(double altitude){
        this.altitude = altitude;
    }

    @Override
    public double calories() {
        UserType userType = getUser().getUserType();
        if(userType == UserType.Professional) {
            long age = ChronoUnit.YEARS.between(getUser().getDateOfBirth(),LocalDate.now());
            double calories = (distance * altitude * age) / 150 * 0; // FAZER FÒR
            return calories;
        }

        else {
            long age = ChronoUnit.YEARS.between(getUser().getDateOfBirth(), LocalDate.now());
            double calories = (distance * altitude * age) / 150; // FAZER FÒRMULA
            return calories;
        }

    }

    @Override
    public Hike clone() {
        return new Hike(this);
    }

    @Override
    public String toString() {
        return "Hike{" +
                super.toString()+
                "distance=" + distance +
                ", altitude=" + altitude +
                ", calories=" + calories() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Hike hiking = (Hike) o;
        return Double.compare(hiking.getDistance(), getDistance()) == 0
                && Double.compare(hiking.getAltitude(), getAltitude()) == 0;
    }
}

