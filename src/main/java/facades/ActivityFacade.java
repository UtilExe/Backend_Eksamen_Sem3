package facades;

import dto.ActivityDTO;
import dto.CityDTO;
import dto.UserDTO;
import entities.Activity;
import entities.CityInfo;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class ActivityFacade {

    private static EntityManagerFactory emf;
    private static ActivityFacade instance;

    private ActivityFacade() {
    }

    public static ActivityFacade getActivityFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ActivityFacade();
        }
        return instance;
    }

    // As a member I would like to be able to create an exercise activity so that I can save it for future purposes. 
    public ActivityDTO createActivity(ActivityDTO activityDTOobj, UserDTO userDTOobj, CityDTO cityDTOobj) {
        EntityManager em = emf.createEntityManager();
       Activity activity;
       CityInfo city;
        try {
            em.getTransaction().begin();
            
            User user = em.find(User.class, userDTOobj.getUsername());
          //  activity = em.find(Activity.class, activityDTOobj.getId());
          activity = new Activity(activityDTOobj.getExerciseType(), activityDTOobj.getDuration(), activityDTOobj.getDistance(), activityDTOobj.getComment());
        
      //  city = new CityInfo(cityDTOobj.getPrimærtnavn(), cityDTOobj.getVisueltcenter(), cityDTOobj.getKommuner(), cityDTOobj.convert(cityDTOobj.getEgenskaber()));
       
      // Test because I could not convert from Object array to the specific data-type fields.
      // consider making a check if it already exists... (which it does cause it's constant data).
        city = new CityInfo("Navn", 1.0, "Kommune", 5533);
        
        user.addActivitys(activity);
        city.addActivitys(activity);
        
        em.persist(user);
        em.persist(city);
        
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new ActivityDTO(activity);
    }
   
}
