package Api;

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
public class UserServices extends AbstractServices<User> {

    public UserServices() {
        super(User.class);
    }

    @POST
    @Path("edit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editFirstLastNames(@QueryParam("email") String email,
                                       @QueryParam("password") String password,
                                       @QueryParam("firstname") String firstname,
                                       @QueryParam("lastname") String lastname) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (UserManager.editUser(email, password, firstname, lastname)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("profilePicture")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editProfilePicture(@QueryParam("email") String email,
                                       @QueryParam("picture") String picture) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if(UserManager.editProfilePicture(email,picture)){
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("quit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response quitColocation(@QueryParam("email") String email,
                                   @QueryParam("name") String coloc_name) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (UserManager.quitColocation(email, coloc_name)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("vote")
    @Produces(MediaType.APPLICATION_JSON)
    public Response vote(@QueryParam("email") String email,
                         @QueryParam("serviceID") Long serviceID,
                         @QueryParam("vote") int vote) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (UserManager.vote(email, serviceID, vote)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("valid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response valid(@QueryParam("email") String email,
                          @QueryParam("achievedServiceID") Long achievedServiceID,
                          @QueryParam("valid") boolean validated) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (UserManager.valid(email, achievedServiceID, validated)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

}
