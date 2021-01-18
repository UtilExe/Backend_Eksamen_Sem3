package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ActivityDTO;
import dto.UserDTO;
import entities.User;
import errorhandling.API_Exception;
import facades.ActivityFacade;
import java.util.ArrayList;
import java.util.List;
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

@Path("activity")
public class ActivityResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static SecurityContext sc;
    
    public static final ActivityFacade facade = ActivityFacade.getActivityFacade(EMF);
    
    @Path("create")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String makeActivity(String json) throws API_Exception {
        ActivityDTO activityDTO = gson.fromJson(json, ActivityDTO.class);
        UserDTO userDTO = gson.fromJson(json, UserDTO.class);
        return gson.toJson(facade.createActivity(activityDTO, userDTO));
    }

}