class StackWithMax:
    """
    Create a stack and keep track of the max element on it.
    """

    def __init__(self):
        """
        Init a stack and an helper stack with max tracking
        """

        self.__stack = []
        self.__max_stack = []

    def push(self, item):
        """
        Push a new item and push the max between current max and current element
        :param item: The item to push
        """

        self.__stack.append(item)

        if len(self.__max_stack) > 0:
            current_max = self.__max_stack[-1]
            if item > current_max:
                # If current item is greater than current max, it goes at the top of the stack
                self.__max_stack.append(item)
            else:
                # If current item is smaller than current max, we push the current max once again
                self.__max_stack.append(current_max)
        else:
            self.__max_stack.append(item)

    def pop(self):
        """
        Pop item from top of the stack (LIFO) and max stack
        :return: The item on top of the stack
        """

        self.__max_stack.pop()

        return self.__stack.pop()

    def size(self):
        """
        Return the size of the stack.
        :return: Size of the stack
        """
        return len(self.__stack)

    def is_empty(self):
        """
        Check whether stack is empty.
        :return: True if empty, False otherwise.
        """
        return self.size() == 0

    def return_the_maximum(self):
        """
        Return the maximum element in the stack, which is always on top of the stack
        :return: The maximum element in the stack
        """

        return self.__max_stack[-1]


def unit_test():
    """
    Test QueueWithTwoStacks
    """

    stack = StackWithMax()
    stack.push(20)
    stack.push(10)
    stack.push(30)

    print(stack.return_the_maximum())
    stack.pop()
    print(stack.return_the_maximum())
    stack.pop()
    print(stack.return_the_maximum())
    stack.pop()


unit_test()
