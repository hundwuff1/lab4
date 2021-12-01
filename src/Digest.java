import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Digest {
    private MessageDigest messageDigest;

    public byte[] digest(byte[] text) {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            return messageDigest.digest(text);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean checkSignature(byte[] oldDigest,byte[] text){
        byte[] newDigest=digest(text);
        return Arrays.equals(newDigest,oldDigest);
    }
}
