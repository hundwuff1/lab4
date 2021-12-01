import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class DESedeCrypt {
    private Cipher cipher;

    DESedeCrypt() {
        try {
            cipher = Cipher.getInstance("TripleDES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypt(byte[] text,Key key) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            System.out.println(Arrays.toString(key.getEncoded()));
            return cipher.doFinal(text);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public byte[] decrypt(byte[] text, Key key){
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(text);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Key generateKey() {
        try {
            return KeyGenerator.getInstance("TripleDES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  Key getKey(byte[] bytes){

        return new SecretKeySpec(bytes,"TripleDES");
    }
}
