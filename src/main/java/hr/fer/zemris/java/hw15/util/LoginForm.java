package hr.fer.zemris.java.hw15.util;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.http.HttpServletRequest;

/**
 * A form model that suits the web-representation of a login form. This class
 * implements the {@linkplain AbstractForm} which offers a unique way of
 * handling errors, which are stored in a map and can later be fetched to show
 * the user an error message of the problem.
 * <p>
 * This form offers the following functionality:
 * <ul>
 * <li>Filling the form from the current request (
 * {@linkplain #fillFromHttpRequest(HttpServletRequest)}). The method reads all
 * necessary parameters and fills in appropriate properties in the form.
 * <li>Filling the form from a blog user (
 * {@linkplain #fillFromBlogUser(BlogUser)}). The method reads necessary blog
 * user parameters and fills the form appropriately.
 * <li>Filling the blog user from this form (
 * {@linkplain #fillBlogUser(BlogUser)}). The method stores necessary form
 * parameters into the specified blog entry.
 * </ul>
 *
 * @author Mario Bobic
 */
public class LoginForm extends AbstractForm {

    /** Nickname of the blog user. */
    private String nick;

    /** Password of the blog user (stored temporarily). */
    private String password;

    /** SHA-1 40 character long password hash of the user's password. */
    private String passwordHash;

    @Override
    public void fillFromHttpRequest(HttpServletRequest req) {
        nick = prepare(req.getParameter("nick"));
        password = prepare(req.getParameter("password"));
        passwordHash = ServletUtil.generatePasswordHash(password);
    }

    /**
     * This method reads necessary blog <tt>user</tt> parameters and fills the
     * form appropriately.
     *
     * @param user a blog user
     */
    public void fillFromBlogUser(BlogUser user) {
        nick = user.getNick();
        password = null;
        passwordHash = user.getPasswordHash();
    }

    /**
     * This method stores necessary form parameters into the specified blog
     * user.
     *
     * @param user a blog user
     */
    public void fillBlogUser(BlogUser user) {
        user.setNick(nick);
        user.setPasswordHash(passwordHash);
    }

    @Override
    public void validate() {
        errors.clear();

        if (nick.isEmpty()) {
            errors.put("nick", "Nickname is required field!");
        } else {
            BlogUser user = DAOProvider.getDAO().getUserByNick(nick);
            if (user == null) {
                errors.put("nick", "Nickname does not exist!");
            }
        }

        if (password != null && password.isEmpty()) {
            errors.put("password", "Password is required field!");
        } else {
            BlogUser user = DAOProvider.getDAO().getUserByNick(nick);
            if (user != null && !passwordHash.equals(user.getPasswordHash())) {
                errors.put("password", "Password is incorrect.");
                System.out.println("Entered password: " + password);
                System.out.println("Entered password hash: " + passwordHash);

                System.out.println("User password hash:    " + user.getPasswordHash());
            }
        }
    }

    /**
     * Returns the nickname of the blog user.
     *
     * @return the nickname of the blog user
     */
    public String getNick() {
        return nick;
    }

    /**
     * Sets the nickname of the blog user.
     *
     * @param nick the new nickname of the blog user
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Returns the SHA-1 40 character long password hash of the user's password.
     *
     * @return the SHA-1 40 character long password hash of the user's password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the SHA-1 password hash of the user's password.
     *
     * @param passwordHash the new SHA-1 password hash of the user's password
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

}
