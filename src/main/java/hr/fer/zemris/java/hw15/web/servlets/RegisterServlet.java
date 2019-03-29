package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.util.RegistrationForm;

/**
 * This servlet represents a registration page that offers a form for
 * registering. If the form contains errors, none of the data is saved to the
 * database and the user is asked again to enter the information, showing him
 * all forms with values that he provided, except the password form.
 *
 * @author Mario Bobic
 */
@WebServlet(name="register", urlPatterns={"/servleti/register"})
public class RegisterServlet extends HttpServlet {
    /** Serialization UID. */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationForm form = new RegistrationForm();
        form.fillFromHttpRequest(req);

        req.setAttribute("form", form);

        form.validate();
        if (form.hasErrors()) {
            req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
            return;
        }

        // Fill user data and add to database
        BlogUser user = new BlogUser();
        form.fillBlogUser(user);
        DAOProvider.getDAO().addBlogUser(user);

        req.getSession().setAttribute("current.user.id", user.getId());
        req.getSession().setAttribute("current.user.fn", user.getFirstName());
        req.getSession().setAttribute("current.user.ln", user.getLastName());
        req.getSession().setAttribute("current.user.nick", user.getNick());
        resp.sendRedirect(req.getContextPath() + "/servleti/main");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

}
