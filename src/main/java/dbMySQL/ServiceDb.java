package dbMySQL;

import model.Service;
import model.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.*;

public class ServiceDb {

    EntityDb entityDb;

    public ServiceDb() {
    }

    public Service addService(Service service) throws NamingException, SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {

        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        entityDb.entitymanager.persist( service );
        entityDb.entitymanager.joinTransaction();
        transaction.commit();

        return service;
    }
    public Service getService(int id){

        Service service = entityDb.entitymanager.find(Service.class,id);
        return service;
    }
    public Service updateUser(Service service) throws NamingException, SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        entityDb.entitymanager.merge(service);
        entityDb.entitymanager.joinTransaction();
        transaction.commit();
        return service;
    }
}
