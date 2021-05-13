package PasswordManager;

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

    public Security(String application, String username, String user) {
        this.plainKey = username.substring(0, 4) + application.substring(0, 4) + user.substring(0, 4);
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
            System.out.println("NoSuchAlgorithmException: " + e);
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

    public String DecryptPassword(String encPassword) {
        try {
            setKey();

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return new String(cipher.doFinal(Base64.getDecoder().decode(encPassword)));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }
}
