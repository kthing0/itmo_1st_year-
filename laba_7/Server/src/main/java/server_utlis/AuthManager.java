package server_utlis;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthManager {
    static DatabaseManager databaseManager = DatabaseManager.getInstance();

    public static boolean checkLogin(String login, String password){
        return databaseManager.existsUser(login, hashPassword(password));
    }

    public static boolean register(String login, String password){
        return databaseManager.register(login, hashPassword(password));
    }

    public static String hashPassword(String password){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] digest = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger num = new BigInteger(1, digest);
            return num.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
