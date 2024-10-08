def task_1(input_bits):
    # Step 1: Start with the 8-bit input
    original_message = [int(bit) for bit in '10101010']  # Example 8 bits
    original_length = len(original_message)  # Original length (in bits)

    # Step 2: Append a '1' bit
    padded_message = original_message + [1]
    print(f'After adding 1 lenngth : {len(padded_message)}')
    print(f'After adding 1: {padded_message}')
    

    # Step 3: Add zeros to make total length 896 bits
    # Current length is 9 bits (8 original + 1 for padding)
    zeros_to_add = 896 - len(padded_message)
    padded_message += [0] * zeros_to_add
    print(f'Afetr padding with 0s length : {len(padded_message)}')
    print(f'After padding with 0s: {padded_message}')

    # Step 4: Append the length of the original message in 128 bits
    length_bits = original_length  # Original length in bits
    length_binary = f'{length_bits:0128b}'  # Convert to 128-bit binary
    print(f'Length in binary 128-bits : {length_binary}')
    length_array = [int(bit) for bit in length_binary]  # Create array of bits

    # Append the length to the padded message
    final_message = padded_message + length_array
    print(f'Final padded message (1024 bits): {final_message}')
    print(f'Final message length: {len(final_message)} bits')




# Call the function
task_1('10101010')


def decimal_to_binary_manual(n, bit_length):
    binary_representation = []
    
    # Convert to binary by dividing by 2 and storing the remainders
    while n > 0:
        binary_representation.append(n % 2)
        n //= 2
    
    # Reverse the list to get the correct binary order
    binary_representation.reverse()

    # Pad with zeros to make it `bit_length` bits long
    while len(binary_representation) < bit_length:
        binary_representation.insert(0, 0)
    
    return binary_representation

# Convert 8 to 128-bit binary manually
binary_128_bit = decimal_to_binary_manual(8, 128)
print(f'Length in binary 128-bits: {binary_128_bit}')








