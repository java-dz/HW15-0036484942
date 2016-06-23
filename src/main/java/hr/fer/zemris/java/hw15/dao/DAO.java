package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Interface that provides data persistence.
 * 
 * @author Mario Bobic
 */
public interface DAO {

	/**
	 * Returns an entry with the specified <tt>id</tt>. If an entry with the
	 * specified id is not found, <tt>null</tt> is returned.
	 * 
	 * @param id entry id
	 * @return a <tt>BlogEntry</tt> with the specified <tt>id</tt>
	 * @throws DAOException if a transaction exception occurs
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Returns a comment with the specified <tt>id</tt>. If a comment with the
	 * specified id is not found, <tt>null</tt> is returned.
	 * 
	 * @param id comment id
	 * @return a <tt>BlogComment</tt> with the specified <tt>id</tt>
	 * @throws DAOException if a transaction exception occurs
	 */
	public BlogComment getBlogComment(Long id) throws DAOException;
	
	/**
	 * Returns a user with the specified <tt>id</tt>. If a user with the
	 * specified id is not found, <tt>null</tt> is returned.
	 * 
	 * @param id user id
	 * @return a <tt>BlogUser</tt> with the specified <tt>id</tt>
	 * @throws DAOException if a transaction exception occurs
	 */
	public BlogUser getBlogUser(Long id) throws DAOException;

	/**
	 * Adds the specified blog <tt>entry</tt> to the <tt>blog_entries</tt> table
	 * in the database by persisting the specified object.
	 * 
	 * @param entry entry to be added to the database
	 * @throws DAOException if a transaction exception occurs
	 */
	public void addBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Adds the specified blog <tt>comment</tt> to the <tt>blog_comments</tt>
	 * table in the database by persisting the specified object.
	 * 
	 * @param comment comment to be added to the database
	 * @throws DAOException if a transaction exception occurs
	 */
	public void addBlogComment(BlogComment comment) throws DAOException;
	
	/**
	 * Adds the specified blog <tt>user</tt> to the <tt>blog_users</tt> table in
	 * the database by persisting the specified object.
	 * 
	 * @param user user to be added to the database
	 * @throws DAOException if a transaction exception occurs
	 */
	public void addBlogUser(BlogUser user) throws DAOException;

	/**
	 * Returns a user with the specified nickname <tt>nick</tt>. If a user with
	 * the specified nickname is not found, <tt>null</tt> is returned.
	 * 
	 * @param nick user nickname
	 * @return a <tt>BlogUser</tt> with the specified <tt>nick</tt>
	 * @throws DAOException if a transaction exception occurs
	 */
	public BlogUser getUserByNick(String nick) throws DAOException;

	/**
	 * Returns a user with the specified <tt>email</tt>. If a user with the
	 * specified email is not found, <tt>null</tt> is returned.
	 * 
	 * @param email user email
	 * @return a <tt>BlogUser</tt> with the specified <tt>email</tt>
	 * @throws DAOException if a transaction exception occurs
	 */
	public BlogUser getUserByEmail(String email) throws DAOException;

	/**
	 * Returns a <tt>List</tt> of blog users stored in the database. If no users
	 * exist in the database, an empty list is returned.
	 * 
	 * @return a <tt>List</tt> of blog users stored in the database
	 * @throws DAOException if a transaction exception occurs
	 */
	public List<BlogUser> getUsers() throws DAOException;
	
	/**
	 * Removes a blog entry with the specified <tt>id</tt> from the
	 * <tt>blog_entries</tt> table in the database.
	 * <p>
	 * If no blog entry with the specified <tt>id</tt> is found, this
	 * method exits with no modifications whatsoever.
	 * 
	 * @param id id of the blog entry to be removed
	 * @throws DAOException if a transaction exception occurs
	 */
	public void removeBlogEntry(Long id) throws DAOException;
	
	/**
	 * Removes a blog comment with the specified <tt>id</tt> from the
	 * <tt>blog_comments</tt> table in the database.
	 * <p>
	 * If no blog comment with the specified <tt>id</tt> is found, this
	 * method exits with no modifications whatsoever.
	 * 
	 * @param id id of the blog comment to be removed
	 * @throws DAOException if a transaction exception occurs
	 */
	public void removeBlogComment(Long id) throws DAOException;
}