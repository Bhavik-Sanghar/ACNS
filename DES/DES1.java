
//Code for DES algorthim genarte key  

package DES;

import java.security.SecureRandom;

public class DES1 {
    // Generate 64-bit key as array of bits (0s and 1s)
    public static int[] generateKey() {
        SecureRandom random = new SecureRandom();
        int[] key = new int[64]; // Array to hold 64 bits

        for (int i = 0; i < 64; i++) {
            key[i] = random.nextInt(2); // Each bit is either 0 or 1
        }
        return key;
    }

    // We have to Discard every 8th bit (i.e., positions 7, 15, 23, ..., 63)
    public static int[] newKey(int[] key) {
        int[] newKey = new int[56];
        int newIndex = 0;
        for (int i = 0; i < 64; i++) {
            if ((i + 1) % 8 != 0) {
                newKey[newIndex] = key[i]; // Add valid bits to newKey
                newIndex++;
            }
        }
        return newKey;
    }

    // Splitting the 56-bit key
    public static int[][] splitKey(int[] key56) {
        int[] left = new int[28];
        int[] right = new int[28];

        // Copy first 28 bits to the left half
        for (int i = 0; i < 28; i++) {
            left[i] = key56[i];
        }

        // Copy the remaining 28 bits to the right half
        for (int i = 28; i < 56; i++) {
            right[i - 28] = key56[i];
        }

        // Return both halves as a 2D array
        return new int[][] { left, right };
    }

    // Perform circular left shift by a given number of positions
    public static int[] leftShift(int[] half, int shifts) {
        int[] shiftedHalf = new int[28];
        // Circular left shift
        for (int i = 0; i < 28; i++) {
            shiftedHalf[i] = half[(i + shifts) % 28];
        }

        return shiftedHalf;
    }

    public static void performKeyShifts(int[][] halves, int round) {
        // Number of shifts depends on the round
        int shifts = (round == 1 || round == 2 || round == 9 || round == 16) ? 1 : 2;

        // Perform left shift on both halves
        halves[0] = leftShift(halves[0], shifts); // Left half
        halves[1] = leftShift(halves[1], shifts); // Right half
    }

    // Apply PC-2 to compress the combined 56-bit key to 48 bits
    public static int[] applyPC2(int[] combinedKey) {
        // PC-2 table: choosing 48 bits out of 56
        int[] PC2 = {
                14, 17, 11, 24, 1, 5,
                3, 28, 15, 6, 21, 10,
                23, 19, 12, 4, 26, 8,
                16, 7, 27, 20, 13, 2,
                41, 52, 31, 37, 47, 55,
                30, 40, 51, 45, 33, 48,
                44, 49, 39, 56, 34, 53,
                46, 42, 50, 36, 29, 32
        };

        int[] roundKey = new int[48];
        for (int i = 0; i < 48; i++) {
            roundKey[i] = combinedKey[PC2[i] - 1]; // Apply the PC-2 permutation
        }

        return roundKey;
    }

    public static void main(String[] args) {
        int[] key = generateKey();
        int[] newKey = newKey(key);
        int[][] halves = splitKey(newKey);

        // Print key as binary sequence
        System.out.print("64-bit Key: ");
        for (int bit : key) {
            System.out.print(bit);
        }
        System.out.println();
        System.out.print("56-bit Key: ");
        for (int bit : newKey) {
            System.out.print(bit);
        }
        System.out.println();

        // Print the initial halves
        System.out.print("Initial Left half: ");
        for (int bit : halves[0]) {
            System.out.print(bit);
        }
        System.out.println();

        System.out.print("Initial Right half: ");
        for (int bit : halves[1]) {
            System.out.print(bit);
        }
        System.out.println();

        // // Perform key shifts and print the results for each round
        // for (int round = 1; round <= 16; round++) {
        // performKeyShifts(halves, round);

        // System.out.print("Round " + round + " Left half: ");
        // for (int bit : halves[0]) {
        // System.out.print(bit);
        // }
        // System.out.println();

        // System.out.print("Round " + round + " Right half: ");
        // for (int bit : halves[1]) {
        // System.out.print(bit);
        // }
        // System.out.println();
        // }

        // Perform key shifts and generate round keys
        for (int round = 1; round <= 16; round++) {
            performKeyShifts(halves, round);

            // Combine left and right halves
            int[] combinedKey = new int[56];
            System.arraycopy(halves[0], 0, combinedKey, 0, 28); // Left half
            System.arraycopy(halves[1], 0, combinedKey, 28, 28); // Right half

            // Apply PC-2 to get 48-bit round key
            int[] roundKey = applyPC2(combinedKey);

            // Print the round key
            System.out.print("Round " + round + " Key (48 bits): ");
            for (int bit : roundKey) {
                System.out.print(bit);
            }
            System.out.println();
        }
    }
}
