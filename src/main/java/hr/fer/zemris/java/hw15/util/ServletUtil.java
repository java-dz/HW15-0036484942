package hr.fer.zemris.java.hw15.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

/**
 * This class serves as a utility class mostly for servlets, but can be used
 * across the whole server application. Defines most important and most common
 * methods.
 *
 * @author Mario Bobic
 */
public class ServletUtil {

	/** Maximum length of the e-mail address. */
	public static final int EMAIL_LEN = 60;

	/**
	 * Disable instantiation.
	 */
	private ServletUtil() {
	}
	
	/**
	 * Generates the 40 character long SHA-1 password hash of the user's
	 * password by converting the specified <tt>password</tt> to an array of
	 * bytes decoded with the {@link StandardCharsets#UTF_8 UTF-8} charset and
	 * digested with the hash-algorithm.
	 * 
	 * @param password password to be hashed
	 * @return the hash of the specified <tt>password</tt>
	 */
	public static String generatePasswordHash(String password) {
		byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new InternalError("Algorithm unavailable (SHA-1)", e);
		}
		
		return DatatypeConverter.printHexBinary(md.digest(passwordBytes));
	}
	
	/**
	 * Sets the <tt>message</tt> attribute with name <tt>error</tt> and forwards
	 * the request to an <tt>error.jsp</tt> which shows the user an error.
	 * 
	 * @param req object that contains the request the client has made of the servlet
	 * @param resp object that contains the response the servlet sends to the client
	 * @param message message to be set as an error attribute
	 * @throws ServletException if the request could not be handled
	 * @throws IOException if an input or output error is detected
	 */
	public static void sendError(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {
		req.setAttribute("error", message);
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}
	
	/**
	 * Returns true if the specified <tt>email</tt> is a valid e-mail address.
	 * <p>
	 * A valid e-mail address must not exceed {@linkplain #EMAIL_LEN} characters
	 * in length and must contain an 'at' symbol between two words (username and
	 * domain)
	 * 
	 * @param email e-mail address to be validated
	 * @return true if the e-mail address is valid, false othwerise
	 */
	public static boolean isEmailValid(String email) {
		int l = email.length();
		if (l > EMAIL_LEN) {
			return false;
		}
		
		int p = email.indexOf('@');
		if (l<3 || p==-1 || p==0 || p==l-1) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Parses the specified string argument <tt>s</tt> as a signed decimal
	 * <tt>long</tt>. The characters in the string must all be decimal digits,
	 * except that the first character may be an ASCII minus sign <tt>'-'</tt>
	 * to indicate a negative value or an ASCII plus sign <tt>'+'</tt> to
	 * indicate a positive value.
	 * <p>
	 * If the string argument is not parsable as a <tt>long</tt>, <tt>null</tt>
	 * is returned.
	 * 
	 * @param s string to be parsed as <tt>long</tt>
	 * @return a <tt>Long</tt> value or <tt>null</tt> if string is unparsable
	 */
	public static Long parseLong(String s) {
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
