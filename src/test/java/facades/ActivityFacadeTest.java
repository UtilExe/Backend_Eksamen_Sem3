package facades;

import dto.ActivityDTO;
import dto.CityDTO;
import dto.UserDTO;
import entities.Activity;
import entities.CityInfo;
import entities.Role;
import entities.User;
import entities.WeatherInfo;
import errorhandling.API_Exception;
import java.util.List;
import javassist.tools.rmi.ObjectNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

public class ActivityFacadeTest {

    private static EntityManagerFactory emf;
    private static ActivityFacade facade;

    //  public Activity(String exerciseType, double duration, double distance, String comment) {
    private static Activity activity;
    private static CityInfo city;
    private static WeatherInfo weather;

    public ActivityFacadeTest() {
    }


    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ActivityFacade.getActivityFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            em.createQuery("delete from CityInfo").executeUpdate();
            em.createQuery("delete from WeatherInfo").executeUpdate();
            em.createQuery("delete from Activity").executeUpdate();

            em.createQuery("delete from Role").executeUpdate();
            em.createQuery("delete from User").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "password", "Kasper Henriksen", 20, 120);
            user.addRole(userRole);
            User admin = new User("admin", "password", "Mads Frederik", 18, 85);
            admin.addRole(adminRole);
            User both = new User("user_admin", "password", "Line Madsen", 45, 60);
            both.addRole(userRole);
            both.addRole(adminRole);

            activity = new Activity("Jogging", 60, 40, "A nice jog");
            city = new CityInfo("Hvidovre", "9.85832042, 56.76802916", "Rødovre", 6066);
            weather = new WeatherInfo("5", "Solrigt", "44", "5 m/s Nord");

            user.addActivitys(activity);
            city.addActivitys(activity);
            activity.setWeatherInfo(weather);

            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.persist(city);
            em.persist(weather);

            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @Disabled
    @Test
    public void testGetActivityCount() {
        assertEquals(1, facade.getActivityCount(), "Expects one row in the database");
    }

    @Test
    public void testGetAllActivites() throws API_Exception {
        int expResult = 1;
        List<ActivityDTO> result = facade.getAllActivites();
        assertEquals(expResult, result.size());

        // Second test: validate if the comment of the element is the same as activity original activity Object
        for (ActivityDTO c : result) {
            assertTrue(c.getComment().equals(activity.getComment()));
        }
    }
    

    @Disabled
    @Test
    public void testCreateActivity() throws API_Exception {
        ActivityDTO activityDTO = new ActivityDTO("Went well", 40, 80, "Jogging");
        UserDTO userDTO = new UserDTO("Janne", "henrik123", "henrik123", "Janne Frederiksen", 20, 40);
        // CityDTO cityDTO = new CityDTO(); ...
    }
}
