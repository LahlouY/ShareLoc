package Api;

import Security.JWTokenUtility;
import Security.SigninNeeded;
import io.swagger.annotations.*;
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
@Api("/auth")
public class LogServices {

    public LogServices() {
    }

    @GET
    @SigninNeeded
    @Path("whoami")
    @ApiOperation(value = "Check user authentication")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "User authenticated", response = User.class),
            @ApiResponse(code = 204, message = "No authentication ") })
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
    @ApiOperation(value = "Create User")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "User created"),
            @ApiResponse(code = 409, message = "User not created") })
    public Response signup( @ApiParam(value = "User email", required = true) @QueryParam("email") String email,
                            @ApiParam(value = "User password", required = true) @QueryParam("password") String password,
                            @ApiParam(value = "User firstname", required = true) @QueryParam("firstname") String firstname,
                            @ApiParam(value = "User lastname", required = true) @QueryParam("lastname") String lastname) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, RollbackException, NotSupportedException {
        if (UserManager.createUser(email, password, firstname, lastname))
            return Response.status(Response.Status.CREATED).build();
        return Response.status(Response.Status.CONFLICT).build();

    }

    @POST
    @Path("signin")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Sign In")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "User signed in"),
            @ApiResponse(code = 406, message = "DataBase finds nothing that satisfies the criteria") })
    public Response signin(@ApiParam(value = "User email", required = true) @QueryParam("email") String email,
                           @ApiParam(value = "User password", required = true) @QueryParam("password") String password){
        User user = UserManager.login(email,password);
        if(user != null) {
            return Response.ok().entity(JWTokenUtility.buildJWT(user.getEmail())).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    public static List<String> findUserRoles(String user) {
        return null;
    }

}
