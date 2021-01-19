
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.UserDTO;
import errorhandling.API_Exception;
import facades.UserFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

/**
 *
 * @author Emil
 */
@Path("register")
public class RegisterResource {

    public static final int TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30 min
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final UserFacade facade = UserFacade.getUserFacade(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String isUp() {
        return String.format("{\"message\":\"%s\"}", "Server is up");
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(String jsonPerson) throws AuthenticationException, API_Exception {
        UserDTO userDTO = gson.fromJson(jsonPerson,UserDTO.class);
        facade.createUser(userDTO.getUsername(), userDTO.getUserPass(), userDTO.getPasswordCheck(), userDTO.getFullName(), userDTO.getAge(), userDTO.getWeight());
         return Response.ok(new Gson().toJson(userDTO.getUsername())).build();
    }
}