package Api;

import dao.AbstractDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AbstractServices<T> {

    private final AbstractDao<T> dao;

    public AbstractServices(Class<T> serviceClass) {
        this.dao = new AbstractDao<T>(serviceClass);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok()
                .entity(dao.findAll())
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Integer id) {
        final T obj = dao.find(id);
        if (obj == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok()
                .entity(obj)
                .build();
    }
}
