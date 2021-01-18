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
import errorhandling.API_Exception;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    // As a member I would like to be able to create an exercise activity so that I can save it for future purposes. 
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
            
            // Check if it already exists
            TypedQuery<CityInfo> cityQuery = em.createQuery("SELECT c from CityInfo c WHERE c.name = :name", CityInfo.class)
                    .setParameter("name", cityDTO.getName());
            List<CityInfo> cityList = new ArrayList<>();
            
            cityList = cityQuery.getResultList();
            if (!cityList.isEmpty()) {
                /* Måske ikke nødvendigt ligefrem at trigger en Exception. 
                   Afhænger af, om brugeren skal kunne se den eller blot skal få resultatet ud, uden at persistere til DB'en */
                // throw new API_Exception("Byen findes allerede");
                return new ActivityDTO(activity);
            }
            
            WeatherInfo weather = new WeatherInfo(
                    weatherDTO.getCurrentData().getTemperature(), 
                    weatherDTO.getCurrentData().getSkyText(), 
                    weatherDTO.getCurrentData().getHumidity(), 
                    weatherDTO.getCurrentData().getWindText()
            );
            
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
