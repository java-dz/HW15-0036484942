package hr.fer.zemris.java.hw15.model;

import static hr.fer.zemris.java.hw15.util.ServletUtil.EMAIL_LEN;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * This class represents a single blog user. Instances of this class serve as
 * objects to the object-oriented tasks, such as Servlets, and also as entities
 * to the blog database. Concretely, this class represents a
 * <tt>blog_users</tt> table in the database.
 * <p>
 * A single blog user object contains the following information:
 * <ol>
 * <li>the user ID, auto-generated on every object instantiation,
 * <li>first name of the user,
 * <li>last name of the user,
 * <li>nickname of the user,
 * <li>email of the user,
 * <li>a SHA-1 40 character long password hash of the user's password and
 * <li>a list of blog entries created by the user.
 * </ol>
 * <p>
 * All of the parameters above have corresponding getters and setters. String
 * constants in this class, which are represented by a <tt>VARCHAR</tt> when
 * stored in the database, have corresponding public static final fields
 * containing the length of <tt>VARCHAR</tt> that will be used by the database.
 *
 * @author Mario Bobic
 */
@Entity
@Table(name="blog_users")
@Cacheable(true)
public class BlogUser {

	/** Maximum length of the blog user first name. */
	public static final int FN_LEN = 30;

	/** Maximum length of the blog user last name. */
	public static final int LN_LEN = 40;

	/** Maximum length of the blog user nickname. */
	public static final int NICK_LEN = 40;

	/** ID of the blog user. */
	@Id @GeneratedValue
	private Long id;

	/** First name of the blog user. */
	@Column(nullable=false, length=FN_LEN)
	private String firstName;

	/** Last name of the blog user. */
	@Column(nullable=false, length=LN_LEN)
	private String lastName;

	/** Nickname of the blog user. */
	@Column(nullable=false, length=NICK_LEN, unique=true)
	private String nick;

	/** E-mail of the blog user. */
	@Column(nullable=false, length=EMAIL_LEN, unique=true)
	private String email;

	/** SHA-1 40 character long password hash of the user's password. */
	@Column(nullable=false, length=40)
	private String passwordHash;
	
	/** A list of blog entries created by this user. */
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("createdAt DESC")
	private List<BlogEntry> blogEntries = new ArrayList<>();
	
	/**
	 * Default constructor. Constructs an instance of {@code BlogUser} with
	 * <tt>null</tt> values.
	 */
	public BlogUser() {
	}

	/**
	 * Constructs an instance of {@code BlogUser} with the specified parameters.
	 * 
	 * @param firstName first name of the blog user
	 * @param lastName last name of the blog user
	 * @param nick nickname of the blog user
	 * @param email email of the blog user
	 * @param passwordHash SHA-1 password hash of the user's password
	 */
	public BlogUser(String firstName, String lastName, String nick, String email, String passwordHash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	/**
	 * Returns the ID of the blog user.
	 *
	 * @return the ID of the blog user
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the ID of the blog user.
	 *
	 * @param id the new ID of the blog user
	 */
	public void setId(Long id) {
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
	
	/**
	 * Returns the list of blog entries created by this user.
	 *
	 * @return the list of blog entries created by this user
	 */
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Sets the list of blog entries created by this user.
	 *
	 * @param blogEntries the new list of blog entries created by this user
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BlogUser))
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
