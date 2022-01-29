package dbMySQL;

import model.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import javax.transaction.*;
import javax.transaction.RollbackException;

public class UserDb {

    EntityDb entityDb;

    public UserDb() {
    }

    public User addUser(User user) throws NamingException, SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {

        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        entityDb.entitymanager.persist( user );
        entityDb.entitymanager.joinTransaction();
        transaction.commit();

        return user;
    }
    public User getUser(int id){

        User user = entityDb.entitymanager.find(User.class,id);
        return user;
    }

    public User getUserEmailPass(String email, String password){

        return (User) entityDb.entitymanager.createQuery("select u FROM User u where u.email = :email and u.password = :password")
                .setParameter("email",email)
                .setParameter("password",password)
                .getSingleResult();
    }
    public User updateUser(User user) throws NamingException, SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        entityDb.entitymanager.merge(user);
        entityDb.entitymanager.joinTransaction();
        transaction.commit();
        return user;
    }
}
