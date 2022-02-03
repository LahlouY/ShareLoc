package Api;

import io.swagger.annotations.*;
import manager.AchievedServiceManager;
import model.AchievedService;

import javax.naming.NamingException;
import javax.transaction.*;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("achievedService")
@Api
public class AchievedServiceServices extends AbstractServices<AchievedService> {

    public AchievedServiceServices() {
        super(AchievedService.class);
    }

    @POST
    @Path("new")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create new achieved Service")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "New achieved Service created"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response newAchievedService(@ApiParam(value = "User email", required = true) @QueryParam("email") String email,
                                       @ApiParam(value = "Service ID", required = true) @QueryParam("serviceID") Long serviceID,
                                       @ApiParam(value = "Date of service", required = true) @QueryParam("date") String date,
                                       @ApiParam(value = "Picture of service", required = true) @QueryParam("picture") String picture,
                                       @ApiParam(value = "List of users", required = true) @QueryParam("to") List<String> to) throws HeuristicRollbackException,
            SystemException, HeuristicMixedException, NamingException, RollbackException, NotSupportedException {
        if (AchievedServiceManager.newAchievedService(email, serviceID, date, picture,to)) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}
