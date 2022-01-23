package com.example.comp1406courseproject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Crawler {
    //represents a Page:URL map
    private HashMap<String, Page> knownLinks;
    //represents a word:count map
    private HashMap<String, Integer> uniqueWordCount;

    public Crawler() {
        //initializes the variables
        knownLinks = new HashMap<>();
        uniqueWordCount = new HashMap<>();
    }

    //creates all files needed to perform searches
    public void crawl(String startingPageLink) {
        //resets any previous crawl
        reset();
        //makes a queue of URLs to parse
        ArrayList<String> toBeParsed = new ArrayList<>();
        //adds the starting page to the parse list
        toBeParsed.add(startingPageLink);
        //adds the starting link to the known link
        knownLinks.put(startingPageLink, new Page());

        //loops through all pages that are to be parsed
        while (toBeParsed.size() > 0) {
            //keeps track of the unique words in the page
            ArrayList<String> uniqueWordList = new ArrayList<>();
            //sets the currentURL to the first URL in queue
            String currentURL = toBeParsed.remove(0);
            //updates the hashmap
            knownLinks.replace(currentURL, parse(currentURL));

            //for every outgoing link, add it to known links and the queue
            for (String outgoingLink : knownLinks.get(currentURL).getOutgoingLinks()) {
                if (!knownLinks.containsKey(outgoingLink)) {
                    toBeParsed.add(outgoingLink);
                    knownLinks.put(outgoingLink, new Page(currentURL));
                } else {
                    Page updatedPage = knownLinks.get(outgoingLink);
                    updatedPage.addIncomingLink(currentURL);
                    knownLinks.replace(outgoingLink, updatedPage);
                }
            }

            for (String word : knownLinks.get(currentURL).getPageWords()) {
                if (!uniqueWordList.contains(word)) {
                    uniqueWordList.add(word);
                    if (!uniqueWordCount.containsKey(word)) {
                        uniqueWordCount.put(word, 1);
                    } else {
                        uniqueWordCount.replace(word, uniqueWordCount.get(word) + 1);
                    }
                }
            }
        }
        makeFiles();
    }

    //creates a page object with a URL as a parameter. Preserves incoming links.
    public Page parse(String currentURL) {
        //object to store the page info
        Page parsedPage;
        //makes an object to read HTML
        WebRequester contentReader = new WebRequester();

        try {
            //if the URL has only incoming links saved, parse the rest
            String currentContent = contentReader.readURL(currentURL);

            parsedPage = new Page(parseTitle(currentContent), parseWords(currentContent),
                    parseLinks(currentContent, currentURL), knownLinks.get(currentURL).getIncomingLinks());

        } catch (IOException e) {
            System.out.println("Bad URL");
            return null;
        }
        return parsedPage;
    }

    //with parameters of the current URL's contents in string form and the current url, returns the absolute outgoing links
    public ArrayList<String> parseLinks(String currentPageLinkContents, String currentURL) {
        ArrayList<String> outgoingLinks = new ArrayList<>();
        //deletes everything before the first href
        currentPageLinkContents = currentPageLinkContents.substring(currentPageLinkContents.indexOf("href="));
        //while there's a link, get the link
        while (currentPageLinkContents.contains("href=")) {
            currentPageLinkContents = currentPageLinkContents.substring(currentPageLinkContents.indexOf("href="));
            String OutgoingLink = currentPageLinkContents.substring(currentPageLinkContents.indexOf("\"") + 1,
                    currentPageLinkContents.indexOf("\">"));
            if (OutgoingLink.startsWith(".")) {
                outgoingLinks.add(currentURL.substring(0, currentURL.lastIndexOf("/")) + OutgoingLink.substring(1));
            } else {
                outgoingLinks.add(OutgoingLink);
            }
            //removes the href from the contents
            currentPageLinkContents = currentPageLinkContents.substring(currentPageLinkContents.indexOf("</a>") + 4);
        }
        return outgoingLinks;
    }

    //parses a list of words
    public ArrayList<String> parseWords(String currentPageLinkContents) {
        ArrayList<String> words = new ArrayList<>();

        //as long as it contains a paragraph tag, get the contents and add it to the list
        while (currentPageLinkContents.contains("<p>")) {
            words.addAll(Arrays.stream(currentPageLinkContents.substring(currentPageLinkContents.indexOf("<p>") + 3,
                    currentPageLinkContents.indexOf("</p>")).strip().split("\\s+")).toList());
            currentPageLinkContents = currentPageLinkContents.substring(currentPageLinkContents.indexOf("</p>") + 4);
        }
        return words;
    }

    //parses the title
    public String parseTitle(String currentPageLinkContents) {
        //finds the content between title tags
        return currentPageLinkContents.substring(currentPageLinkContents.indexOf("<title>") + 7,
                currentPageLinkContents.indexOf("</title>")).strip();
    }

    //for every word, write it in a line, then the number of pages that word appears in the subsequent line
    public void makePagesWithWordsFile() {
        try {
            PrintWriter PagesWithWords = new PrintWriter(new FileWriter("resources" + File.separator + "PagesWithWords.txt"));
            for (String word : uniqueWordCount.keySet()) {
                PagesWithWords.print(word + "\n" + uniqueWordCount.get(word) + "\n");
            }
            PagesWithWords.close();
        } catch (IOException e) {
            System.out.println("Fatal Error occurred. ID 01");
        }

    }

    //for every URL, write it in a line, then its pageID in the subsequent line
    public void makeURLToIDFile() {
        try {
            PrintWriter URLToID = new PrintWriter(new FileWriter("resources" + File.separator + "URLToID.txt"));
            for (String URL : knownLinks.keySet()) {
                URLToID.print(URL + "\n" + knownLinks.get(URL).getPageID() + "\n");
            }
            URLToID.close();
        } catch (IOException e) {
            System.out.println("Fatal Error occurred. ID 02");
        }
    }

    //makes an empty pageRank files
    public void makeEmptyPageRankFile() {
        try {
            PrintWriter URLToID = new PrintWriter(new FileWriter("resources" + File.separator + "pageRank.txt"));
            URLToID.close();
        } catch (IOException e) {
            System.out.println("Fatal Error occurred. ID 03");
        }
    }

    //resets all variables, information
    public static void reset() {
        Page.resetPageIDCount();
        deleteDir(new File("resources"));
    }

    //make a resources directory to store all information
    public void makeFiles() {
        File directory = new File("resources");
        directory.mkdir();
        for (Page p : knownLinks.values()) {
            //sets an id to the page and makes the page files
            p.identify();
            p.makeFiles();
        }
        //makes other required files
        makePagesWithWordsFile();
        makeURLToIDFile();
        makeEmptyPageRankFile();
    }

    //deletes all files in the given directory
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            for (File subDir : dir.listFiles()) {
                deleteDir(subDir);
            }
        }
        dir.delete();
    }
}