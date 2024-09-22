import java.nio.charset.StandardCharsets;
import java.util.*;

public class Test {

    // Base64 character table (0-63)
    private static final String BASE64_TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static List<Integer> stringToBitsArray(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.US_ASCII);
        List<Integer> bitsList = new ArrayList<>();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                bitsList.add((val & 128) == 0 ? 0 : 1);      // 128 = 1000 0000
                val <<= 1;
            }
        }
        return bitsList;
    }

    public static void printBitsInChunks(List<Integer> bits, int chunkSize) {
        for (int i = 0; i < bits.size(); i++) {
            System.out.print(bits.get(i));
            if ((i + 1) % chunkSize == 0 && i != bits.size() - 1) {
                System.out.print(" "); // Space after every chunkSize bits
            }
        }
        System.out.println(); // New line after printing all bits
    }

    public static int padBitsForChunks(List<Integer> bits, int chunkSize) {
        int originalSize = bits.size();
        int padding = chunkSize - (bits.size() % chunkSize);
        if (padding != chunkSize) {
            for (int i = 0; i < padding; i++) {
                bits.add(0); // Pad with 0s
            }
        }
        return bits.size() - originalSize;  // Return the number of padding bits added
    }

    public static List<Integer> bitsToDecimalList(List<Integer> bits, int chunkSize) {
        List<Integer> decimalList = new ArrayList<>();
        for (int i = 0; i < bits.size(); i += chunkSize) {
            StringBuilder binaryString = new StringBuilder();
            for (int j = 0; j < chunkSize; j++) {
                binaryString.append(bits.get(i + j));
            }
            int decimal = Integer.parseInt(binaryString.toString(), 2); // Convert binary string to decimal
            decimalList.add(decimal);
        }
        return decimalList;
    }

    public static String base64(List<Integer> decimalList , int paddingCount) {
        StringBuilder ans = new StringBuilder();
        for (int decimal : decimalList) {
            ans.append(BASE64_TABLE.charAt(decimal));  // Get corresponding Base64 character
        }
        // Add padding '=' characters
         for (int i = 0; i < paddingCount / 2; i++) {
            ans.append('=');
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        System.err.print("Enter your text : ");
        Scanner sc = new Scanner(System.in);
        String plaintxt = sc.nextLine();
        System.out.println("String in normal format: " + plaintxt);

        // Convert string to bits
        List<Integer> bits = stringToBitsArray(plaintxt);
        System.out.println("Binary Representation (Before Padding):");
        printBitsInChunks(bits, 6);

        // Pad bits to make them divisible by 6
        int paddingBits = padBitsForChunks(bits, 6);
        System.out.println("Binary Representation (After Padding):");
        printBitsInChunks(bits, 6);

        // Convert bits to decimal (6-bit chunks)
        List<Integer> decimalList = bitsToDecimalList(bits, 6);
        System.out.println("Decimal Representation: " + decimalList);

        // Map decimals to Base64
        String base64Encoded = base64(decimalList,paddingBits);
        System.out.println("Base64 Encoded String: " + base64Encoded);
    }
}
