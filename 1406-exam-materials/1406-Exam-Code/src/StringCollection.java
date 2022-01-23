/*
A StringCollection will store a collection of strings. This collection must maintain
the order of items and allow duplicates, like a list. You should make an effort to minimize
the computational complexity of your StringCollection implementation.

You may not change this file or add anything to it. You must work with
the provided interface specification.
*/

import java.util.List;

public interface StringCollection {
    /**
     * Adds the given string to the end of this collection.
     * It should be possible to implement this method in O(1) time.
     * @param s the string to add to the collection
     */
    void add(String s);

    /**
     * Adds all of the strings from the given StringCollection, in order,
     * to the calling StringCollection. This method must not modify the
     * given StringCollection in any way.
     * It should be possible to implement this method in O(k) time, where
     * k is the number of elements in the given StringCollection.
     */
    void addAll(StringCollection list);

    /**
     * Clears all entries within the calling StringCollection.
     */
    void clear();

    /**
     * Returns true (or false) to indicate whether the given string is
     * contained (or not) within the calling StringCollection.
     * It should be possible to implement this method in O(1) time.
     * @param s the string to search for
     * @return true if s is in the StringCollection, false otherwise
     */
    boolean contains(String s);

    /**
     * Retrieve the string at the given index within the calling StringCollection
     * @param index the index to retrieve (0 represents the start of the collection)
     * @return the string at the given index if the index is valid, null otherwise
     */
    String get(int index);

    /**
     * Returns the first/earliest index at which the string s occurs in the collection.
     * If the string does not occur in the collection, returns -1.
     * @param s the string to search for
     * @return the index of the first occurrence of the given string in the collection, or -1
     * if the given string is not present
     */
    int indexOf(String s);

    /**
     * Removes and returns the string at the given index. Shifts any following elements to
     * the left by 1 (decreases their index by 1). If the index is invalid, returns null.
     * @param index the index to remove from the collection
     * @return the string that was removed, or null if the index was not valid
     */
    String remove(int index);

    /**
     * Returns the number of strings contained in the StringCollection. Duplicates
     * should be counted.
     * It should be possible to implement this method in O(1) time.
     * @return the number of strings contained in the calling collection.
     */
    int size();

    /**
     * Returns the number of unique strings contained in the collection (i.e., not
     * counting duplicates).
     * It should be possible to implement this method in O(1) time.
     * @return the number of unique strings contained in the collection
     */
    int getUniqueStringCount();

    /**
     * Returns a new StringCollection containing all strings from the calling
     * StringCollection that begin with the specified prefix. Duplicate strings
     * should not be included. The order of the results is unimportant. You should
     * instantiate an additional instance of your own StringCollectionImplementation
     * to return from this method.
     * @param prefix the prefix to match
     * @return a new StringCollection containing all strings from the calling
     * StringCollection that begin with the specified prefix.
     */
    StringCollection getUniqueByPrefix(String prefix);

    /**
     * Returns the number of times the given string occurs in the calling
     * StringCollection.
     * It should be possible to implement this method in O(1) time.
     * @param s the string to count
     * @return the frequency of s in the calling StringCollection
     */
    int getFrequency(String s);

    /**
     * Returns a new List containing the words within the calling StringCollection
     * in lexicographically (i.e., alphabetical) sorted order. Duplicate words
     * should be included.
     * @return a list of lexicographically sorted words from the StringCollection
     */
    List<String> getSortedWords();
}
