package Elgamal;

import java.math.BigInteger;
import java.util.Random;

public class Elgamal{

    // Function to find the primitive root for a prime p
    public static int findPrimitiveRoot(int p) {
        for (int g = 2; g < p; g++) {
            if (isPrimitiveRoot(g, p)) {
                return g;
            }
        }
        throw new IllegalArgumentException("No primitive root found for " + p);
    }

    // Check if g is a primitive root of prime p
    private static boolean isPrimitiveRoot(int g, int p) {
        for (int i = 1; i < p - 1; i++) {
            if (BigInteger.valueOf(g).modPow(BigInteger.valueOf(i), BigInteger.valueOf(p)).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }

    // Power mod function to compute (base^exp) % mod
    public static BigInteger powerMod(BigInteger base, BigInteger exp, BigInteger mod) {
        return base.modPow(exp, mod);
    }

    public static void main(String[] args) {
        int p = 17;
        System.out.println("The prime number is: " + p);

        // Find the smallest primitive root of p
        int g = findPrimitiveRoot(p);
        System.out.println("The smallest primitive root of " + p + " is: " + g);

        // Private key / Random value
        int a = 11;
        System.out.println("Private key is: " + a);

        // Key Generation
        BigInteger bigP = BigInteger.valueOf(p);
        BigInteger bigG = BigInteger.valueOf(g);
        BigInteger bigA = BigInteger.valueOf(a);
        BigInteger e = powerMod(bigG, bigA, bigP);
        System.out.println("Public key is: " + e);

        // Message to encrypt
        int m = 12;
        System.out.println("Message is: " + m);

        // Random value (Bob's choice)
        int b = 13;
        System.out.println("Random value (b) is: " + b);

        // Encryption:
        BigInteger bigB = BigInteger.valueOf(b);
        BigInteger c1 = powerMod(bigG, bigB, bigP);
        System.out.println("C1 is: " + c1);

        BigInteger bigM = BigInteger.valueOf(m);
        BigInteger c2 = (bigM.multiply(powerMod(e, bigB, bigP))).mod(bigP);
        System.out.println("C2 is: " + c2);

        // Decryption:
        BigInteger x = powerMod(c1, bigA, bigP);
        System.out.println("X (c1^a mod p) is: " + x);

        // Modular inverse of x
        BigInteger xInv = x.modInverse(bigP);  // Modular inverse using BigInteger
        BigInteger decryptedMessage = (c2.multiply(xInv)).mod(bigP);  // Decrypt the message
        System.out.println("Decrypted message is: " + decryptedMessage);
    }
}
