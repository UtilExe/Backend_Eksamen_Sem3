package facades;

import dto.ActivityDTO;
import dto.UserDTO;
import entities.Activity;
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
    public ActivityDTO createActivity(ActivityDTO activityDTOobj, UserDTO userDTOobj) {
        EntityManager em = emf.createEntityManager();
       Activity activity;
        try {
            em.getTransaction().begin();
            
            User user = em.find(User.class, userDTOobj.getUsername());
          //  activity = em.find(Activity.class, activityDTOobj.getId());
          activity = new Activity(activityDTOobj.getExerciseType(), activityDTOobj.getDuration(), activityDTOobj.getDistance(), activityDTOobj.getComment());
        
        
        user.addActivitys(activity);
        
        em.persist(user);
        
        
            
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new ActivityDTO(activity);
    }
   
}
