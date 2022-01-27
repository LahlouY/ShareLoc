package Service;

import dbMySQL.ColocationDb;
import model.Colocation;
import model.User;

import javax.naming.NamingException;
import javax.transaction.*;
import javax.transaction.NotSupportedException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("colocations")
public class RootColocation {
    static ColocationDb colocationDb = new ColocationDb();

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Colocation addColocation(@QueryParam("nameColocation") String nameColocation,
                                    @QueryParam("userAdmin") int userAdmin) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {

        return colocationDb.addColocation(new Colocation(nameColocation,userAdmin));

    }
    @Path("get-{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Colocation getColocation(@PathParam("id") int id){
        return colocationDb.getColocation(id);
    }


    @Path("update")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Colocation updateColocation(Colocation colocation) throws SystemException, NamingException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        return colocationDb.updateColocation(colocation);
    }
    @Path("add")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Colocation addUserToColocation() throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        Colocation colocation = new Colocation("ham",1);
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(55);
        list.add(4);
        list.add(36);
        colocation.setMembersId(list);

        return colocationDb.addColocation(colocation);
    }

}
