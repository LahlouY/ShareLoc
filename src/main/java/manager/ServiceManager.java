package manager;

import model.Colocation;
import model.Service;
import model.User;

import javax.naming.NamingException;
import javax.transaction.*;

public class ServiceManager extends DaoManager {

    public ServiceManager() {
        super();
    }

    public static boolean createService(String email, String colocationName, String title, String description, int cost) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, RollbackException, NotSupportedException {
        User creator = getUser(email);
        Colocation colocation = getColocation(colocationName);

        if (creator != null && colocation != null) {
            Service service = new Service(colocation, creator, title, description, cost);
            serviceDao.create(service);
            return true;
        }
        return false;
    }
}
