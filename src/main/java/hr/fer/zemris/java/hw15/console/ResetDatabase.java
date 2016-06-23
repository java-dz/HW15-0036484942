package hr.fer.zemris.java.hw15.console;

import java.util.Date;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.util.ServletUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


//
// IMPLEMENTATION NOTE: If this procedure fails to execute, open the ij-console,
// connect as database admin and drop all tables in order for them to be created
// and filled again.
//
// Example:
//  1. connect 'jdbc:derby://localhost:1527/blogBaza;user=blogDBAdmin;password=blogDBPassword';)
//  2. DROP TABLE blog_comments;
//  3. DROP TABLE blog_entries;
//  4. DROP TABLE blog_users;
//
/**
 * This program is used for resetting the database state to an initial one. One
 * blog user is added and five blog entries are added to the user (or by the
 * user). Afterwards, blog comments are added, each in <tt>500</tt> millisecond
 * interval, which will later be ordered by date posted in descending order.
 * <p>
 * The added user is a test user whose nickname is <tt>perica</tt> and password
 * is <tt>perica</tt>. You can log into that user and modify entries that were
 * created here.
 * <p>
 * This program prints out working status onto the standard error, with each step
 * described with starting and ending progress.
 *
 * @author Mario Bobic
 */
public class ResetDatabase {

	/**
	 * Program entry point.
	 * 
	 * @param args not used
	 * @throws InterruptedException ignored in single-threaded application
	 */
	public static void main(String[] args) throws InterruptedException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");  

		// Step 0 - clear all previous entries !!!CAUTION!!!
		// -----------------------------------------
		clearDatabase(emf);
		
		// Step 1 - creating new users...
		// -----------------------------------------
		BlogUser user = addPerica(emf);
		
		// step 2 - creating new blog posts...
		// -----------------------------------------
		BlogEntry blogEntry1 = addEntry(emf, user, "Moj prvi blog", "Ovo je moj prvi blog zapis.");
		Thread.sleep(500);
		BlogEntry blogEntry2 = addEntry(emf, user, "Objava koja ima nove redove", "Ova\nobjava\nje\nproširena\nkroz\nvišestruke       \nnove\n			redove");
		Thread.sleep(500);
		BlogEntry blogEntry3 = addEntry(emf, user, "Stylesheet (CSS3)", "Isječak CSS-a:\n\n.error {\n\tfont-family: arial;\n\tfont-style: italic;\n\tfont-size: 0.9em;\n\tcolor: #FF0000;\n}");
		Thread.sleep(500);
		BlogEntry blogEntry4 = addEntry(emf, user, "Ovo je jedan blog post koji ima jako jako dugačak naslov, (ima točno 80 znakova)", "Ovaj naslov ima točno 80 znakova, izbrojao sam <a href=\"http://www.lettercount.com/\">ovdje</a>.\n  Kad bi naslov bio duži od 80 znakova, ovo bi se desilo:\n  <hr width=\"95%\">\n  <img src=\"http://www.deviantpics.com/images/2016/06/22/too_long.png\" alt=\"Title too long\" />\n  <hr width=\"95%\">");
		Thread.sleep(500);
		BlogEntry blogEntry5 = addEntry(emf, user, "Mačke", "<img src=\"http://i.imgur.com/FAl6Tks.jpg\" alt=\"Puno puno macaka\" />");
		
		// Step 3 - adding a few comments...
		// -----------------------------------------
		addComment(emf, blogEntry1.getId(), "ivica@ivo.hr", "Blog ti je super!");
		Thread.sleep(500);
		addComment(emf, blogEntry1.getId(), "stefica@stef.org", "Vau!");
		Thread.sleep(500);
		addComment(emf, blogEntry1.getId(), "ivica@ivo.hr", "Još jedan komentar.");
		
		Thread.sleep(500);
		addComment(emf, blogEntry2.getId(), "fanta@stic.com", "Fantastično ti je ovo! Wow!!!");
		
		Thread.sleep(500);
		addComment(emf, blogEntry3.getId(), "w3@schools.com", "A CSS je okej... Mislim može proć.");

		Thread.sleep(500);
		addComment(emf, blogEntry4.getId(), "perica@peric.hr", "Kužite kao tu je slika");
		Thread.sleep(500);
		addComment(emf, blogEntry4.getId(), "ivica@ivo.hr", "kužimo da nismo glupi -.-.-.-.-.-.-.-.-.-..-");
		
		Thread.sleep(500);
		addComment(emf, blogEntry5.getId(), "cat@kittens.co.uk", "wow omg kittens omg i luv cats pls *o*");
		
