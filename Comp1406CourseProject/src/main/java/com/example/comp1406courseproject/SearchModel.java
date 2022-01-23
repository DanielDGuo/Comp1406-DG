package com.example.comp1406courseproject;

import java.io.*;
import java.util.*;

public class SearchModel {
    //declares variables
    private int shownSearchResultCount;
    private boolean boost;
    //note: not final as projectTester requires it to not be final
    ArrayList<SearchResultObject> shownSearchResults;

    //default constructor
    public SearchModel() {
        boost = false;
        shownSearchResults = new ArrayList<>();
        shownSearchResultCount = 10;
    }

    public ArrayList<SearchResultObject> search(String query) {
        //declares variables
        Set<String> uniqueTerms = new TreeSet<>();
        ArrayList<Double> queryVector = new ArrayList<>();
        ArrayList<ArrayList<Double>> docVectors = new ArrayList<>();
        HashMap<String, Double> allSearchResults = new HashMap<>();
        shownSearchResults = new ArrayList<>();

        //populates the TreeSet with unique terms from the query
        uniqueTerms.addAll(Arrays.asList(query.split("\\s+")));

        //construct the query vector in order of unique terms
        for (String term : uniqueTerms) {
            queryVector.add(getTFIDF(term, query.split("\\s+")));
        }


        //construct docVectors to be a 2D list of every URL's TFIDF of each term
        try {
            BufferedReader URLToIDReader = new BufferedReader(new FileReader("resources" + File.separator + "URLToID.txt"));
            while (URLToIDReader.readLine() != null) {
                ArrayList<Double> currentDocVector = new ArrayList<>();
                int currentPageID = Integer.parseInt(URLToIDReader.readLine());
                for (String term : uniqueTerms) {
                    currentDocVector.add(getTFIDF(term, getWordList(currentPageID)));
                }
                docVectors.add(currentDocVector);
            }
            URLToIDReader.close();
        } catch (IOException e) {
            System.out.println("A fatal error occurred ID 01");
            return null;
        }

        //adds all title-score pairs into a hashmap
        try {
            BufferedReader URLToIDReader = new BufferedReader(new FileReader("resources" + File.separator
                    + "URLToID.txt"));
            for (String currentURL = URLToIDReader.readLine(); currentURL != null; currentURL = URLToIDReader.readLine()) {
                int currentPageID = Integer.parseInt(URLToIDReader.readLine());
                String title = new BufferedReader(new FileReader("resources" + File.separator
                        + currentPageID + File.separator + "title.txt")).readLine();
                double score = getScore(queryVector, docVectors.get(currentPageID - 1), currentURL);
                allSearchResults.put(title, score);
            }
            URLToIDReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("A fatal error occurred ID 02");
            return null;
        } catch (IOException e) {
            System.out.println("A fatal error occurred ID 03");
            return null;
        }

        ArrayList<String> addedTitles = new ArrayList<>();
        //find the top x scores, making sure not to add the same one twice, where x is equal to shownSearchResultCount
        for (int i = 0; i < shownSearchResultCount; i++) {
            String topTitle = "";
            double topScore = -1;

            //find the top scoring index that isn't in addedIndexes
            for (String title : allSearchResults.keySet()) {
                if ((((allSearchResults.get(title) > topScore) || (allSearchResults.get(title) == topScore
                        && title.compareTo(topTitle) < 0)) && !addedTitles.contains(title))) {
                    topScore = allSearchResults.get(title);
                    topTitle = title;
                }
            }
            //append the information to the top ten list
            shownSearchResults.add(new SearchResultObject(topTitle, allSearchResults.get(topTitle)));
            addedTitles.add(topTitle);
        }
        return shownSearchResults;
    }

    //returns the base 2 log of x
    public double log2(double x) {
        return Math.log(x) / Math.log(2d);
    }

