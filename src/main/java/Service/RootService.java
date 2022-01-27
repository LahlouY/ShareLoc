package Service;

import auth.Secured;
import dbMySQL.ServiceDb;
import dbMySQL.UserDb;
import model.Service;
import model.User;

import javax.naming.NamingException;
import javax.transaction.*;
import javax.transaction.NotSupportedException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("services")
public class RootService {
    static ServiceDb serviceDb = new ServiceDb();
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Service addUser(@QueryParam("email") String title,
                        @QueryParam("password") String description,
                        @QueryParam("firstName") int cost) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {

        return serviceDb.addService(new Service(title,description,cost));

    }

    @Path("get-{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Service getUser(@PathParam("id") int id){
        return serviceDb.getService(id);
    }

    @Path("update")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Service updateUser(Service service) throws SystemException, NamingException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        return serviceDb.updateUser(service);
    }

    @GET
    @Secured
    @Path("auth-{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Service mySecuredMethod(@PathParam("id") int id){
        return serviceDb.getService(id);
    }
}
