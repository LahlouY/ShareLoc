package Api;

import io.swagger.annotations.Api;
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
    public Response newAchievedService(@QueryParam("email") String email,
                                       @QueryParam("serviceID") Long serviceID,
                                       @QueryParam("date") String date,
                                       @QueryParam("picture") String picture,
                                       @QueryParam("to") List<String> to) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, RollbackException, NotSupportedException {
        if (AchievedServiceManager.newAchievedService(email, serviceID, date, picture,to)) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}
