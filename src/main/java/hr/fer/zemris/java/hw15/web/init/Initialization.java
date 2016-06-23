package hr.fer.zemris.java.hw15.web.init;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMFProvider;

/**
 * This class is an initialization {@link WebListener web listener} that creates
 * an entity manager factory and sets it to the {@linkplain JPAEMFProvider} upon
 * context initialization and destroys the entity manager factory by setting it
 * to <tt>null</tt> using the
 * {@linkplain JPAEMFProvider#setEmf(EntityManagerFactory)} and then closing the
 * entity manager factory.
 *
 * @author Mario Bobic
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");  
		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory)sce.getServletContext().getAttribute("my.application.emf");
		if (emf != null) {
			emf.close();
		}
	}
}