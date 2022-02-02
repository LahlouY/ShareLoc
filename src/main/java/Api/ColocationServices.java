package Api;

import io.swagger.annotations.Api;
import manager.ColocationManager;
import model.Colocation;

import javax.naming.NamingException;
import javax.transaction.*;
import javax.transaction.NotSupportedException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("colocation")
@Api
public class ColocationServices extends AbstractServices<Colocation> {

    public ColocationServices() {
        super(Colocation.class);
    }

    @POST
    @Path("new")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addColocation(@QueryParam("name") String name,
                                  @QueryParam("admin") String admin_email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (ColocationManager.createColocation(name, admin_email)) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("remove")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeColocation(@QueryParam("name") String name,
                                     @QueryParam("admin") String admin_email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (ColocationManager.removeColocation(name, admin_email)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("invite")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendInvitation(@QueryParam("name") String name,
                                   @QueryParam("admin") String admin,
                                   @QueryParam("email") String email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (ColocationManager.inviteUserIntoColocation(name, admin, email)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("editName")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editColocationName(@QueryParam("name") String name,
                                       @QueryParam("admin") String admin_email,
                                       @QueryParam("newName") String newName) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (ColocationManager.editColocationName(name, admin_email, newName)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("removeMember")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeMember(@QueryParam("name") String name,
                                 @QueryParam("admin") String admin_email,
                                 @QueryParam("email") String member_email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (ColocationManager.removeMemberFromColoc(name, admin_email, member_email)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @GET
    @Path("best")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBestUser(@QueryParam("email") String email,
                                @QueryParam("name") String name) {
        String msg = ColocationManager.getBestUser(email, name);
        if (msg != null) {
            return Response.ok(msg).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

}
