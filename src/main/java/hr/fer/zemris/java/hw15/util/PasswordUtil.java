package hr.fer.zemris.java.hw15.util;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

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

}
