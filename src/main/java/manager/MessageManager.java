package manager;

import model.Colocation;
import model.Image;
import model.Message;
import model.User;

import javax.naming.NamingException;
import javax.transaction.*;
import java.sql.Timestamp;
import java.util.List;

public class MessageManager extends DaoManager {

    public MessageManager() {
        super();
    }

    public static boolean sendMessage(String email, String colocationName, String content, String picture) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, RollbackException, NotSupportedException {
        User sender = getUser(email);
        Colocation colocation = getColocation(colocationName);

        if (sender == null || colocation == null || !userIsIntoColoc(sender, colocation)) {
            return false;
        }

        Image image = null;
        if (!picture.equals("null")) {
            image = downloadImg(picture);
        }

        Timestamp current_tp = new Timestamp(System.currentTimeMillis());
        if (content != null && !content.equals("")) {
            Message message = new Message(sender, content, current_tp, image);
            messageDao.create(message);
            colocation.addMessage(message);
            colocationDao.edit(colocation);
        }

        return true;
    }

    public static List<Message> getMessages(String email, String name) {
        User user = getUser(email);
        Colocation colocation = getColocation(name);
        if (colocation == null || user == null|| !userIsIntoColoc(user, colocation) ) {
            return null;
        }
        return colocation.getMessages();
    }
}
