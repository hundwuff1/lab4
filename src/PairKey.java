import java.security.PrivateKey;
import java.security.PublicKey;

public class PairKey {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public PairKey(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public byte[] getEPrivateKey() {
        return privateKey.getEncoded();
    }

    public byte[] getEPublicKey() {
        return publicKey.getEncoded();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

}
