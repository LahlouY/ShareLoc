package dbMySQL;

import model.Colocation;
import model.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import javax.transaction.*;
import javax.transaction.RollbackException;

public class ColocationDb {
    EntityDb entityDb;

    public ColocationDb() {
    }
    public Colocation addColocation(Colocation colocation) throws NamingException, SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {

        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        entityDb.entitymanager.persist( colocation );
        entityDb.entitymanager.joinTransaction();
        transaction.commit();

        return colocation;
    }
    public Colocation getColocation(int id){

        Colocation colocation = entityDb.entitymanager.find(Colocation.class,id);
        return colocation;
    }
    public Colocation updateColocation(Colocation colocation) throws NamingException, SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        entityDb.entitymanager.merge(colocation);
        entityDb.entitymanager.joinTransaction();
        transaction.commit();
        return colocation;
    }
}
