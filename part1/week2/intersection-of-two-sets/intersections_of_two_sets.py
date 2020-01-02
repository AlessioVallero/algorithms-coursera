from functools import cmp_to_key


class TwoSets:
    """
    Given two arrays a[] and b[], each containing n distinct 2D points in the plane
    we count the number of points that are contained both in array a[] and array b[]
    """

    @staticmethod
    def compare(first, second):
        if first[0] < second[0] or (first[0] == second[0] and first[1] < second[1]):
            return -1
        elif first[0] > second[0] or (first[0] == second[0] and first[1] > second[1]):
            return 1
        else:
            return 0

    @staticmethod
    def intersection(a, b):
        if len(a) < 1 or len(b) < 1:
            return 0

        sorted_a = sorted(a, key=cmp_to_key(TwoSets.compare))
        sorted_b = sorted(b, key=cmp_to_key(TwoSets.compare))

        i = j = count = 0
        while i < len(sorted_a) and j < len(sorted_b):
            compare_elements = TwoSets.compare(sorted_a[i], sorted_b[j])
            if compare_elements == 0:
                count += 1
                i += 1
                j += 1
            elif compare_elements == -1:
                i += 1
            else:
                j += 1

        return count


def unit_test():
    """
    Test TwoSets
    """

    first_ar = [[10, 1], [4, 5], [7, 2]]
    second_ar = [[50, 4], [7, 2], [17, 2], [10, 1], [4, 5]]

    print(TwoSets.intersection(first_ar, second_ar))


unit_test()
