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

public class WeatherAndCityFacade {

    private static EntityManagerFactory emf;
    private static WeatherAndCityFacade instance;

    private WeatherAndCityFacade() {
    }

    public static WeatherAndCityFacade getWeatherAndCityFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new WeatherAndCityFacade();
        }
        return instance;
    }
    
      public List<WeatherResponseDTO> getWeatherDataForUser(UserDTO userDTO) throws API_Exception {
      EntityManager em = emf.createEntityManager();
        List<WeatherInfo> weathers = new ArrayList();
        List<WeatherResponseDTO> allWeathersDTO = new ArrayList();
        
        try {
            em.getTransaction().begin();
            
            TypedQuery<WeatherResponseDTO> q1 = em.createQuery(
                    "SELECT new dto.DTOForCherry.WeatherResponseDTO(w.humidity, w.skyText, w.temperature, w.windText) "
                    + "FROM WeatherInfo w JOIN w.activity a where a.user.userName = :username", WeatherResponseDTO.class)
                    .setParameter("username", userDTO.getUsername());
            List<WeatherResponseDTO> weatherResponseDTO =  q1.getResultList();
            
            if (weatherResponseDTO.isEmpty()) {
                throw new API_Exception("Not found", 404);
            }

            em.getTransaction().commit();
            return weatherResponseDTO;
        } finally {
            em.close();
        }
    }
      
      public List<CityDTOForDB> getCityDataForUser(UserDTO userDTO) throws API_Exception {
      EntityManager em = emf.createEntityManager();
        List<CityInfo> weathers = new ArrayList();
        List<CityDTOForDB> allWeathersDTO = new ArrayList();
        
        try {
            em.getTransaction().begin();
            
            TypedQuery<CityDTOForDB> q1 = em.createQuery(
                    "SELECT new dto.CityDTOForDB(c.name, c.geocoordinates, c.municipality, c.population) FROM CityInfo c JOIN c.activitys a where a.user.userName = :username", CityDTOForDB.class)
                    .setParameter("username", userDTO.getUsername());
            List<CityDTOForDB> weatherResponseDTO = q1.getResultList();
            
            if (weatherResponseDTO.isEmpty()) {
                throw new API_Exception("Not found", 404);
            }

            em.getTransaction().commit();
            return weatherResponseDTO;
        } finally {
            em.close();
        }
    }

}
