package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.util.LoginForm;

/**
 * This servlet represents the main page where users can log in, register and
 * view other blog authors. The {@linkplain #doGet} method just obtains a list
 * of users and forwards the request to the <tt>.jsp</tt> file, while the
 * {@linkplain #doPost} method does that and tries to log the user in based on
 * the information he provided in the form.
 *
 * @author Mario Bobic
 */
@WebServlet(name="main", urlPatterns={"/servleti/main"})
public class MainServlet extends HttpServlet {
    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BlogUser> users = DAOProvider.getDAO().getUsers();
        req.setAttribute("users", users);

        LoginForm form = new LoginForm();
        form.fillFromHttpRequest(req);

        req.setAttribute("form", form);

        form.validate();
        if (form.hasErrors()) {
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            return;
        }

        // Get user from database and set current session
        BlogUser user = DAOProvider.getDAO().getUserByNick(form.getNick());

        req.getSession().setAttribute("current.user.id", user.getId());
        req.getSession().setAttribute("current.user.fn", user.getFirstName());
        req.getSession().setAttribute("current.user.ln", user.getLastName());
        req.getSession().setAttribute("current.user.nick", user.getNick());
        resp.sendRedirect(req.getContextPath() + "/servleti/main");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BlogUser> users = DAOProvider.getDAO().getUsers();

        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

}
