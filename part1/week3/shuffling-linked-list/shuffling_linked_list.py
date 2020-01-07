# Shuffling a linked list. Given a singly-linked list containing n items, rearrange the items uniformly at random.
# Your algorithm should consume a logarithmic (or constant) amount of extra memory and
# run in time proportional to nlogn in the worst case.

import time
import numpy as np


class Merge:
    """
    Perform MergeSort
    """

    @staticmethod
    def __compute_linked_list_size(linked_list):
        """
        Traverse linked list until the end to find the size

        :param linked_list: Head of the linked list
        :return: Size of the list
        """

        size = 0

        runner = linked_list.head
        while runner:
            runner = runner.next
            size += 1

        return size

    @staticmethod
    def __merge(left_node, right_node, shuffle):
        i_node = left_node
        j_node = right_node

        # Divide first check from while loop below to properly store head of the ret list
        # If we shuffle, decision is taken randomly, otherwise we check if left elem is smaller then right elem
        if (not shuffle and i_node.data < j_node.data) or (shuffle and np.random.randint(0, 2, 1)[0] > 0):
            runner = i_node
            i_node = i_node.next
        # If we shuffle, decision is taken randomly, otherwise we check if right elem is smaller then left elem
        else:
            runner = j_node
            j_node = j_node.next

        # Head of return linked list
        merged_ret = runner

        # Until one of the two halves are not fully processed...
        while i_node or j_node:
            # Left list is done, we just append the right list from this point
            if i_node is None:
                runner.next = j_node
                j_node = j_node.next
            # Right list is done, we just append the left list from this point
            elif j_node is None:
                runner.next = i_node
                i_node = i_node.next
            # If we shuffle, decision is taken randomly, otherwise we check if left elem is smaller then right elem
            elif (not shuffle and i_node.data < j_node.data) or (shuffle and np.random.randint(0, 2, 1)[0] > 0):
                runner.next = i_node
                i_node = i_node.next
            # If we shuffle, decision is taken randomly, otherwise we check if right elem is smaller then left elem
            else:
                runner.next = j_node
                j_node = j_node.next
            # Increment runner
            runner = runner.next

        return merged_ret

    @staticmethod
    def __find_center_node(start_node, center_index):
        for i in range(center_index):
            start_node = start_node.next

        return start_node

    @staticmethod
    def __merge_sort(node, left, right, shuffle):
        if left < right:
            center = (left + right) // 2
            m_node = Merge.__find_center_node(node, center - left)

            ls_node = node
            rs_node = m_node.next
            m_node.next = None

            # Sort/Shuffle left half
            sorted_left = Merge.__merge_sort(ls_node, left, center, shuffle)
            # Sort/Shuffle right half
            sorted_right = Merge.__merge_sort(rs_node, center + 1, right, shuffle)
            # Merge/Shuffle left and right halves together
            return Merge.__merge(sorted_left, sorted_right, shuffle)
        return node

    @staticmethod
    def sort(linked_list, shuffle=False):
        size = Merge.__compute_linked_list_size(linked_list)

        # If length is smaller than 2, no need to shuffle
        if size > 1:
            # Perform MergeShuffle
            linked_list.head = Merge.__merge_sort(linked_list.head, 0, size - 1, shuffle)


class ListNode:
    """
    A node in a singly-linked list.
    """
    def __init__(self, data=None, next=None):
        self.data = data
        self.next = next

    def __repr__(self):
        return repr(self.data)


class SinglyLinkedList:
    def __init__(self):
        """
        Create a new singly-linked list.
        Takes O(1) time.
        """
        self.head = None

    def __repr__(self):
        """
        Return a string representation of the list.
        Takes O(n) time.
        """
        nodes = []
        curr = self.head
        while curr:
            nodes.append(repr(curr))
            curr = curr.next
        return '[' + ', '.join(nodes) + ']'

    def prepend(self, data):
        """
        Insert a new element at the beginning of the list.
        Takes O(1) time.
        """
        self.head = ListNode(data=data, next=self.head)

    def append(self, data):
        """
        Insert a new element at the end of the list.
        Takes O(n) time.
        """
        if not self.head:
            self.head = ListNode(data=data)
            return
        curr = self.head
        while curr.next:
            curr = curr.next
        curr.next = ListNode(data=data)

    def find(self, key):
        """
        Search for the first element with `data` matching
        `key`. Return the element or `None` if not found.
        Takes O(n) time.
        """
        curr = self.head
        while curr and curr.data != key:
            curr = curr.next
        return curr  # Will be None if not found

    def remove(self, key):
        """
        Remove the first occurrence of `key` in the list.
        Takes O(n) time.
        """
        # Find the element and keep a
        # reference to the element preceding it
        curr = self.head
        prev = None
        while curr and curr.data != key:
            prev = curr
            curr = curr.next
        # Unlink it from the list
        if prev is None:
            self.head = curr.next
        elif curr:
            prev.next = curr.next
            curr.next = None

    def reverse(self):
        """
        Reverse the list in-place.
        Takes O(n) time.
        """
        curr = self.head
        prev_node = None
        next_node = None
        while curr:
            next_node = curr.next
            curr.next = prev_node
            prev_node = curr
            curr = next_node
        self.head = prev_node


def unit_test():
    """
    Test shuffling_linked_list
    """
    size = 8
    a = [7, 6, 5, 4, 3, 2, 1, 0]
    a.reverse()
    a.reverse()

    linked_list = SinglyLinkedList()

    for i in range(size):
        linked_list.append(a[i])

    print("Unsorted Linked List: ", linked_list)

    start = time.time()
    Merge.sort(linked_list, shuffle=True)
    end = time.time()

    print("Sorted Linked List: ", linked_list)
    print("Elapsed: ", (end - start))


unit_test()
