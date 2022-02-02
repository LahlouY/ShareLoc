package Api;

import Security.JWTokenUtility;
import Security.SigninNeeded;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import manager.UserManager;
import model.User;

import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("auth")
@Api
public class LogServices {

    public LogServices() {
    }

    @GET
    @SigninNeeded
    @Path("whoami")
    @Produces(MediaType.APPLICATION_JSON)
    public Response whoami(@Context SecurityContext security) {
        User user = UserManager.getUser(security.getUserPrincipal().getName());
        if (user!=null)
            return Response.ok().entity(user).build();
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Path("signup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(@QueryParam("email") String email,
                           @QueryParam("password") String password,
                           @QueryParam("firstname") String firstname,
                           @QueryParam("lastname") String lastname) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, RollbackException, NotSupportedException {
        if (UserManager.createUser(email, password, firstname, lastname))
            return Response.status(Response.Status.CREATED).build();
        return Response.status(Response.Status.CONFLICT).build();

    }

    @POST
    @Path("signin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signin(@QueryParam("email") String email,
                           @QueryParam("password") String password){
        User user = UserManager.login(email,password);
        if(user != null) {
            return Response.ok().entity(JWTokenUtility.buildJWT(user.getEmail())).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    /**
     * Méthode permettant de récupérer l'ensemble des roles d'un utilisateur
     *
     * @param user l'utilisateur
     * @return une liste de tous les roles associés à l'utilisateur user
     */
    public static List<String> findUserRoles(String user) {
        return null;
    }

}
