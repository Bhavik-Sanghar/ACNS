import java.math.BigInteger;
import java.util.Random;

/**
 * This class implements the RSA algorithm for encryption and decryption.
 */
public class RSA {
    private BigInteger p; // First prime number
    private BigInteger q; // Second prime number
    private BigInteger n; // Modulus (p * q)
    private BigInteger phi; // Totient of n ((p-1) * (q-1))
    private BigInteger e; // Public exponent
    private BigInteger d; // Private exponent
    private int bitLength = 1024; // Bit length for prime number generation
    private Random r; // Random number generator

    /**
     * Constructor for the RSA class. Generates keys upon initialization.
     */
    public RSA() {
        r = new Random();

        // Generate two large prime numbers p and q
        p = BigInteger.probablePrime(bitLength, r);
        q = BigInteger.probablePrime(bitLength, r);

        // Calculate n = p * q
        n = p.multiply(q);

        // Calculate phi = (p-1) * (q-1)
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Choose a random prime number e such that 1 < e < phi and gcd(e, phi) = 1
        e = BigInteger.probablePrime(bitLength / 2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e = e.add(BigInteger.ONE);
        }

        // Calculate d such that d * e = 1 (mod phi)
        d = e.modInverse(phi);
    }

    /**
     * Encrypts a message using the public key (e, n).
     */
    public BigInteger encrypt(BigInteger message) {
        // Encryption: ciphertext = message^e (mod n)
        return message.modPow(e, n);
    }

    /**
     * Decrypts an encrypted message using the private key (d, n).
     */
    public BigInteger decrypt(BigInteger encrypted) {
        // Decryption: message = ciphertext^d (mod n)
        return encrypted.modPow(d, n);
    }

    /**
     * Main method for testing the RSA encryption and decryption.
     */
    public static void main(String[] args) {
        RSA rsa = new RSA();
        String msg = "Hello, RSA!";

        // Convert the message string to a BigInteger
        BigInteger message = new BigInteger(msg.getBytes());

        // Encrypt the message
        BigInteger encrypted = rsa.encrypt(message);
        System.out.println("Encrypted: " + encrypted);

        // Decrypt the encrypted message
        BigInteger decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypted: " + new String(decrypted.toByteArray()));
    }
}
