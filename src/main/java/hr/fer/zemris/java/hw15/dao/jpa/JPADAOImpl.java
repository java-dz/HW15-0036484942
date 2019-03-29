package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TransactionRequiredException;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * This class is a concrete implementation of the {@linkplain DAO} interface. It
 * works with an {@linkplain EntityManager} from the {@linkplain JPAEMProvider}
 * to find entities, persist objects, and to create and remove queries.
 *
 * @author Mario Bobic
 */
public class JPADAOImpl implements DAO {

    @Override
    public BlogEntry getBlogEntry(Long id) throws DAOException {
        return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
    }

    @Override
    public BlogComment getBlogComment(Long id) throws DAOException {
        return JPAEMProvider.getEntityManager().find(BlogComment.class, id);
    }

    @Override
    public BlogUser getBlogUser(Long id) throws DAOException {
        return JPAEMProvider.getEntityManager().find(BlogUser.class, id);
    }

    @Override
    public void addBlogEntry(BlogEntry entry) throws DAOException {
        JPAEMProvider.getEntityManager().persist(entry);
    }

    @Override
    public void addBlogComment(BlogComment comment) throws DAOException {
        JPAEMProvider.getEntityManager().persist(comment);
    }

    @Override
    public void addBlogUser(BlogUser user) throws DAOException {
        JPAEMProvider.getEntityManager().persist(user);
    }

    @Override
    public BlogUser getUserByNick(String nick) throws DAOException {
        BlogUser user;

        try {
            user = (BlogUser) JPAEMProvider.getEntityManager()
                    .createQuery("SELECT u FROM BlogUser AS u WHERE u.nick=:nick")
                    .setParameter("nick", nick)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException("Error occured while fetching user by nick.", e);
        }

        return user;
    }

    @Override
    public BlogUser getUserByEmail(String email) throws DAOException {
        BlogUser user;

        try {
            user = (BlogUser) JPAEMProvider.getEntityManager()
                    .createQuery("SELECT u FROM BlogUser AS u WHERE u.email=:email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException("Error occured while fetching user by email.", e);
        }

        return user;
    }

    @Override
    public List<BlogUser> getUsers() throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();

        @SuppressWarnings("unchecked")
        List<BlogUser> users = em.createQuery("SELECT u FROM BlogUser AS u").getResultList();

        return users;
    }

    @Override
    public void removeBlogEntry(Long id) throws DAOException {
        BlogEntry entry = getBlogEntry(id);
        try {
            JPAEMProvider.getEntityManager().remove(entry);
        } catch (TransactionRequiredException ignorable) {}
    }

    @Override
    public void removeBlogComment(Long id) throws DAOException {
        BlogComment comment = getBlogComment(id);
        try {
            JPAEMProvider.getEntityManager().remove(comment);
        } catch (TransactionRequiredException ignorable) {}
    }

}
