class QueueWithTwoStacks:
    """Create a queue using two stacks."""

    def __init__(self):
        self.__first_stack = []
        self.__second_stack = []

    def enqueue(self, item):
        """
        Enqueue an item in first Stack and reverse the second Stack.
        :param item: Object to enqueue
        """

        # Reverse current(first) Stack if not empty into second(backup) Stack
        while len(self.__first_stack) > 0:
            elem = self.__first_stack.pop()
            self.__second_stack.append(elem)

        # Append new element into now empty current(first) Stack
        self.__first_stack.append(item)

        # Reverse second(backup) Stack into current(first) Stack on top of the new element just appended
        while len(self.__second_stack) > 0:
            elem = self.__second_stack.pop()
            self.__first_stack.append(elem)

    def dequeue(self):
        """
        Return the oldest element in queue (LIFO).
        :return: Oldest element in queue
        """

        return self.__first_stack.pop()

    def size(self):
        """
        Return the size of the queue.
        :return: Size of the queue
        """
        return len(self.__first_stack)

    def is_empty(self):
        """
        Check whether queue is empty.
        :return: True if empty, False otherwsie.
        """
        return self.size() == 0


def unit_test():
    """
    Test QueueWithTwoStacks
    """

    queue = QueueWithTwoStacks()
    queue.enqueue(10)
    queue.enqueue(20)
    queue.enqueue(30)

    print(queue.dequeue())
    print(queue.dequeue())
    print(queue.dequeue())


unit_test()
