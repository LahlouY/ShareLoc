package Api;

import Security.SigninNeeded;
import io.swagger.annotations.*;
import manager.ColocationManager;
import model.Colocation;

import javax.naming.NamingException;
import javax.transaction.*;
import javax.transaction.NotSupportedException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("colocation")
@SigninNeeded
@Api("/colocation")
public class ColocationServices extends AbstractServices<Colocation> {

    public ColocationServices() {
        super(Colocation.class);
    }

    @POST
    @Path("new")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create new colocation")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "New colocation created"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response addColocation(@ApiParam(value = "Colocation name", required = true) @QueryParam("name") String name,
                                  @ApiParam(value = "Admin email", required = true) @QueryParam("admin") String admin_email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (ColocationManager.createColocation(name, admin_email)) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("remove")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete colocation")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Colocation deleted"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response removeColocation(@ApiParam(value = "Colocation name", required = true) @QueryParam("name") String name,
                                     @ApiParam(value = "Admin email", required = true) @QueryParam("admin") String admin_email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (ColocationManager.removeColocation(name, admin_email)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("invite")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Invite user to colocation")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "User invited"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response sendInvitation(@ApiParam(value = "Colocation name", required = true) @QueryParam("name") String name,
                                   @ApiParam(value = "Admin email", required = true) @QueryParam("admin") String admin,
                                   @ApiParam(value = "User email", required = true) @QueryParam("email") String email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (ColocationManager.inviteUserIntoColocation(name, admin, email)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("editName")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Change colocation name")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Colocation name changed"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response editColocationName(@ApiParam(value = "Colocation name", required = true) @QueryParam("name") String name,
                                       @ApiParam(value = "Admin email", required = true) @QueryParam("admin") String admin_email,
                                       @ApiParam(value = "New colocation name", required = true) @QueryParam("newName") String newName) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (ColocationManager.editColocationName(name, admin_email, newName)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("removeMember")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Remove member of colocation")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "User removed"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response removeMember(@ApiParam(value = "Colocation name", required = true) @QueryParam("name") String name,
                                 @ApiParam(value = "Admin email", required = true) @QueryParam("admin") String admin_email,
                                 @ApiParam(value = "User email", required = true) @QueryParam("email") String member_email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        if (ColocationManager.removeMemberFromColoc(name, admin_email, member_email)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @GET
    @Path("best")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Best member of colocation")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Best user"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response getBestUser(@ApiParam(value = "Admin email", required = true) @QueryParam("email") String email,
                                @ApiParam(value = "Colocation name", required = true) @QueryParam("name") String name) {
        String msg = ColocationManager.getBestUser(email, name);
        if (msg != null) {
            return Response.ok(msg).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

}
