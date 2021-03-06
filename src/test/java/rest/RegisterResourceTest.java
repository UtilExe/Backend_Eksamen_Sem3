package rest;

import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import rest.ApplicationConfig;
import utils.EMF_Creator;

public class RegisterResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "password", "Kasper Henriksen", 20, 120);
            user.addRole(userRole);
            User admin = new User("admin", "password", "Mads Frederik", 18, 85);
            admin.addRole(adminRole);
            User both = new User("user_admin", "password", "Line Madsen", 45, 60);
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public RegisterResourceTest() {
    }

    @Test
    public void serverIsRunningTest() {
        given().when().get("/register").then().statusCode(200);
    }

    @Test
    public void registerTest() {
        String username = "test@user.dk";
        String password1 = "1234secret";
        String password2 = "1234secret";
        String fullName = "Mads Frederiksen";
        int age = 25;
        int weight = 70;

        String jsonRequest = String.format(
                "{ \"username\": \"%s\", "
                + "\"userPass\": \"%s\","
                + "\"passwordCheck\": \"%s\","
                + "\"fullName\": \"%s\","
                + "\"age\": \"%d\","
                + "\"weight\":\"%d\"}",
                username, password1, password2, fullName, age, weight);

        given()
                .contentType("application/json")
                .body(jsonRequest)
                .when().post("/register").then()
                .statusCode(200);
    }
    
    // Should have been in ActivityResource, and should have had persisted data
    @Test
    public void testActivityCount() throws Exception {
        given()
        .contentType("application/json")
        .get("/activity/count").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("count", equalTo(0));   
    }

}
