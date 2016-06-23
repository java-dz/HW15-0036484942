package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Singleton class that uses a single {@linkplain DAO} object as a provider of the
 * data persistence access.
 *
 * @author Mario Bobic
 */
public class DAOProvider {

	/** DAO singleton object. */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Returns an instance of <tt>DAO</tt>.
	 * 
	 * @return object that encapsulates access to the data persistence layer
	 */
	public static DAO getDAO() {
		return dao;
	}

}