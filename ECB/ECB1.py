import os

def generate_iv(size=8):
    """Generate a random Initialization Vector (IV)."""
    return os.urandom(size)

def bytes_to_bits(data):
    """Convert bytes to a binary string representation."""
    return ''.join(format(byte, '08b') for byte in data)

def encrypt_ecb(message, iv):
    """ECB-like encryption function using XOR with IV."""
    return bytes(message[i] ^ iv[i % len(iv)] for i in range(len(message)))

def main():
    # Generate a random 64-bit IV (8 bytes)
    iv = generate_iv()
    print(f"IV: {iv}")
    print(f"IV (bits): {bytes_to_bits(iv)}")

    # Original message
    message_og = "ABCDEFGH"
    message = message_og.encode('utf-8')

    print(f"Original Message: {message_og}")
    print(f"Original Message (bits): {bytes_to_bits(message)}")

    # Encrypt the message
    encrypted_message = encrypt_ecb(message, iv)

    # Print the encrypted message as binary
    print(f"Encrypted Message (bits): {bytes_to_bits(encrypted_message)}")

if __name__ == "__main__":
    main()