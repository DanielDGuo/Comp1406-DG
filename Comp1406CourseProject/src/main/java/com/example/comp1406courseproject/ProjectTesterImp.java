package com.example.comp1406courseproject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectTesterImp implements ProjectTester {
    Crawler crawler;
    SearchModel search;

    @Override
    public void initialize() {
        crawler = new Crawler();
        search = new SearchModel();
        Crawler.reset();
    }

    @Override
    public void crawl(String seedURL) {
        crawler.crawl(seedURL);
    }

    @Override
    public List<String> getOutgoingLinks(String url) {
        try {
            BufferedReader URLToPageIDReader = new BufferedReader(new FileReader("resources"
                    + File.separator + "URLToID.txt"));
            int pageID = -1;
            for (String line = URLToPageIDReader.readLine(); line != null; line = URLToPageIDReader.readLine()) {
                if (line.equals(url)) {
                    pageID = Integer.parseInt(URLToPageIDReader.readLine());
                    break;
                }
            }
            //returns null if page isn't found
            if (pageID < 0) {
                return null;
            } else {
                BufferedReader outgoingLinksReader = new BufferedReader(new FileReader("resources"
                        + File.separator + pageID + File.separator + "outgoingLinks.txt"));
                return outgoingLinksReader.lines().toList();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getIncomingLinks(String url) {
        try {
            BufferedReader URLToPageIDReader = new BufferedReader(new FileReader("resources" + File.separator + "URLToID.txt"));
            int pageID = -1;
            for (String line = URLToPageIDReader.readLine(); line != null; line = URLToPageIDReader.readLine()) {
                if (line.equals(url)) {
                    pageID = Integer.parseInt(URLToPageIDReader.readLine());
                    break;
                }
            }
            //returns null if page isn't found
            if (pageID < 0) {
                return null;
            } else {
                BufferedReader incomingLinksReader = new BufferedReader(new FileReader("resources"
                        + File.separator + pageID + File.separator + "incomingLinks.txt"));
                return incomingLinksReader.lines().toList();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public double getPageRank(String url) {
        return search.getPageRank(url);
    }

    @Override
    public double getIDF(String word) {
        return search.getIDF(word);
    }

    @Override
    public double getTF(String url, String word) {
        String[] pageContents;
        try {
            BufferedReader URLToPageIDReader = new BufferedReader(new FileReader("resources" + File.separator + "URLToID.txt"));
            int pageID = -1;
            for (String line = URLToPageIDReader.readLine(); line != null; line = URLToPageIDReader.readLine()) {
                if (line.equals(url)) {
                    pageID = Integer.parseInt(URLToPageIDReader.readLine());
                    break;
                }
            }
            //returns null if page isn't found
            if (pageID < 0) {
                return 0d;
            } else {
                pageContents = search.getWordList(pageID);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0d;
        } catch (IOException e) {
            e.printStackTrace();
            return 0d;
        }
        return search.getTF(word, pageContents);
    }

    @Override
    public double getTFIDF(String url, String word) {
        String[] pageContents;
        try {
            BufferedReader URLToPageIDReader = new BufferedReader(new FileReader("resources" + File.separator + "URLToID.txt"));
            int pageID = -1;
            for (String line = URLToPageIDReader.readLine(); line != null; line = URLToPageIDReader.readLine()) {
                if (line.equals(url)) {
                    pageID = Integer.parseInt(URLToPageIDReader.readLine());
                    break;
                }
            }
            //returns null if page isn't found
            if (pageID < 0) {
                return 0d;
            } else {
                pageContents = search.getWordList(pageID);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0d;
        } catch (IOException e) {
            e.printStackTrace();
            return 0d;
        }
        return search.getTFIDF(word, pageContents);
    }

    @Override
    public List<SearchResult> search(String query, boolean boost, int X) {
        if (boost != search.getBoost()) {
            search.setBoost(boost);
        }
        search.setShownSearchResultCount(X);
        return new ArrayList<>(search.search(query));
    }
}
