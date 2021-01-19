package facades;

import dto.ActivityDTO;
import dto.CityDTO;
import dto.CityDTOForDB;
import dto.DTOForCherry.WeatherResponseDTO;
import dto.UserDTO;
import dto.WeatherDTO;
import entities.Activity;
import entities.CityInfo;
import entities.User;
import entities.WeatherInfo;
import errorhandling.API_Exception;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
    
    public long getActivityCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long activityCount = (long) em.createQuery("SELECT COUNT(a) FROM Activity a").getSingleResult();
            return activityCount;
        } finally {
            em.close();
        }
    }
    
    public List<ActivityDTO> getAllActivites() throws API_Exception {
      EntityManager em = emf.createEntityManager();
        List<Activity> allActivities = new ArrayList();
        List<ActivityDTO> allActivtiesDTO = new ArrayList();
        
        try {
            allActivities = em.createNamedQuery("Activity.getAllRows").getResultList();
            
            if (allActivities.isEmpty()) {
                throw new API_Exception("Not found", 404);
            }

            for (Activity activity : allActivities) {
                allActivtiesDTO.add(new ActivityDTO(activity));
            }

            return allActivtiesDTO;
        } finally {
            em.close();
        }
    }

    public ActivityDTO createActivity(ActivityDTO activityDTOobj, UserDTO userDTOobj, CityDTO cityDTO, WeatherDTO weatherDTO) throws API_Exception {
        EntityManager em = emf.createEntityManager();
        Activity activity;
        CityInfo city;
        try {
            em.getTransaction().begin();

            User user = em.find(User.class, userDTOobj.getUsername());
            if (user == null) {
                throw new API_Exception("Brugeren kunne ikke findes");
            }
            activity = new Activity(activityDTOobj.getExerciseType(), activityDTOobj.getDuration(), activityDTOobj.getDistance(), activityDTOobj.getComment());

            city = new CityInfo(cityDTO.getName(), cityDTO.getVisueltcenterString(), cityDTO.getKommuneName(), cityDTO.getEgenskaber().getIndbyggerantal());

            // Check if City already exists. We don't parse ID on this object, so must check it this way to avoid a rewrite with including ID.
            TypedQuery<CityInfo> cityQuery = em.createQuery("SELECT c from CityInfo c WHERE c.name = :name", CityInfo.class)
                    .setParameter("name", cityDTO.getName()).setMaxResults(1);
            CityInfo tryNow = null;
            if (!cityQuery.getResultList().isEmpty()) {
                tryNow = cityQuery.getSingleResult();
                tryNow.getId();
            }

            List<CityInfo> cityList = new ArrayList<>();

            cityList = cityQuery.getResultList();

            WeatherInfo weather = new WeatherInfo(
                    weatherDTO.getCurrentData().getTemperature(),
                    weatherDTO.getCurrentData().getSkyText(),
                    weatherDTO.getCurrentData().getHumidity(),
                    weatherDTO.getCurrentData().getWindText()
            );

            user.addActivitys(activity);

            if (!cityList.isEmpty()) {
                activity.setWeatherInfo(weather);
                em.persist(user);
                em.persist(weather);
                em.flush(); // execute to db instantly.

                Query q = em.createNativeQuery("UPDATE activity SET city_id = ? WHERE weatherinfo_id = ?");
                q.setParameter(1, tryNow.getId()).setParameter(2, weather.getId());
                q.executeUpdate();

                // city.addActivitys(activity);
                // activity.setCityInfo(city);
                // em.persist(city);
                em.getTransaction().commit();
            } else {
                city.addActivitys(activity);
                activity.setWeatherInfo(weather);
                
                em.persist(user);
                em.persist(city);
                em.persist(weather);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
        return new ActivityDTO(activity);
    }

}
