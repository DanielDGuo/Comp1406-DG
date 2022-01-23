import java.util.ArrayList;
import java.util.List;

public class StringCollectionTester {
    public static void main(String[] args){
        StringCollection sc = new StringCollectionImplementation();
        System.out.println("Initial size should be 0: " + sc.size());
        System.out.println("Adding some words...");
        sc.add("dog");
        sc.add("cat");
        sc.add("elephant");
        System.out.println("Size should now be 3: " + sc.size());
        System.out.println("Index 0 should be dog: " + sc.get(0));
        System.out.println("Index 1 should be cat: " + sc.get(1));
        System.out.println("Index 2 should be elephant: " + sc.get(2));
        System.out.println("Removing index 1");
        sc.remove(1);
        System.out.println("Index 1 should be elephant: " + sc.get(1));
        System.out.println("Index 2 should be null: " + sc.get(2));
        System.out.println("Adding some more words");
        sc.add("dog");
        sc.add("cat");
        sc.add("apple");
        System.out.println("Index of dog should be 0: " + sc.indexOf("dog"));
        System.out.println("Index of cat should be 3: " + sc.indexOf("cat"));
        System.out.println("Index of apple should be 4: " + sc.indexOf("apple"));
        System.out.println("Index of doge should be -1: " + sc.indexOf("doge"));
        System.out.println("Index of do should be -1: " + sc.indexOf("do"));
        System.out.println("Contains elephant should be true: " + sc.contains("elephant"));
        System.out.println("Clearing.");
        sc.clear();
        System.out.println("Size should now be 0: " + sc.size());
        System.out.println("Index of dog should be -1: " + sc.indexOf("dog"));
        System.out.println("Contains elephant should be false: " + sc.contains("elephant"));
        System.out.println("Adding words...");
        sc.add("dog");
        sc.add("cat");
        sc.add("pot");
        sc.add("pie");
        sc.add("dog");
        sc.add("cat");
        sc.add("pot");
        sc.add("pie");
        System.out.println("Size should now be 8: " + sc.size());
        System.out.println("Creating a second collection...");
        StringCollectionImplementation sc2 = new StringCollectionImplementation();
        System.out.println("Adding words to second collection.");
        sc2.add("dog");
        sc2.add("pie");
        sc2.add("dog");
        sc2.add("gary");
        System.out.println("Adding second collection to first");
        sc.addAll(sc2);
        System.out.println("Size should now be 12: " + sc.size());
        System.out.println("String at index 5 should be cat: " + sc.get(5));
        System.out.println("String at index 9 should be pie: " + sc.get(9));
        System.out.println("String at index 11 should be gary: " + sc.get(11));
        System.out.println("String at index 12 should be null: " + sc.get(12));
        System.out.println("String at index -1 should be null: " + sc.get(-1));
        System.out.println("Size of second collection should be 4: " + sc2.size());
        System.out.println("Second collection should still contain dog: " + sc2.contains("dog"));
        System.out.println("Second collection should still contain pie: " + sc2.contains("pie"));
        System.out.println("Second collection should not contain cat: " + sc2.contains("cat"));
        System.out.println("Unique string count should be 5: " + sc.getUniqueStringCount());
        System.out.println("Frequency of dog should be 4: " + sc.getFrequency("dog"));
        System.out.println("Frequency of cat should be 2: " + sc.getFrequency("cat"));
        System.out.println("Frequency of dig should be 0: " + sc.getFrequency("dig"));
        System.out.println("Adding more words...");
        sc.add("do");
        sc.add("doge");
        sc.add("doggy");
        sc.add("dreary");
        sc.add("dot");
        sc.add("dobby");
        sc.add("dremel");
        sc.add("dogg");
        System.out.println("Size of unique by prefix for d should be 9: " + sc.getUniqueByPrefix("d").size());
        System.out.println("Size of unique by prefix for do should be 7: " + sc.getUniqueByPrefix("do").size());
        System.out.println("Size of unique by prefix for dog should be 4: " + sc.getUniqueByPrefix("dog").size());
        System.out.println("Size of unique by prefix for dr should be 2: " + sc.getUniqueByPrefix("dr").size());
        System.out.println("Size of unique by prefix for p should be 2: " + sc.getUniqueByPrefix("p").size());
        System.out.println("Size of unique by prefix for pi should be 1: " + sc.getUniqueByPrefix("pi").size());
        System.out.println("Size of unique by prefix for pie should be 1: " + sc.getUniqueByPrefix("pie").size());

        System.out.println();
        System.out.println("Expected sorted words at this point: [\"cat\", \"cat\", \"do\", \"dobby\", \"dog\", \"dog\", \"dog\", \"dog\", \"doge\", \"dogg\", \"doggy\", \"dot\", \"dreary\", \"dremel\", \"gary\", \"pie\", \"pie\", \"pie\", \"pot\", \"pot\"]");
        List<String> sorted = sc.getSortedWords();
        System.out.println("Sorted words should match expected sorted words (should be true): " + checkSortedListsEqual(getExpectedSortedWordList(),sorted));
        System.out.println();

        System.out.println("Clearing.");
        sc.clear();
        System.out.println("Adding some more words..");
        sc.add(new String("cat"));
        sc.add(new String("cat"));
        sc.add(new String("dog"));
        sc.add(new String("zebra"));
        System.out.println("Contains cat should be true: " + sc.contains(new String("cat")));
        System.out.println("Frequency of cat should be 2: " + sc.getFrequency(new String("cat")));
        System.out.println("Index of of cat should be 0: " + sc.indexOf(new String("cat")));
        System.out.println("Removing index 0");
        sc.remove(0);
        System.out.println("Index of of cat should be 0: " + sc.indexOf(new String("cat")));
        System.out.println("Removing index 0");
        sc.remove(0);
        System.out.println("Index of of cat should be -1: " + sc.indexOf(new String("cat")));
        System.out.println("Contains cat should be false: " + sc.contains(new String("cat")));
    }

    public static List<String> getExpectedSortedWordList(){
        List<String> result = new ArrayList<String>();
        String[] x = {"cat", "cat", "do", "dobby", "dog", "dog", "dog", "dog", "doge", "dogg", "doggy", "dot", "dreary", "dremel", "gary", "pie", "pie", "pie", "pot", "pot"};
        for(String w: x){
            result.add(w);
        }
        return result;
    }

    public static boolean checkSortedListsEqual(List<String> a, List<String> b){
        if(a.size() != b.size()){
            return false;
        }

        for(int i = 0; i < a.size(); i++){
            if(!a.get(i).equals(b.get(i))){
                return false;
            }
        }

        return true;
    }
}
