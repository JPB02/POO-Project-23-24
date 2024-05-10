import java.io.Serializable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


/**
 * Representa a atividade running e os seus parametros
 */
public class Running extends Distance implements Serializable{
    private double pace; // minutes por km
    private int steps;

    /**
     * Construtor por omissao
     */
    public Running(){
        super();
        this.pace = 0.0;
        this.steps = 0;
    }

    /**
     * Construtor parametrizado
     *

     * @param distance Distancia percorrida
     */
    public Running(String activityID, String type, LocalDate date, int duration, double distance, double pace, int steps){
        super(activityID, type, date, duration, distance);
        this.pace = pace;
        this.steps = steps;
    }

    /**
     * Construtor cópia
     *
     * @param other Recebe uma objeto da classe
     */
    public Running(Running other){
        super(other);
        this.pace = other.getPace();
        this.steps = other.getSteps();
    }
    // ----------------------------Getter and setter methods----------------------------------------------------------

    public double getPace(){
        return pace;
    }

    public void setPace(double pace){
        this.pace = pace;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps){
        this.steps = steps;
    }

    public int calculateSteps(double distance){
        return 1000 * (int)(distance);
    }

    public double calculatePace(double distance, int duration){
        return duration/distance;
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
        return new Running(this);
    }

    @Override
    public String toString() {
        return "Running: \n" +
                super.toString()+
                "\nPace: " + pace+
                "\nSteps: " + steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Running running = (Running) o;
        return Double.compare(running.getDistance(), getDistance()) == 0;
    }


}

