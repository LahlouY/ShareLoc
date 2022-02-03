package Api;

import io.swagger.annotations.*;
import manager.MessageManager;
import model.Message;
import model.User;

import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("message")
@Api
public class MessageServices extends AbstractServices<Message> {

    public MessageServices() {
        super(Message.class);
    }

    @GET
    @Path("coloc")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Find colocation messages")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Messages found", response = Message.class),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response getMessagesIntoColocation(@ApiParam(value = "Colocation name", required = true) @QueryParam("email") String email,
                                              @ApiParam(value = "User email", required = true) @QueryParam("name") String name){
        List<Message> messages = MessageManager.getMessages(email,name);
        if(messages != null){
            return Response.ok(messages.toString()).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }


    @POST
    @Path("new")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Send messages")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Messages sent"),
            @ApiResponse(code = 409, message = "Conflict with server") })
    public Response sendMessage(@ApiParam(value = "User email", required = true) @QueryParam("email") String email,
                                @ApiParam(value = "Colocation name", required = true) @QueryParam("name") String name,
                                @ApiParam(value = "The message", required = true) @QueryParam("message") String message,
                                @ApiParam(value = "the picture", required = true) @QueryParam("picture") String picture) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, RollbackException, NotSupportedException {
        if (MessageManager.sendMessage(email,name,message,picture)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}
