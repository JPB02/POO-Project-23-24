import java.io.Serializable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Canoeing extends Activity implements Serializable{
    private double distance;

    public Canoeing(){
        super();
        this.distance = 0;
    }

    public Canoeing(double distance){
        super();
        this.distance = distance;
    }

    public Canoeing(Canoeing other){
        super(other);
        this.distance = other.getDistance();
    }

    public double getDistance(){
        return distance;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    @Override
    public double calories() {
        UserType userType = getUser().getUserType();
        if(userType == UserType.Professional) {
            long age = ChronoUnit.YEARS.between(getUser().getDateOfBirth(),LocalDate.now());
            double calories = (distance * age) / 150 * 0; // FAZER FÒR
            return calories;
        }

        else {
            long age = ChronoUnit.YEARS.between(getUser().getDateOfBirth(), LocalDate.now());
            double calories = (distance * age) / 150; // FAZER FÒRMULA
            return calories;
        }

    }

    @Override
    public Canoeing clone() {
        return new Canoeing(this);
    }

    @Override
    public String toString() {
        return "Canoeing{" +
                super.toString()+
                "distance=" + distance +
                ", calories=" + calories() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Canoeing canoeing = (Canoeing) o;
        return Double.compare(canoeing.getDistance(), getDistance()) == 0;
    }

}

