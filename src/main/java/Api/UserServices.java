package Api;

import Security.SigninNeeded;
import io.swagger.annotations.*;
import manager.UserManager;
import model.User;

import javax.naming.NamingException;
import javax.transaction.*;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
@SigninNeeded
@Api("/user")
public class UserServices extends AbstractServices<User> {

    public UserServices() {
        super(User.class);
    }

    @POST
    @Path("edit")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Change user name")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "User name changed"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response editFirstLastNames(@ApiParam(value = "User email", required = true) @QueryParam("email") String email,
                                       @ApiParam(value = "User password", required = true) @QueryParam("password") String password,
                                       @ApiParam(value = "User firstname", required = true) @QueryParam("firstname") String firstname,
                                       @ApiParam(value = "User lastname", required = true) @QueryParam("lastname") String lastname) throws HeuristicRollbackException,
            SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (UserManager.editUser(email, password, firstname, lastname)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("profilePicture")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Change profile picture")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Picture changed"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response editProfilePicture(@ApiParam(value = "User email", required = true) @QueryParam("email") String email,
                                       @ApiParam(value = "New picture", required = true)@QueryParam("picture") String picture) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if(UserManager.editProfilePicture(email,picture)){
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("quit")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Quit colocation")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Left colocation"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response quitColocation(@ApiParam(value = "User email", required = true) @QueryParam("email") String email,
                                   @ApiParam(value = "Colocation name", required = true) @QueryParam("name") String coloc_name) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (UserManager.quitColocation(email, coloc_name)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("vote")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Vote")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "voted"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response vote(@ApiParam(value = "User email", required = true) @QueryParam("email") String email,
                         @ApiParam(value = "Service ID", required = true) @QueryParam("serviceID") Long serviceID,
                         @ApiParam(value = "User vote", required = true) @QueryParam("vote") int vote) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (UserManager.vote(email, serviceID, vote)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("valid")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Validate a service")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Service validated"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response valid(@ApiParam(value = "User email", required = true) @QueryParam("email") String email,
                          @ApiParam(value = "Achieved service ID", required = true) @QueryParam("achievedServiceID") Long achievedServiceID,
                          @ApiParam(value = "Validated or not", required = true) @QueryParam("valid") boolean validated) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (UserManager.valid(email, achievedServiceID, validated)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

}
