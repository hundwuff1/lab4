import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;



public class Communication {
    private final String SENDER="sender\\";
    private final String RECEIVER="receiver\\";

    private final RSACrypt rsaCrypt=new RSACrypt();
    private final DESedeCrypt desCrypt=new DESedeCrypt();
    public void sender() {

        initializationCommunication();

        receiver();

        Key digestKey=rsaCrypt.getPublicKey(SENDER+"publicKeyRSADigest.key");
        byte[] encryptTextWithSignature=WorkWithFile.readBinary(SENDER+"encryptText.dat");
        byte[] encryptSignature= Arrays.copyOfRange(encryptTextWithSignature,encryptTextWithSignature.length-256,encryptTextWithSignature.length);
        byte[] encryptText=Arrays.copyOfRange(encryptTextWithSignature,0,encryptTextWithSignature.length-encryptSignature.length);
        byte[] signature=rsaCrypt.decrypt(encryptSignature,digestKey);
        Digest digest=new Digest();
        boolean isNotChange=digest.checkSignature(signature,encryptText);
        if(isNotChange){
            Key privateKey=rsaCrypt.getPrivateKey(SENDER+"senderPrivateRSA.key");
            byte[] bKey=WorkWithFile.readBinary(SENDER+"encryptKey.key");
            bKey=rsaCrypt.decrypt(bKey,privateKey);
            Key desKey=desCrypt.getKey(bKey);

            byte[] decryptText=desCrypt.decrypt(encryptText,desKey);
            System.out.println(translateIntoText(decryptText));
            System.out.println(Arrays.toString(decryptText));
        }else {
            System.out.println("Подпись изменена");
        }
        byte[] bText = WorkWithFile.readBinary("text.txt");

        System.out.println(Arrays.toString(bText));
    }

    public void receiver() {
        Digest digest=new Digest();
        
        Key rsaPublic = rsaCrypt.getPublicKey(RECEIVER+"senderPublicRSA.key");
        Key desKey = desCrypt.generateKey();
        byte[] bText = WorkWithFile.readBinary("text.txt");
        byte[] encryptText = desCrypt.encrypt(bText, desKey);
        byte[] signature=digest.digest(encryptText);
        PairKey pairKey=rsaCrypt.generateKeyPair();

        byte[] encryptDigest=rsaCrypt.encrypt(signature,pairKey.getPrivateKey());

        encryptText=concate(encryptText, encryptDigest);
        byte[] encryptKey=rsaCrypt.encrypt(desKey.getEncoded(),rsaPublic);


        WorkWithFile.writeFile(SENDER+"encryptKey.key",encryptKey);
        WorkWithFile.writeFile(SENDER+"encryptText.dat",encryptText);
        WorkWithFile.writeFile(SENDER+"publicKeyRSADigest.key",pairKey.getEPublicKey());
    }

    private byte[] concate(byte[] array1, byte[] array2){
        return ByteBuffer.allocate(array1.length + array2.length)
                .put(array1)
                .put(array2)
                .array();
    }

    private String translateIntoText(byte[] text){

        StringBuilder result=new StringBuilder();
        for (byte b : text) {
            result.append((char) b);
        }
        return String.valueOf(result);
    }

    private void initializationCommunication(){
        PairKey pair = rsaCrypt.generateKeyPair();

        WorkWithFile.writeFile(SENDER+"senderPrivateRSA.key", pair.getEPrivateKey());
        WorkWithFile.writeFile(RECEIVER+"senderPublicRSA.key", pair.getEPublicKey());
    }


}
