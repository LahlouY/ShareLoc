package dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.*;
import java.util.Collections;
import java.util.List;

public class AbstractDao<T> {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Eclipselink_JPA");
    private static EntityManager entityManager = null;

    private Class<T> clazz;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }


    protected EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }


    public T create(T entite) throws HeuristicRollbackException, SystemException, HeuristicMixedException, RollbackException, NotSupportedException, NamingException {
        final EntityManager em = getEntityManager();
        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        em.persist(entite);
        em.joinTransaction();
        transaction.commit();
        return entite;
    }


    public void edit(T entite) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException, NamingException {
        final EntityManager em = getEntityManager();
        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        em.merge(entite);
        em.joinTransaction();
        transaction.commit();
    }


    public void remove(T entite) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException, NamingException {
        final EntityManager em = getEntityManager();
        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        em.remove(getEntityManager().merge(entite));
        em.joinTransaction();
        transaction.commit();
    }


    public T find(Object id) {
        return getEntityManager().find(clazz, id);
    }


    public List<T> findAll() {
        final EntityManager em = getEntityManager();
        final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.select(criteriaQuery.from(clazz));
        final List<T> results = em.createQuery(criteriaQuery).getResultList();
        if (results == null) {
            return Collections.emptyList();
        }
        return results;
    }


    public long count() {
        final EntityManager em = getEntityManager();
        final CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
        final javax.persistence.criteria.Root<Long> rt = cq.from(Long.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return (Long) q.getSingleResult();
    }


    public T findByTable(String table, String value) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        query.where(criteriaBuilder.equal(root.get(table),value));

        List<T> results = getEntityManager().createQuery(query).getResultList();
        if(results.size() > 0) return results.get(0);
        return null;
    }
    
}
