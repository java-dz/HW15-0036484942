package hr.fer.zemris.java.hw15.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet has a function of user logout. The servlet just invalidates the
 * HTTP session and redirects to the main page.
 *
 * @author Mario Bobic
 */
@WebServlet(name="logout", urlPatterns={"/servleti/logout"})
public class LogoutServlet extends HttpServlet {
    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/servleti/main");
    }

}
