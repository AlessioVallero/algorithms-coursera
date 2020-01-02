class Permutation:
    """
    Given two integer arrays of size n, determine whether one is a permutation of the other.
    """

    @staticmethod
    def equals(a, b):
        a.sort()
        b.sort()

        if len(a) != len(b):
            return False

        i = 0
        while i < len(a):
            if a[i] != b[i]:
                return False
            i += 1

        return True


def unit_test():
    """
    Test TwoSets
    """

    first_ar = [5, 4, 1]
    second_ar = [1, 6, 4]

    print(Permutation.equals(first_ar, second_ar))


unit_test()
