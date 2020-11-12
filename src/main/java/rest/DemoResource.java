package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CatDTO;
import entities.User;
import facades.UserFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user/cats")
    @RolesAllowed("user")
    public String getUserCats() {
        String thisuser = securityContext.getUserPrincipal().getName();
        List<CatDTO> cats = FACADE.getCats(thisuser);
        return GSON.toJson(cats);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin/cats")
    @RolesAllowed("admin")
    public String getAdminCats() {
        String thisuser = securityContext.getUserPrincipal().getName();
        List<CatDTO> cats = FACADE.getCats(thisuser);
        return GSON.toJson(cats);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin/allCats")
    @RolesAllowed("admin")
    public String getAllCats() {
        //String thisuser = securityContext.getUserPrincipal().getName();
        List<CatDTO> cats = FACADE.getAllCats();
        return GSON.toJson(cats);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String addCat(String cat) {
        CatDTO catDTO = GSON.fromJson(cat, CatDTO.class);
        String thisuser = securityContext.getUserPrincipal().getName();
        CatDTO newCat = FACADE.addCat(catDTO, thisuser);
        return "{\"msg\": \"" + newCat.getName() + " was registered!\"}";
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String addCatAdmin(String cat) {
        CatDTO catDTO = GSON.fromJson(cat, CatDTO.class);
        String thisuser = securityContext.getUserPrincipal().getName();
        CatDTO newCat = FACADE.addCat(catDTO, thisuser);
        return "{\"msg\": \"" + newCat.getName() + " was registered!\"}";
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin/{id}")
    @RolesAllowed("admin")
    public String deleteCat(@PathParam("id") long id) {
        CatDTO deletedCat = FACADE.deleteCat(id);
        return "{\"msg\": \"" + deletedCat.getName() + " was deleted!\"}";
    }
}