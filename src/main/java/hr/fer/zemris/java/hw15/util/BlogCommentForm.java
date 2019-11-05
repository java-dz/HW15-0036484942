package hr.fer.zemris.java.hw15.util;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * A form model that suits the web-representation of a blog comment domain
 * object. This class implements the {@linkplain AbstractForm} which offers a
 * unique way of handling errors, which are stored in a map and can later be
 * fetched to show the user an error message of the problem.
 * <p>
 * This form offers the following functionality:
 * <ul>
 * <li>Filling the form from the current request (
 * {@linkplain #fillFromHttpRequest(HttpServletRequest)}). The method reads all
 * necessary parameters and fills in appropriate properties in the form.
 * <li>Filling the form from a blog comment (
 * {@linkplain #fillFromBlogComment(BlogComment)}). The method reads all blog
 * comment parameters and fills the form appropriately.
 * <li>Filling the blog comment from this form (
 * {@linkplain #fillBlogComment(BlogComment)}). The method stores all form
 * parameters into the specified blog comment.
 * </ul>
 * <p>
 * This form also sets the date of the blog comment to the current one.
 *
 * @author Mario Bobic
 */
public class BlogCommentForm extends AbstractForm {

    /** A tracked blog comment object of this form. */
    private BlogComment blogComment;

    /**
     * Flag fetched from request parameters that indicates if the user is logged
     * in. If the user is not logged in, he can not use an e-mail address that
     * is already in use.
     */
    private boolean userLoggedIn;

    @Override
    public void fillFromHttpRequest(HttpServletRequest req) {
        blogComment = new BlogComment();

        Long id = Long.parseLong(req.getParameter("entryID"));

        blogComment.setBlogEntry(DAOProvider.getDAO().getBlogEntry(id));
        blogComment.setUsersEMail(req.getParameter("email"));
        blogComment.setMessage(req.getParameter("message"));
        blogComment.setPostedOn(new Date());

        userLoggedIn = Boolean.parseBoolean(req.getParameter("loggedIn"));
    }

    /**
     * This method reads all blog <tt>comment</tt> parameters and fills the form
     * appropriately.
     *
     * @param comment a blog comment
     */
    public void fillFromBlogComment(BlogComment comment) {
        blogComment = new BlogComment();

        blogComment.setId(comment.getId());
        blogComment.setBlogEntry(comment.getBlogEntry());
        blogComment.setUsersEMail(comment.getUsersEMail());
        blogComment.setMessage(comment.getMessage());
        blogComment.setPostedOn(comment.getPostedOn());
    }

    /**
     * This method stores all form parameters into the specified blog comment.
     *
     * @param comment a blog comment
     */
    public void fillBlogComment(BlogComment comment) {
        comment.setId(blogComment.getId());
        comment.setBlogEntry(blogComment.getBlogEntry());
        comment.setUsersEMail(blogComment.getUsersEMail());
        comment.setMessage(blogComment.getMessage());
        comment.setPostedOn(blogComment.getPostedOn());
    }

    @Override
    public void validate() {
        errors.clear();

        String email = blogComment.getUsersEMail();

        if (email.isEmpty()) {
            errors.put("email", "E-mail is required field!");
        } else if (!ServletUtil.isEmailValid(email)) {
            errors.put("email", "E-mail format is incorrect.");
        } else if (!userLoggedIn && DAOProvider.getDAO().getUserByEmail(email) != null) {
            errors.put("email", "E-mail address is already in use. Log in to add comment with this e-mail.");
        }

        if (blogComment.getMessage().isEmpty()) {
            errors.put("message", "Comment message is required field!");
        } else if (blogComment.getMessage().length() > BlogComment.MESSAGE_LEN) {
            errors.put("message", "Comment message is too long. Use at most " + BlogComment.MESSAGE_LEN + " characters.");
        }
    }

    /**
     * Returns the ID of the blog comment.
     *
     * @return the ID of the blog comment
     */
    public Long getId() {
        return blogComment.getId();
    }

    /**
     * Returns the blog entry to which this comment belongs to.
     *
     * @return the blog entry to which this comment belongs to
     */
    public BlogEntry getBlogEntry() {
        return blogComment.getBlogEntry();
    }

    /**
     * Returns the e-mail address of the user that posted the comment.
     *
     * @return the e-mail address of the user that posted the comment
     */
    public String getUsersEMail() {
        return blogComment.getUsersEMail();
    }

    /**
     * Returns the comment message.
     *
     * @return the comment message
     */
    public String getMessage() {
        return blogComment.getMessage();
    }

    /**
     * Returns the date on which this comment has been posted on.
     *
     * @return the date on which this comment has been posted on
     */
    public Date getPostedOn() {
        return blogComment.getPostedOn();
    }

}
