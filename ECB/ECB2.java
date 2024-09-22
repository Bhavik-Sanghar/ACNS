package ECB;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class ECB2{
    public static void main(String[] args) throws Exception {
        // Generate AES key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // AES-128
        SecretKey secretKey = keyGenerator.generateKey();

        // System.out.println(secretKey.getEncoded());
        
        // // Display the secret key
        // System.out.println("Key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        
        // // Message to be encrypted
        // String message = "Bhavik is cool";
        // System.out.println("Original message: " + message);

        // // Encrypt the message
        // Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        // encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        // byte[] encryptedMessage = encryptCipher.doFinal(message.getBytes());
        // System.out.println("Encrypted message (Base64): " + Base64.getEncoder().encodeToString(encryptedMessage));

        // // Decrypt the message
        // Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        // decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
        // byte[] decryptedMessage = decryptCipher.doFinal(encryptedMessage);
        // System.out.println("Decrypted message: " + new String(decryptedMessage));
    }
}
