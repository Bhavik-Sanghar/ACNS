package ECB;

import java.nio.charset.StandardCharsets;
//Block cipher Electronic Codebook
import java.security.SecureRandom;
import java.util.*;

public class ECB1 {
    public static int[] IV() {
        SecureRandom random = new SecureRandom();
        int[] iv = new int[64]; // Array to hold 64 bits

        for (int i = 0; i < 64; i++) {
            iv[i] = random.nextInt(2); // Each bit is either 0 or 1
        }
        return iv;
    }

    public static int[] EncryptECB(int[] IV, int[] message) {
        int[] output = new int[64];
        for (int i = 0; i < 64; i++) {
            output[i] = IV[i] ^ message[i];
        }
        return output;
    }

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

    // Pad the message to ensure it's a multiple of 64 bits (8 bytes)
    private static int[] padMessage(int[] messageBits) {
        int length = messageBits.length;
        int paddedLength = ((length + 63) / 64) * 64; // Next multiple of 64
        int[] paddedMessage = new int[paddedLength];

        // Copy original message bits
        System.arraycopy(messageBits, 0, paddedMessage, 0, length);

        // PKCS5-like padding: Fill the remaining bits with 0s
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

    public static void main(String[] args) {
        int[] iv = IV();
        System.out.print("IV is :");
        for (int bit : iv) {
            System.out.print(bit);
        }
        System.out.println();

        Scanner sc = new Scanner(System.in);
        System.out.print("ENTER YOUR MESSAGE :) :");
        String input = sc.nextLine();

        System.out.println("Input is : " + input);

        // Convert the input string to a bit array
        int[] messageBits = stringToBitsArray(input);
        System.out.print("Message (in bits): ");
        for (int bit : messageBits) {
            System.out.print(bit);
        }
        System.out.println();

        int[] paddedMessage = padMessage(messageBits);
        System.out.print("Padded Message (in bits): ");
        for (int bit : paddedMessage) {
            System.out.print(bit);
        }
        System.out.println();

        // Split the padded message into 64-bit blocks

        List<int[]> messageBlocks = splitIntoBlocks(paddedMessage);

        // Encrypt each block and print encrypted output
        System.out.println("Encrypted Message (in bits): ");
        for (int[] block : messageBlocks) {
            int[] encryptedBlock = EncryptECB(iv, block);
            for (int bit : encryptedBlock) {
                System.out.print(bit);
            }
            System.out.println(); // Print each encrypted block on a new line
        }
        System.out.println();

    }

}
