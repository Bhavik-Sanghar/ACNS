from math import gcd
import random

def is_prime(num):
    """Check if a number is prime."""
    if num <= 1:
        return False
    for i in range(2, int(num**0.5) + 1):
        if num % i == 0:
            return False
    return True

def generate_large_prime():
    """Generate a random prime number (for simplicity, within a smaller range)."""
    while True:
        num = random.randint(50, 100)
        if is_prime(num):
            return num

def euler_totient(p, q):
    """Calculate Euler's Totient Function φ(n)."""
    return (p - 1) * (q - 1)

def extended_gcd(a, b):
    """Extended Euclidean Algorithm to find gcd and coefficients."""
    if b == 0:
        return a, 1, 0
    gcd, x1, y1 = extended_gcd(b, a % b)
    x = y1
    y = x1 - (a // b) * y1
    return gcd, x, y

def modular_inverse(e, phi):
    """Find modular inverse of e modulo φ using Extended Euclidean Algorithm."""
    gcd, x, _ = extended_gcd(e, phi)
    if gcd != 1:
        raise ValueError("Modular inverse does not exist.")
    return x % phi

def rsa_key_generation():
    """Generate RSA keys."""
    # Step 1: Choose two random primes
    p = generate_large_prime()
    q = generate_large_prime()
    while p == q:  # Ensure p and q are distinct
        q = generate_large_prime()
    n = p * q

    # Step 2: Calculate φ(n)
    phi = euler_totient(p, q)

    # Step 3: Choose e such that gcd(e, φ) = 1
    e = random.randint(2, phi - 1)
    while gcd(e, phi) != 1:
        e = random.randint(2, phi - 1)

    # Step 4: Calculate d (modular inverse of e modulo φ)
    d = modular_inverse(e, phi)

    return (e, n), (d, n), p, q, phi

def rsa_encrypt(plaintext, public_key):
    """Encrypt plaintext using public key."""
    e, n = public_key
    return pow(plaintext, e, n)

def rsa_decrypt(ciphertext, private_key):
    """Decrypt ciphertext using private key."""
    d, n = private_key
    return pow(ciphertext, d, n)

# Example usage
public_key, private_key, p, q, phi = rsa_key_generation()
print(f"Public Key: {public_key}")
print(f"Private Key: {private_key}")
print(f"Primes (p, q): {p}, {q}")
print(f"phi(n): {phi}")


# Encrypt and Decrypt a message
message = 42  # Example plaintext
print(f"\nOriginal Message: {message}")

ciphertext = rsa_encrypt(message, public_key)
print(f"Encrypted Message: {ciphertext}")

decrypted_message = rsa_decrypt(ciphertext, private_key)
print(f"Decrypted Message: {decrypted_message}")
