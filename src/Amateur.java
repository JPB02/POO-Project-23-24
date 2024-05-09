import java.util.ArrayList;
import java.time.LocalDate;
import java.io.*;
import java.util.Map;

/**
 * Esta classe representa um utilizador amador.
 * Esta classe herda os parametros da classe User, e adiciona parametros específicos para um utilizador amador
 */
public class Amateur extends User implements Serializable{

    /**
     * Construtor por omissão
     */
    public Amateur(){
        super();
    }

    /**
     * Construtor de um utilizador amador
     * @param name Nome do utilizador amador
     * @param username UserName de um utilizador amador
     * @param userType Tipo de utilizador amador
     * @param dateOfBirth Data nascimento de um utilizador amador
     * @param height Altura do utilizador
     * @param weight Peso do utilizador
     * @param calories Calorias gastas pelo utilizador
     * @param address Morada utilizador
     * @param email Email do utilizador
     * @param password Password do utilizador para entrar na aplicaçao
     * @param avgHR BPM médio
     * @param activitiesList Lista de atividades no perfil de cada utilizador
     * @param workoutPlansList Lista de planos de treino para cada utilizador
     * @throws IllegalArgumentException Controlo de erros... Se os parametros números forem negativos e/ou strings forem nulas
     */
    public Amateur(String name,String username, String userType, LocalDate dateOfBirth, double height, double weight, double calories, String address, String email, String password, int avgHR, ArrayList<Activity> activitiesList, ArrayList<WorkoutPlan> workoutPlansList) {
        super(name, username, userType, dateOfBirth, height, weight, calories, address, email, password, avgHR, activitiesList, workoutPlansList);
        if (height < 0 || weight < 0 || calories < 0 || avgHR < 0) {
            throw new IllegalArgumentException("Height, weight, calories, and average HR must not be negative.");
        }
        if (name.isEmpty() || username.isEmpty() || userType.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Name, username, userType, address, email, and password must not be empty.");
        }
    }

    /**
     * Construtor cópia
     * @param other Recebe outro utilizador amador
     * @throws NullPointerException controlo de erros, se o utilizador for null
     */
    public Amateur(User other){
        super(other);
        if (other == null) {
            throw new NullPointerException("Other user cannot be null");
        }
    }

    /**
     *Cria uma cópia deste utilizador amador
     * @return Uma nova instancia deste mesmo utilizador
     */
    public User clone(){
        return new Amateur((this));
    }

    /**
     * @return Uma string para representar um utilizador amador.
     */

    public String toString() {
        return super.toString();
    }
    /**
     * Calcula um fator para calcular o gasto de calorias.
     * @return Um fator de calorias.
     */
    public double caloriesFactor() {
        if(this.getAvgHR() > 100){
            return 1.5; // each 2 less
        }
        else return 1.3;
    }
}