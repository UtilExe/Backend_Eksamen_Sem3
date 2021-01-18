package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ActivityDTO;
import dto.CityDTO;
import dto.CombinedDTO;
import dto.UserDTO;
import dto.WeatherDTO;
import entities.User;
import errorhandling.API_Exception;
import facades.ActivityFacade;
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

@Path("activity")
public class ActivityResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static SecurityContext sc;
    private static final ExecutorService ES = Executors.newCachedThreadPool();
    private static final String CITY_AWS_URL = "https://dawa.aws.dk/steder?hovedtype=Bebyggelse&undertype=by&prim%C3%A6rtnavn=";
    private static final String DEGREE_VEJR_URL = "https://vejr.eu/api.php?location=";
    
    public static final ActivityFacade facade = ActivityFacade.getActivityFacade(EMF);
    
    @Path("create")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String makeActivity(String json) throws API_Exception, InterruptedException, ExecutionException, TimeoutException {
        ActivityDTO activityDTO = gson.fromJson(json, ActivityDTO.class);
        UserDTO userDTO = gson.fromJson(json, UserDTO.class);
        CityDTO cityDTO = gson.fromJson(json, CityDTO.class); // felt: primærtnavn
        ActivityDTO result = facade.createActivity(activityDTO, userDTO);
        return responseWithParallelFetch(ES, cityDTO, result);
     //   return gson.toJson(facade.createActivity(activityDTO, userDTO));
    }
    
    public static String responseWithParallelFetch(ExecutorService threadPool, CityDTO cityDTO, ActivityDTO activityResult) throws InterruptedException, ExecutionException, TimeoutException, API_Exception {
        String cityName = cityDTO.getPrimærtnavn();
        Callable<CityDTO[]> callableCityTask = new Callable<CityDTO[]>() {
            @Override
            public CityDTO[] call() throws IOException {
                String json = HttpUtils.fetchData(CITY_AWS_URL + cityName);
                CityDTO[] cityDTO = gson.fromJson(json, CityDTO[].class);
                return cityDTO;
            }
        };
        Callable<WeatherDTO> callableWeatherTask = new Callable<WeatherDTO>() {
            @Override
            public WeatherDTO call() throws IOException {
                String json = HttpUtils.fetchData(DEGREE_VEJR_URL + cityName + "&degree=C");
                WeatherDTO weatherDTO = gson.fromJson(json, WeatherDTO.class);
                return weatherDTO;
            }
        };

        Future<CityDTO[]> futureCity = threadPool.submit(callableCityTask);
        Future<WeatherDTO> futureDegree = threadPool.submit(callableWeatherTask);
      //  Future<SimilarDTO> futureSimilar = threadPool.submit(similarTask);

        CityDTO[] cityData = futureCity.get(5555, TimeUnit.SECONDS);
        WeatherDTO degreeData = futureDegree.get(5555, TimeUnit.SECONDS);
      //  SimilarDTO similar = futureSimilar.get(3, TimeUnit.SECONDS);

        CombinedDTO combinedDTO = new CombinedDTO(cityData, degreeData);
        
        if(combinedDTO.isEmpty()) {
            throw new API_Exception("Not found..", 404);
        } else {
            String combinedJSON = gson.toJson(combinedDTO);

            return combinedJSON;
        }
        
    }

}