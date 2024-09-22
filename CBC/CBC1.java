package CBC;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CBC1 {
    public static void main(String[] args) {
        try {
            // Generate a DES key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56); // DES uses a 56-bit key (internally it's 64 bits, with 8 bits used for parity)
            SecretKey secretKey = keyGenerator.generateKey();

            // Display the key in Base64 encoding
            System.out.println("Key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));

            System.out.print("Key in bits: ");
            byte[] keyBytes = secretKey.getEncoded();
            for (byte b : keyBytes) {
                // Convert each byte to its binary representation
                String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
                System.out.print(binaryString);
            }
            System.out.println();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
