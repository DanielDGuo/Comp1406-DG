package com.example.comp1406courseproject;

import java.io.*;
import java.util.ArrayList;

public class Page {
    //declares variables
    private static int currentID = 1;
    private int pageID;
    private String pageTitle;
    private ArrayList<String> pageWords, outgoingLinks, incomingLinks;

    //full constructor
    public Page(String title, ArrayList<String> words, ArrayList<String> outLinks, ArrayList<String> inLinks) {
        this(title, words, outLinks);
        incomingLinks = inLinks;
    }

    //constructor without incoming link information
    public Page(String title, ArrayList<String> words, ArrayList<String> outLinks) {
        pageTitle = title;
        pageWords = words;
        outgoingLinks = outLinks;
        incomingLinks = new ArrayList<>();
        pageID = -1;
    }

    //constructor if linked from another page
    public Page(String inLink) {
        this();
        incomingLinks.add(inLink);
    }

    //default constructor
    public Page() {
        pageTitle = "";
        pageWords = new ArrayList<>();
        outgoingLinks = new ArrayList<>();
        incomingLinks = new ArrayList<>();
        pageID = -1;
    }

    //adds an incoming link to the page's incoming link list
    public void addIncomingLink(String inLink) {
        incomingLinks.add(inLink);
    }
    
    public ArrayList<String> getOutgoingLinks() {
        return outgoingLinks;
    }

    public ArrayList<String> getIncomingLinks() {
        return incomingLinks;
    }

    public ArrayList<String> getPageWords() {
        return pageWords;
    }

    public int getPageID() {
        return pageID;
    }
    
    //makes the files in a directory named after the pageID
    public void makeFiles() {
        File directory = new File("resources" + File.separator + pageID);
        directory.mkdir();

        makeWordFile();
        makeTitleFile();
        makeOutgoingLinksFile();
        makeIncomingLinksFile();
    }

    //makes a word file under the directory named after the page ID
    public void makeWordFile() {
        try {
            PrintWriter words = new PrintWriter(new FileWriter("resources" + File.separator + pageID
                    + File.separator + "words.txt"));
            for (String w : pageWords) {
                words.println(w);
            }
            words.close();
        } catch (FileNotFoundException e) {
            System.out.println("word file creation unsuccessful");
        } catch (IOException e) {
            System.out.println("word file writing unsuccessful");
        }
    }

    //makes a title file under the directory named after the page ID
    public void makeTitleFile() {
        try {
            PrintWriter title = new PrintWriter(new FileWriter("resources" + File.separator + pageID
                    + File.separator + "title.txt"));
            title.print(pageTitle);
            title.close();
        } catch (FileNotFoundException e) {
            System.out.println("title file creation unsuccessful");
        } catch (IOException e) {
            System.out.println("title file writing unsuccessful");
        }
    }

    //makes an outgoing links file under the directory named after the page ID
    public void makeOutgoingLinksFile() {
        try {
            PrintWriter outLinks = new PrintWriter(new FileWriter("resources" + File.separator + pageID
                    + File.separator + "outgoingLinks.txt"));
            for (String outLink : outgoingLinks) {
                outLinks.println(outLink);
            }
            outLinks.close();
        } catch (FileNotFoundException e) {
            System.out.println("outLink file creation unsuccessful");
        } catch (IOException e) {
            System.out.println("outLink file writing unsuccessful");
        }
    }

    //makes an incoming links file under the directory named after the page ID
    public void makeIncomingLinksFile() {
        try {
            PrintWriter inLinks = new PrintWriter(new FileWriter("resources" + File.separator + pageID
                    + File.separator + "incomingLinks.txt"));
            for (String inLink : incomingLinks) {
                inLinks.println(inLink);
            }
            inLinks.close();
        } catch (FileNotFoundException e) {
            System.out.println("inLink file creation unsuccessful");
        } catch (IOException e) {
            System.out.println("inLink file writing unsuccessful");
        }
    }

    //formally identifies a page with a unique ID
    public void identify() {
        pageID = currentID;
        currentID++;
    }

    //resets the pageID counter
    public static void resetPageIDCount() {
        currentID = 1;
    }

    @Override
    public String toString() {
        return " title: " + pageTitle + " words: " + pageWords + " dir: " + pageID + " out: " + getOutgoingLinks() + " in: " + getIncomingLinks();
    }

}
