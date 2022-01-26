package dbMySQL;

import javax.persistence.*;

public class EntityDb {
    @PersistenceUnit(unitName = "Eclipselink_JPA")
    static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
    @PersistenceContext(unitName = "Eclipselink_JPA")
    static EntityManager entitymanager = emfactory.createEntityManager( );

}
