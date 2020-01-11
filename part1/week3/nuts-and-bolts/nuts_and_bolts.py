# Nuts and bolts.
# A disorganized carpenter has a mixed pile of n nuts and n bolts.
# The goal is to find the corresponding pairs of nuts and bolts.
# Each nut fits exactly one bolt and each bolt fits exactly one nut.
# By fitting a nut and a bolt together, the carpenter can see which one is bigger
# (but the carpenter cannot compare two nuts or two bolts directly).
# Design an algorithm for the problem that uses at most proportional to nlogn compares (probabilistically).

import time
import numpy as np


class Quick:
    """
    Perform QuickSort
    """

    @staticmethod
    def __partition(arr, lo, hi, pivot):
        i = j = lo

        # Same as standard QuickSort "hi pivot" partitioning, except that the pivot is coming
        # from outside so we need to move it to the "hi" index as soon as we find it to make
        # the partitioning technique equivalent
        while j < hi:
            # Classic partitioning
            if arr[j] < pivot:
                arr[i], arr[j] = arr[j], arr[i]
                i += 1
            # We've found the item equivalent to the pivot, so we move it to "hi" to match the method
            elif arr[j] == pivot:
                arr[hi], arr[j] = arr[j], arr[hi]
                # We still need to process the ex hi element, to we decrease j such that we process the
                # ex hi element in the next iteration
                j -= 1
            j += 1
        # Classic partitioning, we move the pivot in the right position and return it
        arr[i], arr[hi] = arr[hi], arr[i]

        return i

    @staticmethod
    def __quick_sort_nuts_and_bolts(arr_nuts, arr_bolts, lo, hi):
        if lo < hi:
            # Choose a bolt as pivot and partition the nuts
            # The returned pivot is going to be the nut matching the bolt pivot
            # Doesn't matter which pivot we choose because we don't know the nut matching the pivot anyway
            pivot = Quick.__partition(arr_nuts, lo, hi, arr_bolts[np.random.randint(lo, hi + 1, 1)[0]])
            # The problem is stated as we cannot use nuts for nuts and bolts for bolts
            # Pivot is same as previous arr_bolts[hi], but is coming from the nuts array
            # so we can use it to partition the bolts
            Quick.__partition(arr_bolts, lo, hi, arr_nuts[pivot])

            Quick.__quick_sort_nuts_and_bolts(arr_nuts, arr_bolts, lo, pivot - 1)
            Quick.__quick_sort_nuts_and_bolts(arr_nuts, arr_bolts, pivot + 1, hi)

    @staticmethod
    def nuts_and_bolts(arr_nuts, arr_bolts):
        Quick.__quick_sort_nuts_and_bolts(arr_nuts, arr_bolts, 0, len(arr_nuts) - 1)


def unit_test():
    """
    Test nuts_and_bolts
    """

    nuts = [7, 6, 5, 4, 3, 2, 1, 0]
    bolts = [6, 5, 7, 0, 2, 3, 4, 1]

    print("Unsorted Nuts: ", nuts)
    print("Unsorted Bolts: ", bolts)

    start = time.time()
    Quick.nuts_and_bolts(nuts, bolts)
    end = time.time()

    print("Nuts: ", nuts)
    print("Bolts: ", bolts)
    print("Elapsed: ", (end - start))


unit_test()
