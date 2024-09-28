package CBC;

//We hae to amke code for CBC mode in block cipher we are not making for devlopment we are here how it work we dont use inbuilt lib also we print output liek key and all thing at each steps

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

public class CBC1 {
    public static void main(String[] args) {
        // Generate a random key
        int[] key = generateKey();
        System.out.println("Key: " + Arrays.toString(key));

        // Generate a random initialization vector (IV)
        int[] iv = generateIV();
        System.out.println("IV: " + Arrays.toString(iv));

        // Input message
        String message = "Hello, world!";
        System.out.println("Message: " + message);

        // Convert message to bits
        int[] messageBits = stringToBitsArray(message);
        System.out.println("Message in bits: " + Arrays.toString(messageBits));

        // Pad the message
        int[] paddedMessage = padMessage(messageBits);
        System.out.println("Padded message in bits: " + Arrays.toString(paddedMessage));

        // Split the padded message into blocks
        List<int[]> messageBlocks = splitIntoBlocks(paddedMessage);
        System.out.println("Message blocks: " + messageBlocks);

        // Encrypt each block using CBC mode
        List<int[]> encryptedBlocks = encryptCBC(key, iv, messageBlocks);
        System.out.println("Encrypted blocks: " + encryptedBlocks);

        // Convert encrypted blocks to a string
        String encryptedMessage = bitsArrayToString(encryptedBlocks);
        System.out.println("Encrypted message: " + encryptedMessage);

        // Decrypt the encrypted message
        List<int[]> decryptedBlocks = decryptCBC(key, iv, encryptedBlocks);
        System.out.println("Decrypted blocks: " + decryptedBlocks);

        // Convert decrypted blocks to a string
        String decryptedMessage = bitsArrayToString(decryptedBlocks);
        System.out.println("Decrypted message: " + decryptedMessage);
    }

    // Generate a random 64-bit key
    private static int[] generateKey() {
        SecureRandom random = new SecureRandom();
        int[] key = new int[64];
        for (int i = 0; i < 64; i++) {
            key[i] = random.nextInt(2);
        }
        return key;
    }

    // Generate a random 64-bit initialization vector (IV)
    private static int[] generateIV() {
        SecureRandom random = new SecureRandom();
        int[] iv = new int[64];
        for (int i = 0; i < 64; i++) {
            iv[i] = random.nextInt(2);
        }
        return iv;
    }

    // Convert a string to an array of bits
    private static int[] stringToBitsArray(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        int[] bits = new int[bytes.length * 8];

        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                bits[i * 8 + j] = (bytes[i] >> (7 - j)) & 1;
            }
        }

        return bits;
    }

    // Pad the message to a multiple of 64 bits
    private static int[] padMessage(int[] messageBits) {
        int length = messageBits.length;
        int paddedLength = ((length + 64 - 1) / 64) * 64;
        int[] paddedMessage = new int[paddedLength];

        System.arraycopy(messageBits, 0, paddedMessage, 0, length);

        for (int i = length; i < paddedLength; i++) {
            paddedMessage[i] = 0;
        }

        return paddedMessage;
    }

    // Split the message into 64-bit blocks
    private static List<int[]> splitIntoBlocks(int[] messageBits) {
        List<int[]> blocks = new ArrayList<>();
        for (int i = 0; i < messageBits.length; i += 64) {
            int[] block = new int[64];
            System.arraycopy(messageBits, i, block, 0, 64);
            blocks.add(block);
        }
        return blocks;
    }

    // Encrypt a block using CBC mode
    private static int[] encryptBlock(int[] key, int[] block) {
        // In a real implementation, this would be a proper block cipher
        // For simplicity, we're just XORing with the key
        int[] output = new int[64];
        for (int i = 0; i < 64; i++) {
            output[i] = key[i] ^ block[i];
        }
        return output;
    }

    // Encrypt the message using CBC
    private static List<int[]> encryptCBC(int[] key, int[] iv, List<int[]> messageBlocks) {
        List<int[]> encryptedBlocks = new ArrayList<>();
        int[] previousBlock = iv;

        for (int[] block : messageBlocks) {
            // XOR the current block with the previous block
            int[] xorBlock = xor(block, previousBlock);
            System.out.println("XORed block: " + Arrays.toString(xorBlock));

            // Encrypt the XORed block
            int[] encryptedBlock = encryptBlock(key, xorBlock);
            System.out.println("Encrypted block: " + Arrays.toString(encryptedBlock));

            // Add the encrypted block to the list
            encryptedBlocks.add(encryptedBlock);

            // Set the previous block to the current encrypted block
            previousBlock = encryptedBlock;
        }

        return encryptedBlocks;
    }

    // Decrypt a block using CBC mode
    private static int[] decryptBlock(int[] key, int[] block) {
        // In a real implementation, this would be the inverse of the block cipher
        // For simplicity, we're just XORing with the key
        int[] output = new int[64];
        for (int i = 0; i < 64; i++) {
            output[i] = key[i] ^ block[i];
        }
        return output;
    }

    // Decrypt the message using CBC
    private static List<int[]> decryptCBC(int[] key, int[] iv, List<int[]> encryptedBlocks) {
        List<int[]> decryptedBlocks = new ArrayList<>();
        int[] previousBlock = iv;

        for (int[] block : encryptedBlocks) {
            // Decrypt the current block
            int[] decryptedBlock = decryptBlock(key, block);
            System.out.println("Decrypted block: " + Arrays.toString(decryptedBlock));

            // XOR the decrypted block with the previous block
            int[] xorBlock = xor(decryptedBlock, previousBlock);
            System.out.println("XORed block: " + Arrays.toString(xorBlock));

            // Add the XORed block to the list
            decryptedBlocks.add(xorBlock);

            // Set the previous block to the current decrypted block
            previousBlock = block;
        }

        return decryptedBlocks;
    }

    // XOR two arrays of bits
    private static int[] xor(int[] a, int[] b) {
        int[] output = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            output[i] = a[i] ^ b[i];
        }
        return output;
    }

    // Convert an array of bits to a string
    private static String bitsArrayToString(List<int[]> blocks) {
        StringBuilder sb = new StringBuilder();
        for (int[] block : blocks) {
            for (int i = 0; i < block.length; i += 8) {
                int byteValue = 0;
                for (int j = 0; j < 8; j++) {
                    byteValue |= block[i + j] << (7 - j);
                }
                sb.append((char) byteValue);
            }
        }
        return sb.toString();
    }
}
