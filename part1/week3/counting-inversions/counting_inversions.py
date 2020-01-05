# Counting inversions.
# An inversion in an array a[] is a pair of entries a[i] and a[j] such that i < j but a[i] > a[j].
# Given an array, design a linearithmic algorithm to count the number of inversions.

import time


class Merge:
    """
    Perform MergeSort to a python list.
    """

    @staticmethod
    def __merge(a, aux, left, center, right):
        count_inversions = 0

        # If the last element of first half is smaller than first element of second half, there's no need to merge
        if a[center] > a[center+1]:
            i = left  # Starting index for left side
            j = center + 1  # Starting index for right side
            aux_index = left  # Starting index in the auxiliary list

            # Until one of the two halves are not fully processed...
            while i <= center and j <= right:
                # If left element is smaller we copy it to the aux list
                if a[i] < a[j]:
                    aux[aux_index] = a[i]
                    i += 1
                # If right element is smaller we copy it to the aux list
                else:
                    aux[aux_index] = a[j]
                    count_inversions += (center + 1 - i)
                    j += 1
                aux_index += 1

            # If left list is not fully processed, we append it to the aux list
            while i <= center:
                aux[aux_index] = a[i]
                i += 1
                aux_index += 1

            # If right list is not fully processed, we append it to the aux list
            while j <= right:
                aux[aux_index] = a[j]
                j += 1
                aux_index += 1

            # Copy back auxiliary list content to original list
            # (from left to right only)
            for i in range(left, right + 1):
                a[i] = aux[i]

        return count_inversions

    @staticmethod
    def __merge_sort(a, aux, left, right):
        count_inversions = 0

        if left < right:
            center = (left + right) // 2
            # Sort left half
            count_inversions += Merge.__merge_sort(a, aux, left, center)
            # Sort right half
            count_inversions += Merge.__merge_sort(a, aux, center + 1, right)
            # Merge sorted left and right halves
            count_inversions += Merge.__merge(a, aux, left, center, right)

        return count_inversions

    @staticmethod
    def sort(a):
        # If length is smaller than 2, no need to sort
        if len(a) > 1:
            # Create O(n) auxiliary list
            aux = [None] * len(a)
            # Perform MergeSort
            return Merge.__merge_sort(a, aux, 0, len(a) - 1)

        return 0


def unit_test():
    """
    Test merge_sort
    """
    size = 4
    a = [i for i in range(size) if i % 2 == 0] + [i for i in range(size) if i % 2 != 0]
    a.reverse()

    print(a)

    start = time.time()
    print(Merge.sort(a.copy()))
    end = time.time()
    print("Elapsed: ", (end - start))


unit_test()
