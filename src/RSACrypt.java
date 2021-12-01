import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSACrypt {
    private Cipher cipher;
    public PairKey generateKeyPair() {
        try {
            cipher = Cipher.getInstance("RSA");
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = keyPairGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            return new PairKey(privateKey, publicKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public PrivateKey getPrivateKey(String filePath) {
        try {
            byte[] bytes = WorkWithFile.readBinary(filePath);
            KeySpec ks = new PKCS8EncodedKeySpec(bytes,"RSA");
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePrivate(ks);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
    public PrivateKey privateKey(byte[] bytes) {
        try {

            KeySpec ks = new PKCS8EncodedKeySpec(bytes,"RSA");
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePrivate(ks);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PublicKey getPublicKey(String filePath) {
        byte[] bytes = WorkWithFile.readBinary(filePath);
        KeySpec ks = new X509EncodedKeySpec(bytes);
        KeyFactory kf;

        try {
            kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(ks);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
    public byte[] encrypt(byte[] text, Key key) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            System.out.println(key);
            return cipher.doFinal(text);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public byte[] decrypt(byte[] encryptText, Key key) {

        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(encryptText);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
