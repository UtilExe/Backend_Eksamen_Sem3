package facades;

import dto.ActivityDTO;
import dto.CityDTO;
import dto.CityDTOForDB;
import dto.UserDTO;
import dto.WeatherDTO;
import entities.Activity;
import entities.CityInfo;
import entities.User;
import entities.WeatherInfo;
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
    public ActivityDTO createActivity(ActivityDTO activityDTOobj, UserDTO userDTOobj, CityDTO cityDTO, WeatherDTO weatherDTO) {
        EntityManager em = emf.createEntityManager();
        Activity activity;
        CityInfo city;
        try {
            em.getTransaction().begin();

            User user = em.find(User.class, userDTOobj.getUsername());
            activity = new Activity(activityDTOobj.getExerciseType(), activityDTOobj.getDuration(), activityDTOobj.getDistance(), activityDTOobj.getComment());

            city = new CityInfo(cityDTO.getName(), cityDTO.getVisueltcenterString(), cityDTO.getKommuneName(), cityDTO.getEgenskaber().getIndbyggerantal());
            
            WeatherInfo weather = new WeatherInfo(
                    weatherDTO.getCurrentData().getTemperature(), 
                    weatherDTO.getCurrentData().getSkyText(), 
                    weatherDTO.getCurrentData().getHumidity(), 
                    weatherDTO.getCurrentData().getWindText()
            );
            
         //   WeatherInfo weather = new WeatherInfo("123", "test", "test", "test");
            user.addActivitys(activity);
            city.addActivitys(activity);
       
            activity.setWeatherInfo(weather);

            em.persist(user);
            em.persist(city);
            em.persist(weather);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new ActivityDTO(activity);
    }

}
