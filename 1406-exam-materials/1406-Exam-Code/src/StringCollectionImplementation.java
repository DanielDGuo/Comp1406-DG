import java.util.*;

/*
Write an implementation of the StringCollection interface here. You may store
the data in any way you wish. While minimizing the overall computational complexity
of your solution is ideal, you should prioritize minimizing the computational
complexity of the getFrequency and getByPrefix methods. You may use additional
storage/memory to facilitate the minimization of computational complexity.
*/
public class StringCollectionImplementation implements StringCollection {
    //Stores a string and its frequency. Has a smaller time complexity on some methods. Doesn't keep duplicate entry order
    private Map<String, Integer> strColMap;
    //A list that stores order. Stores the word order. Keeps duplicate entry order
    private ArrayList<String> strColList;

    //Your class must support this zero argument constructor
    public StringCollectionImplementation() {
        strColMap = new HashMap<>();
        strColList = new ArrayList<>();
    }

    /**
     * Adds the given string to the end of this collection.
     * It should be possible to implement this method in O(1) time.
     *
     * @param s the string to add to the collection
     */
    public void add(String s) {
        if (strColMap.containsKey(s)) {
            strColMap.replace(s, strColMap.get(s) + 1);
        } else {
            strColMap.put(s, 1);
        }
        strColList.add(s);
    }

    /**
     * Adds all of the strings from the given StringCollection, in order,
     * to the calling StringCollection. This method must not modify the
     * given StringCollection in any way.
     * It should be possible to implement this method in O(k) time, where
     * k is the number of elements in the given StringCollection.
     *
     * @param list
     */
    public void addAll(StringCollection list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    /**
     * Clears all entries within the calling StringCollection.
     */
    public void clear() {
        strColMap.clear();
        strColList.clear();
    }

    /**
     * Returns true (or false) to indicate whether the given string is
     * contained (or not) within the calling StringCollection.
     * It should be possible to implement this method in O(1) time.
     *
     * @param s the string to search for
     * @return true if s is in the StringCollection, false otherwise
     */
    public boolean contains(String s) {
        return strColMap.containsKey(s);
    }

    /**
     * Retrieve the string at the given index within the calling StringCollection
     *
     * @param index the index to retrieve (0 represents the start of the collection)
     * @return the string at the given index if the index is valid, null otherwise
     */
    public String get(int index) {
        if (index >= strColList.size() || index < 0) {
            return null;
        }
        return strColList.get(index);
    }

    /**
     * Returns the first/earliest index at which the string s occurs in the collection.
     * If the string does not occur in the collection, returns -1.
     *
     * @param s the string to search for
     * @return the index of the first occurrence of the given string in the collection, or -1
     * if the given string is not present
     */
    public int indexOf(String s) {
        return strColList.indexOf(s);
    }

    /**
     * Removes and returns the string at the given index. Shifts any following elements to
     * the left by 1 (decreases their index by 1). If the index is invalid, returns null.
     *
     * @param index the index to remove from the collection
     * @return the string that was removed, or null if the index was not valid
     */
    public String remove(int index) {
        if (index >= strColList.size()|| index < 0) {
            return null;
        }

        String removedStr = strColList.remove(index);
        if (strColMap.get(removedStr) == 1) {
            strColMap.remove(removedStr);
        } else {
            strColMap.replace(removedStr, strColMap.get(removedStr) - 1);
        }
        return removedStr;
    }

    /**
     * Returns the number of strings contained in the StringCollection. Duplicates
     * should be counted.
     * It should be possible to implement this method in O(1) time.
     *
     * @return the number of strings contained in the calling collection.
     */
    public int size() {
        return strColList.size();
    }

    /**
     * Returns the number of unique strings contained in the collection (i.e., not
     * counting duplicates).
     * It should be possible to implement this method in O(1) time.
     *
     * @return the number of unique strings contained in the collection
     */
    public int getUniqueStringCount() {
        return strColMap.size();
    }

    /**
     * Returns a new StringCollection containing all strings from the calling
     * StringCollection that begin with the specified prefix. Duplicate strings
     * should not be included. The order of the results is unimportant. You should
     * instantiate an additional instance of your own StringCollectionImplementation
     * to return from this method.
     *
     * @param prefix the prefix to match
     * @return a new StringCollection containing all strings from the calling
     * StringCollection that begin with the specified prefix.
     */
    public StringCollection getUniqueByPrefix(String prefix) {
        StringCollection returnstrColMap = new StringCollectionImplementation();
        for (String s : strColMap.keySet()) {
            if (s.startsWith(prefix)) {
                returnstrColMap.add(s);
            }
        }
        return returnstrColMap;
    }

    /**
     * Returns the number of times the given string occurs in the calling
     * StringCollection.
     * It should be possible to implement this method in O(1) time.
     *
     * @param s the string to count
     * @return the frequency of s in the calling StringCollection
     */
    public int getFrequency(String s) {
        if(!strColMap.containsKey(s)){
            return 0;
        }
        return strColMap.get(s);
    }

    /**
     * Returns a new List containing the words within the calling StringCollection
     * in lexicographically (i.e., alphabetical) sorted order. Duplicate words
     * should be included.
     *
     * @return a list of lexicographically sorted words from the StringCollection
     */
    public List<String> getSortedWords() {
        List<String> sortedList = strColList;
        Collections.sort(sortedList);
        return sortedList;
    }
}
