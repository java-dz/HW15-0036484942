package hr.fer.zemris.java.hw15.dao.jpa;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * This class implements the {@linkplain Filter} interface, giving
 * implementation only to the
 * {@linkplain #doFilter(ServletRequest, ServletResponse, FilterChain)} method.
 * <p>
 * A filter is an object that performs filtering tasks on either the request to
 * a resource (a servlet or static content), or on the response from a resource,
 * or both.
 *
 * @author Mario Bobic
 */
@WebFilter("/servleti/*")
public class JPAFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            JPAEMProvider.close();
        }
    }

    @Override
    public void destroy() {
    }

}
