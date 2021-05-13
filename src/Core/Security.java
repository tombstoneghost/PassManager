package Core;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Security {
    private SecretKeySpec secretKey;
    private final String plainKey;

    public Security(String firstName, String lastName, String username) {
        this.plainKey = username.substring(0, 4) + lastName.substring(0, 4) + firstName.substring(0, 4);
    }

    private void setKey() {
        MessageDigest sha;

        try {
            byte[] key = this.plainKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");

            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            this.secretKey = new SecretKeySpec(key, "AES");

        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithm Exception: " + e);
        }
    }

    public String EncryptPassword(String password) {
        try {
            setKey();

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        return null;
    }
}
