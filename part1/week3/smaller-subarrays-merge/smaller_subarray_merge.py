# Merging with smaller auxiliary array.
# Suppose that the subarray 𝚊[𝟶] to 𝚊[𝚗−𝟷] is sorted and the subarray 𝚊[𝚗] to 𝚊[𝟸∗𝚗−𝟷] is sorted.
# How can you merge the two subarrays so that 𝚊[𝟶] to 𝚊[𝟸∗𝚗−𝟷] is sorted
# using an auxiliary array of length n (instead of 2n)?

import time


def smaller_subarrays_merge(a):
    # Auxiliary buffer is half of the a total length
    n = int(len(a) / 2)
    # Array is already sorted if last element of first half is smaller
    # then first element of second half
    if a[n - 1] < a[n]:
        return

    # Init aux array
    aux = [None for i in range(n)]

    i = 0  # First sub array start
    j = n  # Second sub array start
    k = 0  # Aux array index
    z = n  # In place copy start

    while i < n and j < 2 * n:
        # The smaller element between the two arrays is selected
        if a[i] < a[j]:
            next_elem = a[i]
            i += 1
        else:
            next_elem = a[j]
            j += 1

        # Store the selected element either in the auxiliary buffer or the in place buffer
        if k < n:
            aux[k] = next_elem
            k += 1
        else:
            a[z] = next_elem
            z += 1

    # Copy remaining unprocessed elements (only one of the two arrays is unfinished)
    while i < 2 * n:
        a[z] = a[i]
        i += 1
        z += 1
    while j < 2 * n:
        a[z] = a[j]
        j += 1
        z += 1

    # Copy aux array back to first half
    for i in range(n):
        a[i] = aux[i]


def unit_test():
    """
    Test smaller_subarrays_merge
    """
    half_size = 40000000
    a = [i for i in range(half_size) if i % 2 == 0]
    b = [i for i in range(half_size) if i % 2 != 0]

    a = a + b

    start = time.time()
    smaller_subarrays_merge(a)
    end = time.time()

    print(end - start)


unit_test()
