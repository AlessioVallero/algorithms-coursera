class Permutation:
    """
    Given two integer arrays of size n, determine whether one is a permutation of the other.
    """

    @staticmethod
    def equals(a, b):
        if len(a) != len(b):
            return False

        sorted_a = sorted(a)
        sorted_b = sorted(b)

        i = 0
        while i < len(sorted_a):
            if sorted_a[i] != sorted_b[i]:
                return False
            i += 1

        return True


def unit_test():
    """
    Test TwoSets
    """

    first_ar = [5, 4, 1]
    second_ar = [1, 5, 4]

    print(Permutation.equals(first_ar, second_ar))


unit_test()
