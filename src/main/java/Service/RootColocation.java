package Service;

import dbMySQL.ColocationDb;
import model.Colocation;

import javax.naming.NamingException;
import javax.transaction.*;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("colocations")
public class RootColocation {
    static ColocationDb colocationDb = new ColocationDb();
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Colocation addColocation(@QueryParam("nameColocation") String nameColocation,
                                    @QueryParam("userAdmin") String userAdmin) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {

        return colocationDb.addColocation(new Colocation(nameColocation,userAdmin));

    }
}
