package Api;

import Security.SigninNeeded;
import io.swagger.annotations.*;
import manager.ServiceManager;
import model.Service;

import javax.naming.NamingException;
import javax.transaction.*;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("service")
@SigninNeeded
@Api("/service")
public class ServiceServices extends AbstractServices<Service>{

    public ServiceServices() {
        super(Service.class);
    }

    @POST
    @Path("new")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create new service")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Service created"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response newService(@ApiParam(value = "User email", required = true) @QueryParam("email") String email,
                               @ApiParam(value = "Colcoation name", required = true) @QueryParam("name") String colocationName,
                               @ApiParam(value = "Service title", required = true) @QueryParam("title") String title,
                               @ApiParam(value = "Service description", required = true) @QueryParam("description") String description,
                               @ApiParam(value = "Service cost", required = true) @QueryParam("cost") int cost) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, RollbackException, NotSupportedException {
        if(ServiceManager.createService(email,colocationName,title,description, cost)){
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }


}