		emf.close();
	}

	/**
	 * Clears the blog database by deleting <strong>all</strong> blog entries in
	 * this order:
	 * <ol>
	 * <li>first, all blog comments are deleted from the <tt>blog_comments</tt>
	 * table,
	 * <li>second, all blog entries are deleted from the <tt>blog_entries</tt>
	 * table,
	 * <li>third, all blog users are deleted from the <tt>blog_users</tt> table.
	 * </ol>
	 * <p>
	 * The order of deleting is important in case these models don't use the
	 * <tt>CASCADE</tt> delete type.
	 * 
	 * @param emf an entity manager factory
	 */
	private static void clearDatabase(EntityManagerFactory emf) {
		System.err.println("Clearing blog database.");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		int deletedComments = em.createQuery("DELETE FROM BlogComment").executeUpdate();
		System.err.println("Deleted " + deletedComments + " comments.");
		
		int deletedEntries = em.createQuery("DELETE FROM BlogEntry").executeUpdate();
		System.err.println("Deleted " + deletedEntries + " entries.");
		
		int deletedUsers = em.createQuery("DELETE FROM BlogUser").executeUpdate();
		System.err.println("Deleted " + deletedUsers + " users.");
		
		em.getTransaction().commit();
		em.close();

		System.err.println("Finished clearing blog database.");
	}

	/**
	 * Adds the user <tt>perica</tt> to the <tt>blog_users</tt> table in the
	 * blog database using the provided entity manager factory <tt>emf</tt> to
	 * produce an entity manager which is used for the whole transaction
	 * progress. The blog user is returned after the transaction has been
	 * committed.
	 * 
	 * @param emf an entity manager factory
	 * @return the newly created blog user <tt>perica</tt>
	 */
	private static BlogUser addPerica(EntityManagerFactory emf) {
		System.err.println("Adding user perica.");
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		BlogUser blogUser = new BlogUser();
		blogUser.setFirstName("Perica");
		blogUser.setLastName("Perić");
		blogUser.setNick("perica");
		blogUser.setEmail("perica@peric.hr");
		blogUser.setPasswordHash(ServletUtil.generatePasswordHash("perica"));
		
		em.persist(blogUser);
		
		em.getTransaction().commit();
		em.close();

		System.err.println("Finished adding user perica.");
		return blogUser;
	}

	/**
	 * Adds the blog entry created by the specified <tt>creator</tt> with the
	 * specified <tt>title</tt> and <tt>text</tt> to the <tt>blog_entries</tt>
	 * table in the blog database. The blog entry is added using the provided
	 * entity manager factory <tt>emf</tt> to produce an entity manager which is
	 * used for the whole transaction progress. The blog entry is returned after
	 * the transaction has been committed.
	 * 
	 * @param emf an entity manager factory
	 * @param creator the blog entry creator
	 * @param title title of the blog entry
	 * @param text text of the blog entry
	 * @return the newly created blog entry
	 */
	private static BlogEntry addEntry(EntityManagerFactory emf, BlogUser creator, String title, String text) {
		System.err.println("Adding entry " + title);
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		BlogEntry blogEntry = new BlogEntry();
		blogEntry.setCreatedAt(new Date());
		blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
		blogEntry.setTitle(title);
		blogEntry.setText(text);
		blogEntry.setCreator(creator);
		
		em.persist(blogEntry);
		
		em.getTransaction().commit();
		em.close();
		
		System.err.println("Finished adding entry " + title);
		return blogEntry;
	}
	
	/**
	 * Adds the blog comment with the specified <tt>message</tt> to a blog entry
	 * with the specified <tt>blogEntryID</tt> posted by a user with the
	 * specified <tt>email</tt> to the <tt>blog_comments</tt> table in the blog
	 * database. The blog comment is added using the provided entity manager
	 * factory <tt>emf</tt> to produce an entity manager which is used for the
	 * whole transaction progress.
	 * 
	 * @param emf an entity manager factory
	 * @param blogEntryID ID of the blog entry to which the comment is to be added
	 * @param email email of the user posting the comment
	 * @param message comment message
	 */
	private static void addComment(EntityManagerFactory emf, Long blogEntryID, String email, String message) {
		System.err.println("Adding comment by " + email);
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		BlogEntry blogEntry = em.find(BlogEntry.class, blogEntryID);
		
		BlogComment blogComment = new BlogComment();
		blogComment.setUsersEMail(email);
		blogComment.setPostedOn(new Date());
		blogComment.setMessage(message);
		blogComment.setBlogEntry(blogEntry);
		
		em.persist(blogComment);

		blogEntry.getComments().add(blogComment);
		
		em.getTransaction().commit();
		em.close();
		
		System.err.println("Finished adding comment by " + email);
	}
	
}