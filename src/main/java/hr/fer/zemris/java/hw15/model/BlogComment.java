package hr.fer.zemris.java.hw15.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import static hr.fer.zemris.java.hw15.util.ServletUtil.EMAIL_LEN;

/**
 * This class represents a single blog comment. Instances of this class serve as
 * objects to the object-oriented tasks, such as Servlets, and also as entities
 * to the blog database. Concretely, this class represents a
 * <tt>blog_comments</tt> table in the database.
 * <p>
 * A single blog comment object contains the following information:
 * <ol>
 * <li>the comment ID, auto-generated on every object instantiation,
 * <li>a blog entry that it belongs to,
 * <li>an email of the user that posted the comment,
 * <li>message of the comment and
 * <li>date and time the comment was posted on.
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
@Table(name="blog_comments")
public class BlogComment {

    /** Maximum length of the blog comment message. */
    public static final int MESSAGE_LEN = 2*1024;

    /** ID of the blog comment. */
    @Id @GeneratedValue
    private Long id;

    /** Blog entry to which this comment belongs to. */
    @ManyToOne
    @JoinColumn(nullable=false)
    private BlogEntry blogEntry;

    /** E-mail address of the user that posted the comment. */
    @Column(nullable=false, length=EMAIL_LEN)
    private String usersEMail;

    /** Comment message. */
    @Column(nullable=false, length=MESSAGE_LEN)
    private String message;

    /** Date on which this comment has been posted on. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date postedOn;

    /**
     * Returns the ID of the blog comment.
     *
     * @return the ID of the blog comment
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the blog comment.
     *
     * @param id the new ID of the blog comment
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the blog entry to which this comment belongs to.
     *
     * @return the blog entry to which this comment belongs to
     */
    public BlogEntry getBlogEntry() {
        return blogEntry;
    }

    /**
     * Sets the blog entry to which this comment belongs to.
     *
     * @param blogEntry the new blog entry to which this comment belongs to
     */
    public void setBlogEntry(BlogEntry blogEntry) {
        this.blogEntry = blogEntry;
    }

    /**
     * Returns the e-mail address of the user that posted the comment.
     *
     * @return the e-mail address of the user that posted the comment
     */
    public String getUsersEMail() {
        return usersEMail;
    }

    /**
     * Sets the e-mail address of the user that posted the comment.
     *
     * @param usersEMail the new e-mail address of the user that posted the comment
     */
    public void setUsersEMail(String usersEMail) {
        this.usersEMail = usersEMail;
    }

    /**
     * Returns the comment message.
     *
     * @return the comment message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the comment message.
     *
     * @param message the new comment message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the date on which this comment has been posted on.
     *
     * @return the date on which this comment has been posted on
     */
    public Date getPostedOn() {
        return postedOn;
    }

    /**
     * Sets the date on which this comment has been posted on.
     *
     * @param postedOn the new date on which this comment has been posted on
     */
    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
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
        if (getClass() != obj.getClass())
            return false;
        BlogComment other = (BlogComment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