    //multiples each entry of a 2d matrix with a scalar
    public ArrayList<ArrayList<Double>> multMatrixScalar(ArrayList<ArrayList<Double>> array, double x) {
        ArrayList<ArrayList<Double>> replacementArray = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            ArrayList<Double> replacementRow = new ArrayList<>();
            for (int j = 0; j < array.get(0).size(); j++) {
                replacementRow.add(array.get(i).get(j) * x);
            }
            replacementArray.add(replacementRow);
        }
        return replacementArray;
    }

    //adds each entry of a 2D matrix with a scalar
    public ArrayList<ArrayList<Double>> addMatrixScalar(ArrayList<ArrayList<Double>> array, double x) {
        ArrayList<ArrayList<Double>> replacementArray = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            ArrayList<Double> replacementRow = new ArrayList<>();
            for (int j = 0; j < array.get(0).size(); j++) {
                replacementRow.add(array.get(i).get(j) + x);
            }
            replacementArray.add(replacementRow);
        }
        return replacementArray;
    }

    //gets the euclidean distance between two 1 x i vectors
    public double getEuclideanDistance(ArrayList<ArrayList<Double>> a, ArrayList<ArrayList<Double>> b) {
        double currentTotal = 0d;
        //for every index in each 1xn matrix, add their difference squared to the current total
        for (int i = 0; i < a.size(); i++) {
            currentTotal += Math.sqrt((a.get(0).get(i) - b.get(0).get(i)));
        }
        //square root the current total to get the answer
        return Math.sqrt(currentTotal);
    }

    //multiplies two non-null matrixs
    public ArrayList<ArrayList<Double>> multMatrix(ArrayList<ArrayList<Double>> a, ArrayList<ArrayList<Double>> b) {
        ArrayList<ArrayList<Double>> product = new ArrayList<>();
        //checks to see if they can multiply
        if (a.get(0).size() != b.size()) {
            return null;
        }

        //for every row in a, reset the current productRow list
        for (int rowa = 0; rowa < a.size(); rowa++) {
            ArrayList<Double> productRow = new ArrayList<>();
            //for every column in b, set the current product to 0
            for (int columnb = 0; columnb < b.get(0).size(); columnb++) {
                double currentProduct = 0d;
                //for every number in the row of array a, get the dot product of that and the corresponding b column.
                //then add it to the current produce
                for (int columna = 0; columna < a.get(0).size(); columna++) {
                    currentProduct += a.get(rowa).get(columna) * b.get(columna).get(columnb);
                }
                //when done with the current dot product, add it to the current row
                productRow.add(currentProduct);
            }
            //when done with the row, add it to the product
            product.add(productRow);
        }
        //when all rows are done and are added to the product,return the product
        return product;
    }

    public boolean getBoost() {
        return boost;
    }

    public double getTF(String term, String[] termList) {
        double wordCount = 0;
        for (String w : termList) {
            if (term.equals(w)) {
                wordCount++;
            }
        }
        return wordCount / (double) termList.length;
    }

    public double getIDF(String term) {
        //initiates and declares file readers
        try {
            BufferedReader URLToIDReader = new BufferedReader(new FileReader("resources"
                    + File.separator + "URLToID.txt"));
            BufferedReader PagesWithWordsReader = new BufferedReader(new FileReader("resources"
                    + File.separator + "PagesWithWords.txt"));

            //returns the number of pages
            double pageCount = URLToIDReader.lines().toList().size() / 2d;

            //returns the number of pages the term appears in
            double wordAppearances = 0;
            for (String line = PagesWithWordsReader.readLine(); line != null; line = PagesWithWordsReader.readLine()) {
                if (line.equals(term)) {
                    line = PagesWithWordsReader.readLine();
                    wordAppearances = Integer.parseInt(line);
                    break;
                }
            }
            URLToIDReader.close();
            PagesWithWordsReader.close();

            if (wordAppearances == 0) {
                return 0d;
            }
            //returns the IDF
            return log2(pageCount / (1 + wordAppearances));
        } catch (IOException e) {
            System.out.println("A fatal error has occurred ID 04");
        }
        return 0;
    }

    public double getTFIDF(String term, String[] termList) {
        return log2(1 + getTF(term, termList)) * getIDF(term);
    }

    public String[] getWordList(int pageID) {
        try {
            //initializes and declares a reader, a string, and a line
            BufferedReader wordFileReader = new BufferedReader(new FileReader("resources" + File.separator
                    + pageID + File.separator + "words.txt"));
            String words = "";
            //as long as the current line isn't empty, add it to words
            for (String currentLine = wordFileReader.readLine(); currentLine != null; currentLine = wordFileReader.readLine()) {
                words = words + currentLine + "\n";
            }
            wordFileReader.close();
            //return words split across white spaces
            return words.split("\\s+");
        } catch (IOException e) {
            System.out.println("A fatal error occurred ID 05");
        }
        return null;
    }

    //goes wrong when doc vector has a 0 entry?
    public double getScore(ArrayList<Double> queryVector, ArrayList<Double> docVector, String URL) {
        double numerator = 0d;
        double leftDenom = 0d;
        double rightDenom = 0d;
        //calculates the cosine similarity
        for (int i = 0; i < queryVector.size(); i++) {
            numerator += queryVector.get(i) * docVector.get(i);
            leftDenom += Math.pow(queryVector.get(i), 2d);
            rightDenom += Math.pow(docVector.get(i), 2d);
        }
        leftDenom = Math.sqrt(leftDenom);
        rightDenom = Math.sqrt(rightDenom);


        //set score to 0 if there will be a divide by 0 error
        if ((leftDenom * rightDenom) == 0d) {
            return 0d;
        }
        //if it's boosted, find the page rank of the url and multiply the cosine similarity by it
        //scores are rounded to the nearest3 decimal places
        else if (boost) {
            return Math.round(((numerator / (leftDenom * rightDenom))) * getPageRank(URL) * 1000d) / 1000d;
        }//set the score to the cosine similarity on default
        else {
            return Math.round((numerator / (leftDenom * rightDenom)) * 1000d) / 1000d;
        }
    }

    public double getPageRank(String URL) {
        BufferedReader pageRankReader;
        double pageCount;
        String pageRankDocChecker;

        //tries to set pageCount
        try {
            pageRankReader = new BufferedReader(new FileReader("resources" + File.separator + "pageRank.txt"));
            pageRankDocChecker = pageRankReader.readLine();
            BufferedReader URLToIDReader = new BufferedReader(new FileReader("resources" + File.separator
                    + "URLToID.txt"));
            pageCount = URLToIDReader.lines().toList().size() / 2d;
            URLToIDReader.close();
            pageRankReader.close();
        } catch (IOException e) {
            System.out.println("A fatal error has occurred ID 05");
            return -1;
        }

        //if pageRank wasn't calculated, calculate pageRank
        if (pageRankDocChecker == null) {
            //create a URLList and a pageIDList
            ArrayList<String> URLList = new ArrayList<>();
            ArrayList<Integer> pageIDList = new ArrayList<>();
            try {
                BufferedReader URLToIDReader = new BufferedReader(new FileReader("resources"
                        + File.separator + "URLToID.txt"));
                String connectionURL = URLToIDReader.readLine();
                while ((connectionURL != null)) {
                    URLList.add(connectionURL);
                    pageIDList.add(Integer.parseInt(URLToIDReader.readLine()));
                    connectionURL = URLToIDReader.readLine();
                }
                URLToIDReader.close();
            } catch (IOException e) {
                System.out.println("A fatal error occurred ID 06");
                return -1;
            }


            //create adjacency matrix
            ArrayList<ArrayList<Double>> adjacencyMatrix = new ArrayList<>();
            try {
                for (int URLCounter = 0; URLCounter < pageIDList.size(); URLCounter++) {
                    //logs in current connections for the current url
                    ArrayList<Double> currentConnections = new ArrayList<>();
                    double currentConnectionCount = 0d;

                    //for every link, check if it's connected or not
                    for (String potentialConnection : URLList) {
                        boolean connectionExists = false;
                        BufferedReader outgoingLinksReader = new BufferedReader(new FileReader("resources"
                                + File.separator + pageIDList.get(URLCounter) + File.separator + "outgoingLinks.txt"));
                        //if the link is found in the node's outgoing link file, set the flag
                        for (String connectionChecker : outgoingLinksReader.lines().toList()) {
                            if (potentialConnection.equals(connectionChecker)) {
                                connectionExists = true;
                                break;
                            }
                        }

                        //if the flag is set, append 1 and up the connection counter.If not, append 0
                        if (connectionExists) {
                            currentConnections.add(1d);
                            currentConnectionCount++;
                        } else {
                            currentConnections.add(0d);
                        }
                        outgoingLinksReader.close();
                    }

                    //currentConnections now filled, representing the current node 's connections
                    //if the connection count is not 0, set every 1 to 1 divided by the number of 1 s
                    if (currentConnectionCount > 0) {
                        for (int i = 0; i < currentConnections.size(); i++) {
                            currentConnections.set(i, currentConnections.get(i) / currentConnectionCount);
                        }
                    }
                    //if it is 0, set every 1 to 1 divided by the number of directories
                    else {
                        for (int i = 0; i < currentConnections.size(); i++) {
                            currentConnections.set(i, 1d / pageCount);
                        }
                    }

                    //append the current node connections to the adjacency matrix
                    adjacencyMatrix.add(currentConnections);
                }
            } catch (IOException e) {
                System.out.println("A fatal error occurred ID 07");
            }

            //adjacencyMatrix now completed.
            double alpha = 0.1;
            //multiply the adjacency matrix with 1 - alpha
            adjacencyMatrix = multMatrixScalar(adjacencyMatrix, 1d - alpha);
            //for every value, add alpha / numberOfDirs
            adjacencyMatrix = addMatrixScalar(adjacencyMatrix, alpha / pageCount);


            //initial values
            ArrayList<ArrayList<Double>> currentVector = new ArrayList<>();
            ArrayList<ArrayList<Double>> previousVector = new ArrayList<>();
            ArrayList<Double> temp = new ArrayList<>();
            ArrayList<Double> temp2 = new ArrayList<>();

            //add a total sum of 1 to the current vector based on the number of directories
            //adds a number of 0 s to the previous vector based on the number of directories
            for (int i = 0; i < pageCount; i++) {
                temp.add(1d / pageCount);
                temp2.add(0d);
            }
            currentVector.add(temp);
            previousVector.add(temp2);


            //while the distance between the previous and current isn't less than 0.0001, set the current to previous and progress the current
            while (!(getEuclideanDistance(previousVector, currentVector) <= 0.0001)) {
                previousVector = currentVector;
                currentVector = multMatrix(currentVector, adjacencyMatrix);
            }


            //currentVector now the final pageRank list
            try {
                PrintWriter pageRankWriter = new PrintWriter(new FileWriter("resources" + File.separator
                        + "pageRank.txt"));
                //for every directory, link it with its corresponding pagerank in the pagerank file
                for (String currentURL : URLList) {
                    int currentPageID = pageIDList.get(URLList.indexOf(currentURL));
                    double pageRankValue = currentVector.get(0).get(currentPageID - 1);
                    pageRankWriter.print(currentURL + "\n" + pageRankValue + "\n");
                }
                pageRankWriter.close();
            } catch (IOException e) {
                System.out.println("A fatal error occurred ID 08");
                return -1;
            }
        }

        //at this point, pageRank.txt is filled
        try {
            pageRankReader = new BufferedReader(new FileReader("resources" + File.separator + "pageRank.txt"));
            //Find the corresponding pagerank for the given url
            for (String currentURL = pageRankReader.readLine(); currentURL != null; currentURL = pageRankReader.readLine()) {
                if (currentURL.equals(URL)) {
                    return Double.parseDouble(pageRankReader.readLine());
                }
            }
            pageRankReader.close();
        } catch (IOException e) {
            System.out.println("A fatal error occurred ID 09");
            return -1;
        }

        //return -1 if the url was not found
        return -1;
    }

    public ArrayList<SearchResultObject> getShownSearchResults() {
        return shownSearchResults;
    }

    public void setBoost(boolean b) {
        boost = b;
    }

    public void setShownSearchResultCount(int x) {
        shownSearchResultCount = x;
    }
}
