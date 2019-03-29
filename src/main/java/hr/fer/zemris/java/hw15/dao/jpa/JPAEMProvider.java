package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;
import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * This class is a commonly used <tt>JPA</tt> {@linkplain EntityManager}
 * provider associated with the {@linkplain JPAEMFProvider}. The entity managers
 * are obtained through the <tt>JPAEMFProvider</tt>'s entity manager factory.
 *
 * @author Mario Bobic
 */
public class JPAEMProvider {

    /** ThreadLocal object containing local data. */
    private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

    /**
     * Returns a newly created instance of the <tt>EntityManager</tt>.
     *
     * @return an instance of <tt>EntityManager</tt>
     */
    public static EntityManager getEntityManager() {
        LocalData ldata = locals.get();

        if (ldata == null) {
            ldata = new LocalData();
            ldata.em = JPAEMFProvider.getEmf().createEntityManager();
            ldata.em.getTransaction().begin();
            locals.set(ldata);
        }

        return ldata.em;
    }

    /**
     * Closes the provider, releasing any resources that it holds.
     *
     * @throws DAOException if a transaction exception occurs
     */
    public static void close() throws DAOException {
        LocalData ldata = locals.get();
        if (ldata == null) {
            return;
        }

        DAOException dex = null;
        try {
            ldata.em.getTransaction().commit();
        } catch (Exception ex) {
            dex = new DAOException("Unable to commit transaction.", ex);
        }
        try {
            ldata.em.close();
        } catch (Exception ex) {
            if (dex!=null) {
                dex = new DAOException("Unable to close entity manager.", ex);
            }
        }

        locals.remove();
        if (dex != null) {
            throw dex;
        }
    }

    /**
     * This class holds a single package-private field, the
     * {@linkplain EntityManager}, representing the local data.
     *
     * @author Mario Bobic
     */
    private static class LocalData {
        /** An entity manager. */
        EntityManager em;
    }

}
