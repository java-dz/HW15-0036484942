package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class represents a single blog entry. Instances of this class serve as
 * objects to the object-oriented tasks, such as Servlets, and also as entities
 * to the blog database. Concretely, this class represents a
 * <tt>blog_entries</tt> table in the database.
 * <p>
 * A single blog entry object contains the following information:
 * <ol>
 * <li>the entry ID, auto-generated on every object instantiation,
 * <li>a list of blog comments on the entry,
 * <li>date and time the entry was created at,
 * <li>date and time the entry was last modified at,
 * <li>title of the entry,
 * <li>text of the entry and
 * <li>a reference on the blog user that created the entry.
 * </ol>
 * <p>
 * All of the parameters above have corresponding getters and setters. String
 * constants in this class, which are represented by a <tt>VARCHAR</tt> when
 * stored in the database, have corresponding public static final fields
 * containing the length of <tt>VARCHAR</tt> that will be used by the database.
 *
 * @author Mario Bobic
 */
@NamedQueries({
    @NamedQuery(name="BlogEntry.upit1",query="SELECT b FROM BlogComment AS b WHERE b.blogEntry=:be AND b.postedOn>:when")
})
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

    /** Maximum length of the blog entry title. */
    public static final int TITLE_LEN = 80;

    /** Maximum length of the blog entry text. */
    public static final int TEXT_LEN = 4*1024;

    /** ID of the blog entry. */
    @Id @GeneratedValue
    private Long id;

    /** A list of blog comments on this entry. */
    @OneToMany(mappedBy="blogEntry", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
    @OrderBy("postedOn DESC")
    private List<BlogComment> comments = new ArrayList<>();

    /** Date on which this entry was created at. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date createdAt;

    /** Date on which this entry was last modified at. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=true)
    private Date lastModifiedAt;

    /** Title of the blog entry. */
    @Column(nullable=false, length=TITLE_LEN)
    private String title;

    /** Text of the blog entry. */
    @Column(nullable=false, length=TEXT_LEN)
    private String text;

    /** Creator of the blog entry. */
    @ManyToOne
    private BlogUser creator;

    /**
     * Returns the ID of the blog entry.
     *
     * @return the ID of the blog entry
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the blog entry.
     *
     * @param id the new ID of the blog entry
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the list of blog comments on this entry.
     *
     * @return the list of blog comments on this entry
     */
    public List<BlogComment> getComments() {
        return comments;
    }

    /**
     * Sets the list of blog comments on this entry.
     *
     * @param comments the new list of blog comments on this entry
     */
    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }

    /**
     * Returns the date on which this entry was created at.
     *
     * @return the date on which this entry was created at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date on which this entry was created at.
     *
     * @param createdAt the new date on which this entry was created at
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the date on which this entry was last modified at.
     *
     * @return the date on which this entry was last modified at
     */
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Sets the date on which this entry was last modified at.
     *
     * @param lastModifiedAt the new date on which this entry was last modified at
     */
    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Returns the title of the blog entry.
     *
     * @return the title of the blog entry
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the blog entry.
     *
     * @param title the new title of the blog entry
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the text of the blog entry.
     *
     * @return the text of the blog entry
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the blog entry.
     *
     * @param text the new text of the blog entry
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the creator of the blog entry.
     *
     * @return the creator of the blog entry
     */
    public BlogUser getCreator() {
        return creator;
    }

    /**
     * Sets the creator of the blog entry.
     *
     * @param creator the new creator of the blog entry
     */
    public void setCreator(BlogUser creator) {
        this.creator = creator;
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
        BlogEntry other = (BlogEntry) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
