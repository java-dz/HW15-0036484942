package hr.fer.zemris.java.hw15.util;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A form model that suits the web-representation of a blog entry domain object.
 * This class implements the {@linkplain AbstractForm} which offers a unique way
 * of handling errors, which are stored in a map and can later be fetched to
 * show the user an error message of the problem.
 * <p>
 * This form offers the following functionality:
 * <ul>
 * <li>Filling the form from the current request (
 * {@linkplain #fillFromHttpRequest(HttpServletRequest)}). The method reads all
 * necessary parameters and fills in appropriate properties in the form.
 * <li>Filling the form from a blog entry (
 * {@linkplain #fillFromBlogEntry(BlogEntry)}). The method reads all blog entry
 * parameters and fills the form appropriately.
 * <li>Filling the blog entry from this form (
 * {@linkplain #fillBlogEntry(BlogEntry)}). The method stores all form
 * parameters into the specified blog entry.
 * </ul>
 * <p>
 * This form also sets the date created of the blog entry to the current one.
 *
 * @author Mario Bobic
 */
public class BlogEntryForm extends AbstractForm {

	/** A tracked blog entry object of this form. */
	private BlogEntry blogEntry;
	
	@Override
	public void fillFromHttpRequest(HttpServletRequest req) {
		blogEntry = new BlogEntry();

		blogEntry.setCreatedAt(new Date());
		blogEntry.setTitle(prepare(req.getParameter("title")));
		blogEntry.setText(prepare(req.getParameter("text")));
		blogEntry.setCreator((BlogUser)req.getAttribute("author"));
	}

	/**
	 * This method reads all blog <tt>entry</tt> parameters and fills the form
	 * appropriately.
	 * 
	 * @param entry a blog entry
	 */
	public void fillFromBlogEntry(BlogEntry entry) {
		blogEntry = new BlogEntry();

		blogEntry.setId(entry.getId());
		blogEntry.setComments(entry.getComments());
		blogEntry.setCreatedAt(entry.getCreatedAt());
		blogEntry.setLastModifiedAt(entry.getLastModifiedAt());
		blogEntry.setTitle(prepare(entry.getTitle()));
		blogEntry.setText(prepare(entry.getText()));
		blogEntry.setCreator(entry.getCreator());
	}

	/**
	 * This method stores all form parameters into the specified blog entry.
	 * 
	 * @param entry a blog entry
	 */
	public void fillBlogEntry(BlogEntry entry) {
		entry.setId(blogEntry.getId());
		entry.setComments(blogEntry.getComments());
		entry.setCreatedAt(blogEntry.getCreatedAt());
		entry.setLastModifiedAt(blogEntry.getLastModifiedAt());
		entry.setTitle(blogEntry.getTitle());
		entry.setText(blogEntry.getText());
		entry.setCreator(blogEntry.getCreator());
	}
	
	@Override
	public void validate() {
		errors.clear();
		
		if (blogEntry.getTitle().isEmpty()) {
			errors.put("title", "Title is required field!");
		} else if (blogEntry.getTitle().length() > BlogEntry.TITLE_LEN) {
			errors.put("title", "Title is too long. Use at most " + BlogEntry.TITLE_LEN + " characters.");
		}
		
		if (blogEntry.getText().isEmpty()) {
			errors.put("text", "Text is required field!");
		} else if (blogEntry.getText().length() > BlogEntry.TEXT_LEN) {
			errors.put("text", "Text is too long. Use at most " + BlogEntry.TEXT_LEN + " characters.");
		}
	}
	
	/**
	 * Returns the ID of the blog entry.
	 *
	 * @return the ID of the blog entry
	 */
	public Long getId() {
		return blogEntry.getId();
	}
	
	/**
	 * Returns the list of blog comments on this entry.
	 *
	 * @return the list of blog comments on this entry
	 */
	public List<BlogComment> getComments() {
		return blogEntry.getComments();
	}
	
	/**
	 * Returns the date on which this entry was created at.
	 *
	 * @return the date on which this entry was created at
	 */
	public Date getCreatedAt() {
		return blogEntry.getCreatedAt();
	}
	
	/**
	 * Returns the date on which this entry was last modified at.
	 *
	 * @return the date on which this entry was last modified at
	 */
	public Date getLastModifiedAt() {
		return blogEntry.getLastModifiedAt();
	}

	/**
	 * Returns the title of the blog entry.
	 *
	 * @return the title of the blog entry
	 */
	public String getTitle() {
		return blogEntry.getTitle();
	}

	/**
	 * Returns the text of the blog entry.
	 *
	 * @return the text of the blog entry
	 */
	public String getText() {
		return blogEntry.getText();
	}
	
	/**
	 * Returns the creator of the blog entry.
	 *
	 * @return the creator of the blog entry
	 */
	public BlogUser getCreator() {
		return blogEntry.getCreator();
	}
	
}
