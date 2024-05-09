import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Gere as interações do user com a aplicação
 */
public class FitnessAppManager {
    private Map<String, User> users;
    private Fitness fitness;

    /**
     * Constructor para a class
     * Inicializa um map de utilizadores e uma instancia da classe Fitness
     */
    public FitnessAppManager() {
        this.users = new HashMap<>();
        this.fitness = new Fitness();
    }

    /**
     * Faz o registo de um user na aplicação
     *
     * @param user Um objeto de user
     */
    public void registerUser(User user) {
        if (user == null || user.getEmail() == null) {
            throw new IllegalArgumentException("User or user email cannot be null.");
        }
        if (users.containsKey(user.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }
        users.put(user.getEmail(), user);
    }

    /**
     *Tenta fazer login com o o email e a password
     *
     * @param email    Email do utilizador
     * @param password Password do utilizador
     * @return true if login is successful, false otherwise.
     */
    public boolean login(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email or password cannot be null.");
        }
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            // Successful login
            return true;
        }
        return false;
    }

    /**
     * Adiciona uma ativiadade para as atividades de um utilizador
     *
     * @param user Utilizador a quem vai ser adicionada a atividade
     * @param activity Atividade a adicionar
     */
    public void addActivity(User user, Activity activity) {
        if (user == null || activity == null) {
            throw new IllegalArgumentException("User or Activity cannot be null.");
        }
        user.addActivityToUser(activity);
        fitness.getActivityMap().put(activity.getActivityID(), activity);
    }

    /**
     * Adiciona um plano de treino a um user
     *
     * @param user Utilizador a quem vai ser adicionada o plano de treino
     * @param workoutPlan Plano de treino a adicionar
     */
    public void addWorkoutPlan(User user, WorkoutPlan workoutPlan) {
        if (user == null || workoutPlan == null) {
            throw new IllegalArgumentException("User or Workout Plan cannot be null.");
        }
        user.addWorkoutPlanToUser(workoutPlan);
        // Additional logic if needed
    }
}