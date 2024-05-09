import java.util.ArrayList;
import java.time.LocalDate;
import java.io.*;
import java.util.Map;
/**
 * Esta classe representa um utilizador Occasional.
 * Esta classe herda os parametros da classe User, e adiciona parametros específicos para um utilizador Occasional
 */
public class Occasional extends User implements Serializable{
    /**
     * Construtor por omissão
     */
    public Occasional(){
        super();
    }
    /**
     * Construtor de um utilizador Occasional
     * @param name Nome do utilizador Occasional
     * @param username UserName de um utilizador Occasional
     * @param userType Tipo de utilizador Occasional
     * @param dateOfBirth Data nascimento de um utilizador Occasional
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
    public Occasional(String name, String username, String userType, LocalDate dateOfBirth, int height, double weight, double calories, String address, String email, String password, int avgHR, ArrayList<Activity> activitiesList, ArrayList<WorkoutPlan> workoutPlansList) {
        super(name, username, userType, dateOfBirth, height, weight, calories, address, email, password, avgHR, activitiesList, workoutPlansList);
    }
    /**
     * Construtor cópia
     * @param other Recebe outro utilizador Occasional
     * @throws NullPointerException controlo de erros, se o utilizador for null
     */
    public Occasional(User other){
        super(other);
    }
    /**
     *Cria uma cópia deste utilizador Occasional
     * @return Uma nova instancia deste mesmo utilizador
     */
    public User clone(){
        return new Occasional((this));
    }
    /**
     * @return Uma string para representar um utilizador Occasional.
     */
    public String toString() {
        return super.toString();
    }
    /**
     * Calcula um fator para calcular o gasto de calorias.
     * @return Um fator de calorias.
     */
    public double caloriesFactor() {
        if(this.getAvgHR() > 85){
            return 1.2; // each 2 less
        }
        else return 1;
    }
}