package com.example.comp1406courseproject;

public class SearchResultObject implements SearchResult {
    //declares a title and a score
    private String title;
    private double score;

    public SearchResultObject(String t, double s) {
        //initializes the title and score
        title = t;
        score = s;
    }

    public String getTitle() {
        return title;
    }

    public double getScore() {
        return score;
    }

    //toString method
    @Override
    public String toString(){
        return title + ": " + score;
    }
}
