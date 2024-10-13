// SHA-256 

package SHA256;

public class sha256 {
    // Function to print array
    public static void print(int[] arr) {
        for (int x : arr) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    // Function to rotate bits to the right
    public static void ROTR(int[] bits, int n) {
        int length = bits.length;
        // Create a new array to store the rotated bits
        int[] rotatedBits = new int[length];
        // Rotate each bit to the right
        for (int i = 0; i < length; i++) {
            // Calculate the new index after rotation
            int newIndex = (i + n) % length;
            rotatedBits[newIndex] = bits[i];
        }
        // Copy rotatedBits back to the original bits array
        for (int i = 0; i < length; i++) {
            bits[i] = rotatedBits[i];
        }
    }

    // Function to Shift bits to right
    public static void SHR(int[] bits, int n) {
        int length = bits.length;
        // Create a new array to store the shifted bits
        int[] shiftedBits = new int[length];
        // Shift each bit to the right
        for (int i = length - 1; i >= n; i--) {
            shiftedBits[i] = bits[i - n];
        }
        // Fill the leftmost `n` positions with 0
        for (int i = 0; i < n; i++) {
            shiftedBits[i] = 0;
        }
        // Copy shiftedBits back to the original bits array
        for (int i = 0; i < length; i++) {
            bits[i] = shiftedBits[i];
        }
    }

    // Function for majority bit chose between x,y,z
    public static int[] Maj(int[] x, int[] y, int[] z) {
        int length = x.length;
        int ans[] = new int[length];
        // Majority function: (x AND y) XOR (x AND z) XOR (y AND z)
        for (int i = 0; i < length; i++) {
            ans[i] = (x[i] & y[i]) ^ (x[i] & z[i]) ^ (y[i] & z[i]);
        }
        return ans;
    }

    // Function for Choice we chose bit from y , z with help of x
    public static int[] Ch(int[] x, int[] y, int[] z) {
        int length = x.length;
        int ans[] = new int[length];
        // Choice function: (x AND y) XOR (NOT x AND z)
        for (int i = 0; i < length; i++) {
            ans[i] = (x[i] & y[i]) ^ ((1 - x[i]) & z[i]); // (1 - x[i]) is the NOT operation for bit x[i]
        }
        return ans;
    }

    // Function to generate 64 constants (K values) in binary
    public static int[][] generateK() {
        int[][] K = new int[64][32]; // Each K constant will be 32 bits long (stored as array of 32 bits)
        // First 64 prime numbers
        int[] primes = {
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53,
                59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131,
                137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223,
                227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311
        };
        // Generate the K values
        for (int i = 0; i < 64; i++) {
            double cubeRoot = Math.cbrt(primes[i]); // Calculate cube root
            double fractionalPart = cubeRoot - Math.floor(cubeRoot); // Get fractional part
            int constant = (int) (fractionalPart * Math.pow(2, 32)); // Multiply by 2^32 and cast to int

            // Convert the constant to binary and store in K[i] as an array of 32 bits
            K[i] = convertToBinaryArray(constant, 32);
        }
        return K;
    }
    // Helper function to convert an integer to a binary array of given bit length
    public static int[] convertToBinaryArray(int value, int bitLength) {
        int[] binary = new int[bitLength];

        // Fill the array with binary digits (starting from the least significant bit)
        for (int i = bitLength - 1; i >= 0; i--) {
            binary[i] = value & 1; // Get the last bit (0 or 1)
            value >>= 1; // Shift the value right by 1 bit
        }

        return binary;
    }

    public static void main(String[] args) {
        int[] input = { 1, 0, 1, 1, 0, 1, 1 };
        // print(input);
        // ROTR(input, 2);
        // print(input);
        // SHR(input, 2);
        // print(input);
        // int[] x = { 1, 0, 1, 1, 0, 1, 1 };
        // int[] y = { 0, 0, 0, 1, 1, 0, 1 };
        // int[] z = { 1, 1, 0, 0, 0, 1, 1 };
        // print(Maj(x, y, z));
        // print(Ch(x, y, z));
        int[][] K = generateK();
        print(K[0]);

    }
}