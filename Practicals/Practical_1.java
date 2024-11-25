package Practicals;

public class Practical_1 {
     // Method to encrypt the plaintext
     public static String encrypt(String text, int key) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);

            if (Character.isUpperCase(character)) {
                // Encrypt uppercase characters
                char encryptedChar = (char) (((character - 'A' + key) % 26) + 'A');
                result.append(encryptedChar);
            } else if (Character.isLowerCase(character)) {
                // Encrypt lowercase characters
                char encryptedChar = (char) (((character - 'a' + key) % 26) + 'a');
                result.append(encryptedChar);
            } else {
                // Non-alphabetical characters remain unchanged
                result.append(character);
            }
        }
        return result.toString();
    }

    // Method to decrypt the ciphertext
    public static String decrypt(String text, int key) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);

            if (Character.isUpperCase(character)) {
                // Decrypt uppercase characters
                char decryptedChar = (char) (((character - 'A' - key + 26) % 26) + 'A');
                result.append(decryptedChar);
            } else if (Character.isLowerCase(character)) {
                // Decrypt lowercase characters
                char decryptedChar = (char) (((character - 'a' - key + 26) % 26) + 'a');
                result.append(decryptedChar);
            } else {
                // Non-alphabetical characters remain unchanged
                result.append(character);
            }
        }
        return result.toString();
    }
    public static void main(String[] args) {
        String plaintext = "Hello, World!";
        int key = 3;

        // Encryption
        String encryptedText = encrypt(plaintext, key);
        System.out.println("Original Text: " + plaintext);
        System.out.println("Encrypted Text: " + encryptedText);

        // Decryption
        String decryptedText = decrypt(encryptedText, key);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
