package facades;

import dto.UserDTO;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public UserDTO createUser(String username, String password, String passwordCheck, String fullName, int age, double weight) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user != null) {
                throw new AuthenticationException("Error: The username already exists");
            } else {
                em.getTransaction().begin();
                if (password.equals(passwordCheck)) {
                    user = new User(username, password, fullName, age, weight);
                    Role userRole = new Role("user");
                    user.addRole(userRole);
                    em.persist(user);
                    em.getTransaction().commit();
                } else {
                    throw new UnsupportedOperationException("Error: Your two passwords don't match");
                }
            }
        } finally {
            em.close();
        }

        return new UserDTO(user);
    }
    
    

}
