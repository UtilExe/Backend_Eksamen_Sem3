package facades;

import dto.ActivityDTO;
import entities.Activity;
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
/*
    // As a member I would like to be able to create an exercise activity so that I can save it for future purposes. 
    public ActivityDTO createActivity(ActivityDTO activityDTOObject) {
        EntityManager em = emf.createEntityManager();
       Activity activity;
        try {
            em.getTransaction().begin();
            
            
            
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new ActivityDTO(activity);
    }*/
   
}
