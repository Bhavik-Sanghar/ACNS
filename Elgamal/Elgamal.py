from sympy import primitive_root

def find_primitive_root(val):
    try:
        g = primitive_root(val)
        return g
    except ValueError as e:
        print(f"Error: {e}")

def opration(base, exp, mod):
    return pow(base, exp, mod)

def main():
    p = 17
    print(f"The prime number is: {p}")
    g = find_primitive_root(p)
    print(f"The smallest primitive root of {p} is: {g}")

    # Private key / Random val
    a = 11
    print(f"Private key is: {a}")

    # Key Generation:
    e = pow(g, a, p)
    print(f"Public key is: {e}")

    # Message to encrypt
    m = 12
    print(f"Message is: {m}")

    # Random value (Bob's choice)
    b = 13
    print(f"Random value (b) is: {b}")

    # Encryption:
    c1 = pow(g, b, p)
    print(f"C1 is: {c1}")

    c2 = (m * pow(e, b, p)) % p
    print(f"C2 is: {c2}")

    # Decryption:
    x = pow(c1, a, p)
    print(f"X (c1^a mod p) is: {x}")

    # Modular inverse of x
    x_inv = pow(x, p-2, p)  # Using Fermat's Little Theorem to calculate inverse of x mod p
    result = (c2 * x_inv) % p  # Decrypt the message
    print(f"Decrypted message is: {result}")

if __name__ == "__main__":
    main()
