package hr.fer.zemris.java.hw15.web.servlets;

import static hr.fer.zemris.java.hw15.util.ServletUtil.parseLong;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.util.BlogCommentForm;
import hr.fer.zemris.java.hw15.util.BlogEntryForm;
import hr.fer.zemris.java.hw15.util.ServletUtil;

/**
 * This servlet represents multiple functions that are mapped to any URL,
 * starting from the base of the web application in addition with
 * <tt>/servleti/author/</tt>.
 * <p>
 * URL addresses processed by the <tt>GET</tt> HTTP method are expected to be
 * some of the following:
 * <ul>
 * <li><tt>/servleti/author/NICK</tt>
 * <li><tt>/servleti/author/NICK/EID</tt>, where <tt>EID</tt> is an entry ID
 * <li><tt>/servleti/author/NICK/new</tt>, if user with nickname NICK is logged in
 * <li><tt>/servleti/author/NICK/edit</tt>, if user with nickname NICK is logged in
 * <li><tt>/servleti/author/NICK/removeComment</tt>, if user with nickname NICK is
 * logged in
 * </ul>
 * <p>
 * Here are some rules for the URL format while processing the <tt>GET</tt> HTTP
 * method, referencing <strong>path</strong> as the part that comes after
 * <tt>/servleti/author/</tt>:
 * <ol>
 * <li>If the path contains no information, it means that even the nickname has
 * not been provided and an error is sent with the appropriate message.
 * <li>If the path contains a number of elements that is different from both
 * <tt>1</tt> and <tt>2</tt>, an error is sent with the appropriate message.
 * <li>If a user with the specified nickname does not exist, an error is sent
 * with the appropriate message.
 * <li>If a user visiting an entry is the author of the entry,
 * <tt>authorOptions</tt> flag is passed as an attribute to show author options.
 * <li>If a user that is not the author of the entry tries to perform <tt>new</tt>,
 * <tt>edit</tt> or <tt>removeComment</tt> option, an error is sent with the
 * appropriate message.
 * <li>Else if none of the reserved URLs are match the user's request URL, an
 * error is sent with the appropriate message.
 * </ol>
 *
 * @author Mario Bobic
 */
@WebServlet(name="author", urlPatterns={"/servleti/author/*"})
public class AuthorServlet extends HttpServlet {
    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get path info, exit if there is none
        String path = req.getPathInfo();
        if (path == null) {
            ServletUtil.sendError(req, resp, "No nickname provided!");
            return;
        }

        // Get path info elements, exit if they differ from 2 and 3
        String[] elements = path.split("/");
        if (elements.length != 2 && elements.length != 3) {
            ServletUtil.sendError(req, resp, "Invalid URL!");
            return;
        }

        // Get user by nickname, exit if user does not exist
        String nickname = elements[1];
        BlogUser user = DAOProvider.getDAO().getUserByNick(nickname);
        if (user == null) {
            ServletUtil.sendError(req, resp, "User with provided nickname does not exist!");
            return;
        }

        // Set attributes for .jsp file
        req.setAttribute("author", user);

        String currentUserNick = (String) req.getSession().getAttribute("current.user.nick");
        // Link example: /servleti/author/NICK
        if (elements.length == 2) {
            if (currentUserNick != null && currentUserNick.equals(user.getNick())) {
                req.setAttribute("authorOptions", true);
            }
        } else {
            // Link example: /servleti/author/NICK/2
            Long id = parseLong(elements[2]);
            if (id != null) {
                BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
                if (blogEntry == null) {
                    ServletUtil.sendError(req, resp, "Blog entry with the specified id does not exist.");
                    return;
                }
                req.setAttribute("blogEntry", blogEntry);
                req.setAttribute("author", blogEntry.getCreator());
                req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
                return;
            } else {
                // Link example: /servleti/author/NICK/new
                if (!Objects.equals(currentUserNick, user.getNick())) {
                    ServletUtil.sendError(req, resp, "You are not authorized to modify this blog.");
                    return;
                } else if (elements[2].equals("new")) {
                    doNew(req, resp);
                    return;
                } else if (elements[2].equals("edit")) {
                    doEdit(req, resp);
                    return;
                } else if (elements[2].equals("removeComment")) {
                    doRemoveComment(req, resp);
                    return;
                } else {
                    ServletUtil.sendError(req, resp, "Invalid URL!");
                    return;
                }
            }
        }

