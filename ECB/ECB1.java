package ECB;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

public class ECB1 {
    private static final int BLOCK_SIZE = 64; // 64-bit blocks

    public static int[] generateKey() {
        SecureRandom random = new SecureRandom();
        int[] key = new int[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            key[i] = random.nextInt(2);
        }
        return key;
    }

    public static int[] encryptECB(int[] key, int[] block) {
        // In a real implementation, this would be a proper block cipher
        // For simplicity, we're just XORing with the key
        int[] output = new int[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            output[i] = key[i] ^ block[i];
        }
        return output;
    }

    public static int[] decryptECB(int[] key, int[] block) {
        // In ECB, decryption is the same operation as encryption
        return encryptECB(key, block);
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

    private static int[] padMessage(int[] messageBits) {
        int length = messageBits.length;
        int paddedLength = ((length + BLOCK_SIZE - 1) / BLOCK_SIZE) * BLOCK_SIZE;
        int[] paddedMessage = new int[paddedLength];

        System.arraycopy(messageBits, 0, paddedMessage, 0, length);

        for (int i = length; i < paddedLength; i++) {
            paddedMessage[i] = 0;
        }

        return paddedMessage;
    }

    private static List<int[]> splitIntoBlocks(int[] messageBits) {
        List<int[]> blocks = new ArrayList<>();
        for (int i = 0; i < messageBits.length; i += BLOCK_SIZE) {
            int[] block = new int[BLOCK_SIZE];
            System.arraycopy(messageBits, i, block, 0, BLOCK_SIZE);
            blocks.add(block);
        }
        return blocks;
    }

    private static String bitsArrayToString(int[] bits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length; i += 8) {
            int byteValue = 0;
            for (int j = 0; j < 8; j++) {
                byteValue = (byteValue << 1) | bits[i + j];
            }
            sb.append((char) byteValue);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int[] key = generateKey();
        System.out.print("Key is: ");
        for (int bit : key) {
            System.out.print(bit);
        }
        System.out.println();

        Scanner sc = new Scanner(System.in);
        System.out.print("ENTER YOUR MESSAGE: ");
        String input = sc.nextLine();

        System.out.println("Input is: " + input);

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

        List<int[]> messageBlocks = splitIntoBlocks(paddedMessage);

        System.out.println("Encrypted Message (in bits): ");
        List<int[]> encryptedBlocks = new ArrayList<>();
        for (int[] block : messageBlocks) {
            int[] encryptedBlock = encryptECB(key, block);
            encryptedBlocks.add(encryptedBlock);
            for (int bit : encryptedBlock) {
                System.out.print(bit);
            }
            System.out.println();
        }

        System.out.println("Encrypted Message in Text : ");
        for (int[] encryptedBlock : encryptedBlocks) {
            String encryptedText = bitsArrayToString(encryptedBlock);
            System.out.println(encryptedText);
        }
        System.out.println();

        System.out.println("Decrypted Message (in bits): ");
        List<int[]> decryptedBlocks = new ArrayList<>();
        for (int[] encryptedBlock : encryptedBlocks) {
            int[] decryptedBlock = decryptECB(key, encryptedBlock);
            decryptedBlocks.add(decryptedBlock);
            for (int bit : decryptedBlock) {
                System.out.print(bit);
            }
            System.out.println();
        }

        int[] decryptedBits = decryptedBlocks.stream()
                .flatMapToInt(Arrays::stream)
                .toArray();
        String decryptedMessage = bitsArrayToString(decryptedBits).trim();
        System.out.println("Decrypted message: " + decryptedMessage);
    }
}