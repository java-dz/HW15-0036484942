package hr.fer.zemris.java.hw15.util;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A form model that suits the web-representation of a registration form. This class
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
public class RegistrationForm extends AbstractForm {

    /** ID of the blog user. */
    private String id;

    /** First name of the blog user. */
    private String firstName;

    /** Last name of the blog user. */
    private String lastName;

    /** Nickname of the blog user. */
    private String nick;

    /** E-mail of the blog user. */
    private String email;

    /** Password of the blog user (stored temporarily). */
    private String password;

    /** SHA-1 40 character long password hash of the user's password. */
    private String passwordHash;

    @Override
    public void fillFromHttpRequest(HttpServletRequest req) {
        id = prepare(req.getParameter("id"));
        firstName = prepare(req.getParameter("fn"));
        lastName = prepare(req.getParameter("ln"));
        nick = prepare(req.getParameter("nick"));
        email = prepare(req.getParameter("email"));
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
        if (user.getId() == null) {
            id = "";
        } else {
            id = user.getId().toString();
        }

        firstName = user.getFirstName();
        lastName = user.getLastName();
        nick = user.getNick();
        email = user.getEmail();
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
        if(id.isEmpty()) {
            user.setId(null);
        } else {
            user.setId(Long.valueOf(id));
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNick(nick);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
    }

    @Override
    public void validate() {
        errors.clear();

        if (!id.isEmpty()) {
            try {
                Long.parseLong(id);
            } catch (NumberFormatException e) {
                errors.put("id", "ID value is not valid.");
            }
        }

        if (firstName.isEmpty()) {
            errors.put("fn", "First name is required field!");
        } else if (firstName.length() > BlogUser.FN_LEN) {
            errors.put("fn", "First name is too long. Use at most " + BlogUser.FN_LEN + " characters.");
        }

        if (lastName.isEmpty()) {
            errors.put("ln", "Last name is required field!");
        } else if (lastName.length() > BlogUser.LN_LEN) {
            errors.put("ln", "Last name is too long. Use at most " + BlogUser.LN_LEN + " characters.");
        }

        if (nick.isEmpty()) {
            errors.put("nick", "Nickname is required field!");
        } else if (nick.length() > BlogUser.NICK_LEN) {
            errors.put("nick", "Nickname is too long. Use at most " + BlogUser.NICK_LEN + " characters.");
        } else {
            BlogUser user = DAOProvider.getDAO().getUserByNick(nick);
            if (user != null) {
                errors.put("nick", "Nickname already exists!");
            }
        }

        if (email.isEmpty()) {
            errors.put("email", "E-mail is required field!");
        } else if (!ServletUtil.isEmailValid(email)) {
            errors.put("email", "E-mail format is incorrect.");
        } else {
            BlogUser user = DAOProvider.getDAO().getUserByEmail(email);
            if (user != null) {
                errors.put("email", "E-mail already exists!");
            }
        }

        if (password != null && password.isEmpty()) {
            errors.put("password", "Password is required field!");
        }
    }

    /**
     * Returns the ID of the blog user.
     *
     * @return the ID of the blog user
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the blog user.
     *
     * @param id the new ID of the blog user
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the first name of the blog user.
     *
     * @return the first name of the blog user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the blog user.
     *
     * @param firstName the new first name of the blog user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the blog user.
     *
     * @return the last name of the blog user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the blog user.
     *
     * @param lastName the new last name of the blog user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Returns the e-mail of the blog user.
     *
     * @return the e-mail of the blog user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the e-mail of the blog user.
     *
     * @param email the new e-mail of the blog user
     */
    public void setEmail(String email) {
        this.email = email;
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
