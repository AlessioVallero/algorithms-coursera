class Pebbles:
    """
    Given an array of n buckets, each containing a red, white, or blue pebble, sort them by color according to the dutch national flag.
    """

    def __init__(self, ar):
        self.__ar = ar

    @staticmethod
    def __color(i):
        if i == "white":
            return 0
        elif i == "red":
            return -1
        else:
            return 1

    def __swap(self, i, j):
        current = self.__ar[i]
        self.__ar[i] = self.__ar[j]
        self.__ar[j] = current

    def dutch_sort(self):
        if len(self.__ar) < 2:
            return

        i = 0
        reds = 0
        blues = len(self.__ar) - 1
        while i < blues:
            compared_color = Pebbles.__color(self.__ar[i])
            # Red
            if compared_color == -1:
                # Move element i in the reds side and increment both current element and reds "end" pointer
                self.__swap(i, reds)
                i += 1
                reds += 1
            # White
            elif compared_color == 0:
                # No need to move but we obviously need to keep going with another element in the next iteration
                i += 1
            # Blue
            else:
                # Move element i in the blues side and decrement blues "init" pointer
                self.__swap(i, blues)
                blues -= 1


def unit_test():
    """
    Test Pebbles
    """

    ar = ["blue", "white", "blue", "red", "red", "white", "blue", "white", "blue","red", "blue"]
    pebbles = Pebbles(ar)
    pebbles.dutch_sort()

    print(ar)


unit_test()
