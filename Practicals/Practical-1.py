# Caesar Cipher Implementation
def caesar_cipher_encrypt(text, key):
    """
    Encrypts the input text using Caesar Cipher with the given key.
    
    Parameters:
        text (str): The plaintext to be encrypted.
        key (int): The shift value for encryption.
    
    Returns:
        str: The encrypted ciphertext.
    """
    result = ""
    for char in text:
        if char.isupper():
            # Encrypt uppercase characters
            result += chr((ord(char) - ord('A') + key) % 26 + ord('A'))
        elif char.islower():
            # Encrypt lowercase characters
            result += chr((ord(char) - ord('a') + key) % 26 + ord('a'))
        else:
            # Non-alphabetical characters remain the same
            result += char
    return result


def caesar_cipher_decrypt(text, key):
    """
    Decrypts the input text encrypted with Caesar Cipher using the given key.
    
    Parameters:
        text (str): The ciphertext to be decrypted.
        key (int): The shift value used during encryption.
    
    Returns:
        str: The decrypted plaintext.
    """
    result = ""
    for char in text:
        if char.isupper():
            # Decrypt uppercase characters
            result += chr((ord(char) - ord('A') - key) % 26 + ord('A'))
        elif char.islower():
            # Decrypt lowercase characters
            result += chr((ord(char) - ord('a') - key) % 26 + ord('a'))
        else:
            # Non-alphabetical characters remain the same
            result += char
    return result


# Example usage
if __name__ == "__main__":
    plaintext = "Hello, World!"
    key = 3
    print("Original Text: ", plaintext)
    
    # Encrypt the text
    encrypted_text = caesar_cipher_encrypt(plaintext, key)
    print("Encrypted Text: ", encrypted_text)
    
    # Decrypt the text
    decrypted_text = caesar_cipher_decrypt(encrypted_text, key)
    print("Decrypted Text: ", decrypted_text)
