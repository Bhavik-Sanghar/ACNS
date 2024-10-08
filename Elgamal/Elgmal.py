import sympy
import random


#function for find genrator all g
def find_primitive_roots(val):
    roots = []
    
    # Loop through all candidates g from 2 to val-1
    for g in range(2, val):
        unique_set = set()  # To store the results of g^i % val
        
        # Loop over powers from 1 to val-1
        for i in range(1, val):
            result = pow(g, i, val)  # g^i % val
            unique_set.add(result)
        
        # Check if the set contains exactly val-1 elements (i.e., order = val - 1)
        if len(unique_set) == val - 1:
            roots.append(g)
    
    return roots

#find first genrator g for val
def find_first_primitive_root(val):
    # Loop through all candidates g from 2 to val-1
    for g in range(2, val):
        unique_set = set()  # To store the results of g^i % val
        
        # Loop over powers from 1 to val-1
        for i in range(1, val):
            result = pow(g, i, val)  # g^i % val
            unique_set.add(result)
        
        # Check if the set contains exactly val-1 elements (i.e., order = val - 1)
        if len(unique_set) == val - 1:
            return g  # Return the first primitive root found
    
    return None  # Return None if no primitive root is found
        
def modular_inverse(s, p):
    # Loop through all values from 1 to p-1
    for i in range(1, p):
        if (s * i) % p == 1:
            return i  # i is the modular inverse of s
    return None  # If no modular inverse is found (this shouldn't happen with primes)


def main():
    # Prime Number 
    p = sympy.randprime(10**0,10**2)
    print(f"Large prime Number is : {p}")

    # We find g for peime number p 
    g = find_first_primitive_root(p)
    print(f"The smallest primitive root of {p} is: {g}")

    # We Take Any random private number say x in range of (1,p-2) and key k in same range 
    x = random.randint(1,p-2)
    print(f"X is : {x}")
    k = random.randint(1,p-2)
    print(f"K is : {k}")

    # Now We find h Public Key h = (g ^ x) mod p 
    h = pow(g,x,p)
    print(f"Public Key is : {h}")

    # We Take message  m in range of (1,p-1)
    m = 7  # (Here we take static message)
    print(f"Message is : {m}")


    # Encyption (c1 , c2)
    print("-*-*-*-* Encryption -*-*-*-*")
    # c1 = (g ^ k) mod p
    c1 = pow(g,k,p)
    print(f"C1 is : {c1}")

    # c2 = m * ((h ^ k) mod p) mod p
    c2 = (m * pow(h,k,p)) % p
    print(f"C2 is : {c2}")

    # Decyption
    print("-*-*-*-* Decryption -*-*-*-*")
    # First we find S here s = (c1 ^ x) mod p 
    s = pow(c1,x,p)
    print(f"Here S is : {s}")
    # Now for Message M we have to do M = c2 * s ^-1 (S inverse) mod p 
    # Step 1 : we find S_inverse (S ^-1) (Modular Inverse)
    s_inv = modular_inverse(s,p)
    print(f"Here S_inverse is : {s_inv}")
    # Step 2 : we find M (Message)
    Message = (c2 * s_inv) % p
    print(f"Message is : {Message}")


if __name__ == "__main__":
    main()