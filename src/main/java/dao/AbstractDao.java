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

    /**
     * Methode abstraite a definir dans chaque sous-classe qui renvoie
     * l'EntityManager correspondant a la classe.
     *
     * @return l'entity manager
     */
    protected EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }

    /**
     * Methode de creation d'un objet (ajout dans la base).
     *
     * @param entite
     */
    public T create(T entite) throws HeuristicRollbackException, SystemException, HeuristicMixedException, RollbackException, NotSupportedException, NamingException {
        final EntityManager em = getEntityManager();
        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        em.persist(entite);
        em.joinTransaction();
        transaction.commit();
        return entite;
    }

    /**
     * Methode de modification d'un objet.
     *
     * @param entite
     */
    public void edit(T entite) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException, NamingException {
        final EntityManager em = getEntityManager();
        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        em.merge(entite);
        em.joinTransaction();
        transaction.commit();
    }

    /**
     * Methode de suppression d'un objet.
     *
     * @param entite
     */
    public void remove(T entite) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException, NamingException {
        final EntityManager em = getEntityManager();
        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.begin();
        em.remove(getEntityManager().merge(entite));
        em.joinTransaction();
        transaction.commit();
    }

    /**
     * Methode de recherche d'un objet a partir de son identifiant.
     *
     * @param id
     * @return
     * @return
     */
    public T find(Object id) {
        return getEntityManager().find(clazz, id);
    }

    /**
     * Methode recherchant tous les objets de ce type.
     *
     * @return
     */
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

    /**
     * Methode renvoyant le nombre d'objet de ce type.
     *
     * @return
     */
    public long count() {
        final EntityManager em = getEntityManager();
        final CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
        final javax.persistence.criteria.Root<Long> rt = cq.from(Long.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return (Long) q.getSingleResult();
    }

    /**
     * Return an instance of the Entity by table value
     * @param table
     * @param value
     * @return
     */
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
