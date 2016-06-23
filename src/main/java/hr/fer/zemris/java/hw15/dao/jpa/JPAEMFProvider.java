package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * This class is a commonly used <tt>JPA</tt> {@linkplain EntityManagerFactory}
 * provider. Contains a single getter and setter.
 * <p>
 * It contains a cache value of an entity manager factory with the purpose of
 * speeding up the application.
 *
 * @author Mario Bobic
 */
public class JPAEMFProvider {

	/** Current instance of entity manager factory. */
	public static EntityManagerFactory emf;

	/**
	 * Returns an instance of the entity manager factory.
	 * 
	 * @return an instance of the entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the entity manager factory to the specified <tt>emf</tt>.
	 * 
	 * @param emf the entity manager factory to be set
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}