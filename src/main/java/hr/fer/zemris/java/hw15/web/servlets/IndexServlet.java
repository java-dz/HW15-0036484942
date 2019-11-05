package hr.fer.zemris.java.hw15.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//
// IMPLEMENTATION NOTE: If .../blog/non-existent-page redirects you to the main page,
// remove the empty string ("") from the URL pattern mappings. This does not work on
// Jboss 5.1, WebSphere 8.5, google app-engine etc., as well as it may not work on
// some versions of Jetty or Tomcat.
// This means that the user will not be redirected to the main page when entering the
// .../blog/ URL address, but at least CSS and other scripts will work.
//
/**
 * This servlet represents a blog start-page that immediately redirects to the
 * main page. There are four mappings of the servlet - <tt>/index.jsp</tt>, as
 * an index JavaServer Page, <tt>/index.html</tt> and <tt>/index.htm</tt> as two
 * versions of the HTML index page and <tt>/</tt>, as a root of the web
 * application.
 *
 * @author Mario Bobic
 */
@WebServlet(name="index", urlPatterns={"/index.jsp", "/index.html", "/index.htm", ""})
public class IndexServlet extends HttpServlet {
    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/servleti/main");
    }

}
