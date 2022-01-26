package Service;

import dbMySQL.UserDb;
import model.User;


import javax.naming.NamingException;
import javax.transaction.*;
import javax.transaction.NotSupportedException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("users")
public class Root {
    static UserDb userDb = new UserDb();
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(@QueryParam("email") String email,
                        @QueryParam("password") String password,
                        @QueryParam("firstName") String firstName,
                        @QueryParam("lastName") String lastName) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {

        return userDb.addUser(new User(email,password,firstName,lastName));

    }

    @Path("get-{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") int id){
        return userDb.getUser(id);
    }

    @Path("update")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(User user) throws SystemException, NamingException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        return userDb.updateUser(user);
    }
}