        req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
    }

    /**
     * Called by the {@linkplain #doGet} method to allow a servlet to handle an
     * appropriate GET request.
     * <p>
     * This method creates a new blog entry and forwards the blog entry form to
     * the appropriate <tt>.jsp</tt> file.
     *
     * @param req object that contains the request the client has made of the servlet
     * @param resp object that contains the response the servlet sends to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException if an input or output error is detected
     */
    private void doNew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BlogEntry entry = new BlogEntry();
        entry.setCreator((BlogUser)req.getAttribute("author"));
        BlogEntryForm form = new BlogEntryForm();
        form.fillFromBlogEntry(entry);

        req.setAttribute("option", "new");
        req.setAttribute("form", form);

        req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
    }

    /**
     * Called by the {@linkplain #doGet} method to allow a servlet to handle an
     * appropriate GET request.
     * <p>
     * This method gets an existing blog entry from the database, fills the blog
     * entry form and forwards it to the appropriate <tt>.jsp</tt> file.
     *
     * @param req object that contains the request the client has made of the servlet
     * @param resp object that contains the response the servlet sends to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException if an input or output error is detected
     */
    private void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id == null) {
            req.setAttribute("editFlag", true);
            req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
        }

        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);

        BlogEntryForm form = new BlogEntryForm();
        form.fillFromBlogEntry(entry);

        req.setAttribute("option", "edit?id="+id);
        req.setAttribute("form", form);

        req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
    }

    /**
     * Called by the {@linkplain #doGet} method to allow a servlet to handle an
     * appropriate GET request.
     * <p>
     * This method removes a comment with the <tt>commentID</tt> fetched from
     * the request parameters and redirects to the entry with an
     * <tt>entryID</tt> fetched from the request parameters.
     * <p>
     * If no blog comment with the specified comment id is found, this method
     * exits with no modifications whatsoever.
     *
     * @param req object that contains the request the client has made of the servlet
     * @param resp object that contains the response the servlet sends to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException if an input or output error is detected
     */
    private void doRemoveComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long commentID = parseLong(req.getParameter("commentID"));
        if (commentID != null) {
            if (DAOProvider.getDAO().getBlogComment(commentID) != null) {
                DAOProvider.getDAO().removeBlogComment(commentID);
            }
        }

        String nickname = req.getParameter("nick");
        String entryID = req.getParameter("entryID");
        resp.sendRedirect(req.getContextPath() + "/servleti/author/"+nickname+"/"+entryID);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        String[] elements = path.split("/");
        String nickname = elements[1];

        req.setAttribute("author", DAOProvider.getDAO().getUserByNick(nickname));

        // Processing comment adding
        if (elements[2].equals("comment")) {
            BlogCommentForm form = new BlogCommentForm();
            form.fillFromHttpRequest(req);
            form.validate();
            req.setAttribute("form", form);

            Long id = parseLong(req.getParameter("id"));
            if (form.hasErrors()) {
                BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
                req.setAttribute("blogEntry", blogEntry);
                req.setAttribute("author", blogEntry.getCreator());
                req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
                return;
            }

            BlogComment comment = new BlogComment();
            form.fillBlogComment(comment);

            DAOProvider.getDAO().addBlogComment(comment);
            resp.sendRedirect(req.getContextPath() + "/servleti/author/"+nickname+"/"+id);
            return;
        }

        // Processing "new" or "edit"
        req.setAttribute("option", req.getParameter("option"));

        BlogEntryForm form = new BlogEntryForm();
        form.fillFromHttpRequest(req);
        form.validate();

        req.setAttribute("form", form);

        if (form.hasErrors()) {
            req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
            return;
        }

        Long id;
        if (elements[2].equals("new")) {
            BlogEntry entry = new BlogEntry();
            form.fillBlogEntry(entry);

            DAOProvider.getDAO().addBlogEntry(entry);
            id = entry.getId();
        } else if (elements[2].equals("edit")) {
            id = parseLong(req.getParameter("id"));
            BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);

            entry.setTitle(form.getTitle());
            entry.setText(form.getText());
            entry.setLastModifiedAt(new Date());
        } else {
            ServletUtil.sendError(req, resp, "Invalid request: " + elements[2]);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/servleti/author/"+nickname+"/"+id);
    }

}
