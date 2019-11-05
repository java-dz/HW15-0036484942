package hr.fer.zemris.java.hw15.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A form model that suits the web-representation of some domain object. This
 * abstract class offers a unique way of handling errors, which are stored in a
 * map and can later be fetched to show the user an error message of the
 * problem.
 * <p>
 * For every property, the <tt>errors</tt> map is filled with errors when the
 * {@linkplain #validate()} method is called. The form offers the functionality
 * of filling the form from the current request (
 * {@linkplain #fillFromHttpRequest(HttpServletRequest)}). The method reads all
 * necessary parameters and fills in appropriate properties in the form.
 *
 * @author Mario Bobic
 */
public abstract class AbstractForm {

    /**
     * The errors map. It is expected that keys are property names and values
     * are error messages.
     */
    Map<String, String> errors = new HashMap<>();

    /**
     * Returns the errors map.
     *
     * @return the errors map
     */
    public Map<String, String> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

    /**
     * Returns the error message associated with the specified property.
     *
     * @param name name of the property (map key)
     * @return the error message associated with the specified property
     */
    public String getError(String name) {
        return errors.get(name);
    }

    /**
     * Returns true if at least one property has an assigned error. False
     * otherwise.
     *
     * @return true if at least one property has an assigned error
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Returns true if the property with the specified <tt>name</tt> has an error. False otherwise.
     *
     * @param name name of the property
     * @return true if the specified property has an error
     */
    public boolean hasError(String name) {
        return errors.containsKey(name);
    }

    /**
     * Fills this form from the current request by reading all necessary
     * parameters and filling in appropriate properties in the form.
     *
     * @param req the HTTP servlet request with parameters
     */
    public abstract void fillFromHttpRequest(HttpServletRequest req);

    /**
     * Validates the form. The form must be filled before running the
     * validation. This method checks the semantics and constraints of the
     * parameters and fills the errors map if needed.
     */
    public abstract void validate();

    /**
     * Helper method that converts <tt>null</tt> strings to empty strings, which
     * is much more suitable for web usage.
     *
     * @param s a string
     * @return unchanged string <tt>s</tt> if != <tt>null</tt>, otherwise an
     *         empty string
     */
    protected String prepare(String s) {
        if (s == null) return "";
        return s.trim();
    }

}
