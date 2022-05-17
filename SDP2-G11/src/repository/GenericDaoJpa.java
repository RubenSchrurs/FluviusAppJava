package repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GenericDaoJpa<T> implements GenericDao<T> 
{

    private static final String PU_NAME = "project";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);
    protected static final EntityManager em = emf.createEntityManager();
    
    private final Class<T> type;
    
    public GenericDaoJpa(Class<T> type) {
        this.type = type;
    }
    
    public static void startTransaction() {
        em.getTransaction().begin();
    }
    
    public static void commitTransaction() {
        em.getTransaction().commit();
    }
    
    public static void rollbackTransaction() {
        em.getTransaction().rollback();
    }
    
    public static void closePersistency() {
        em.close();
        emf.close();
    }

    @Override
    public List<T> findAll() {
        return em.createQuery("SELECT entity FROM " + type.getName() + " entity", type).getResultList();
    }

    @Override
    public T get(Long id) {
        return em.find(type, id);
    }

    @Override
    public T update(T object) {
        return em.merge(object);
    }

    @Override
    public void delete(T object) {
        em.remove(em.merge(object));
    }

    @Override
    public void insert(T object) {
        em.persist(object);
    }

    @Override
    public boolean exists(Long id) {
        T entity = em.find(type, id);
        
        return entity != null;
    } 
}

