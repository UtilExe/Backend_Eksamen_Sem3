package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ActivityDTO;
import dto.CityDTO;
import dto.CityDTOForDB;
import dto.CombinedDTO;
import dto.UserDTO;
import dto.WeatherDTO;
import entities.User;
import errorhandling.API_Exception;
import facades.ActivityFacade;
import facades.WeatherAndCityFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;
import utils.HttpUtils;

@Path("weatherandcity")
public class WeatherAndCityResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static SecurityContext sc;
    private static final ExecutorService ES = Executors.newCachedThreadPool();
    
    public static final WeatherAndCityFacade facade = WeatherAndCityFacade.getWeatherAndCityFacade(EMF);
    
    @Path("weatheruser")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public String getWeatherDataForUser(String username) throws API_Exception {
        UserDTO userDTO = gson.fromJson(username, UserDTO.class);
        return gson.toJson(facade.getWeatherDataForUser(userDTO));
    }
    
    @Path("cityuser")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public String getCityDataForUser(String username) throws API_Exception {
        UserDTO userDTO = gson.fromJson(username, UserDTO.class);
        return gson.toJson(facade.getCityDataForUser(userDTO));
    }
    
}