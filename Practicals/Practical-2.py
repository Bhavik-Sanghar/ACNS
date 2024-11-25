import random

def generate_random_key():
    """Generate a random 64-bit key as a list of binary values (0s and 1s)."""
    return [random.randint(0, 1) for _ in range(64)]

# Permuted Choice 1 (PC-1) Table
PC1 = [57, 49, 41, 33, 25, 17, 9,
       1, 58, 50, 42, 34, 26, 18,
       10, 2, 59, 51, 43, 35, 27,
       19, 11, 3, 60, 52, 44, 36,
       63, 55, 47, 39, 31, 23, 15,
       7, 62, 54, 46, 38, 30, 22,
       14, 6, 61, 53, 45, 37, 29,
       21, 13, 5, 28, 20, 12, 4]

# Permuted Choice 2 (PC-2) Table
PC2 = [14, 17, 11, 24, 1, 5,
       3, 28, 15, 6, 21, 10,
       23, 19, 12, 4, 26, 8,
       16, 7, 27, 20, 13, 2,
       41, 52, 31, 37, 47, 55,
       30, 40, 51, 45, 33, 48,
       44, 49, 39, 56, 34, 53,
       46, 42, 50, 36, 29, 32]

# Number of Left Shifts per Round
SHIFT_SCHEDULE = [1, 1, 2, 2, 2, 2, 2, 2,
                  1, 2, 2, 2, 2, 2, 2, 1]

def permute(key, table):
    """Perform permutation on the key using the given table."""
    return [key[i - 1] for i in table]

def left_shift(bits, shifts):
    """Perform left circular shift."""
    return bits[shifts:] + bits[:shifts]

def generate_round_keys(initial_key):
    """Generate 16 round keys for DES."""
    # Apply PC-1 to get a 56-bit key
    key_56bit = permute(initial_key, PC1)

    # Split into left and right halves
    C, D = key_56bit[:28], key_56bit[28:]

    round_keys = []
    for round_num, shifts in enumerate(SHIFT_SCHEDULE):
        # Left circular shift
        C = left_shift(C, shifts)
        D = left_shift(D, shifts)

        # Combine halves and apply PC-2
        combined = C + D
        round_key = permute(combined, PC2)
        round_keys.append(round_key)

        # Print the key in bits
        print(f"Round {round_num + 1} Key: {''.join(map(str, round_key))}")

    return round_keys

initial_key = generate_random_key()
print("Initial Key: ", ''.join(map(str, initial_key)))
print("\nRound Keys:")
generate_round_keys(initial_key)
