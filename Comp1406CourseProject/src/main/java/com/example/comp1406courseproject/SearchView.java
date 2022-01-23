package com.example.comp1406courseproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SearchView extends Pane {
    //declares all vie elements
    private Button searchButton;
    private CheckBox boost;
    private TextField searchField;
    private ListView<SearchResultObject> searchResults;
    private SearchModel model;

    public SearchView(SearchModel m) {
        model = m;

        //initializes and positions search button. Makes it the default button
        searchButton = new Button("Search");
        searchButton.setAlignment(Pos.CENTER);
        searchButton.setDisable(true);
        searchButton.relocate(430, 10);
        searchButton.setPrefSize(80, 10);
        searchButton.setDefaultButton(true);

        //initializes and positions boost box
        boost = new CheckBox("Boost?");
        boost.relocate(525, 13);
        boost.setPrefSize(60, 10);

        //initializes and positions search field
        searchField = new TextField();
        searchField.relocate(15, 10);
        searchField.setPrefSize(410, 10);

        //initializes and positions search result list
        searchResults = new ListView<>();
        searchResults.relocate(15, 40);
        searchResults.setPrefSize(570, 250);
        searchResults.setItems(FXCollections.observableArrayList(model.getShownSearchResults()));

        //adds all elements to the view
        getChildren().addAll(searchButton, boost, searchField, searchResults);
        setPrefSize(600, 300);
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public CheckBox getBoost() {
        return boost;
    }

    //updates the view
    public void update() {
        //updates the list elements
        ObservableList<SearchResultObject> list = FXCollections.observableArrayList(model.getShownSearchResults());
        searchResults.setItems(list);

        //enables/disables the search button
        searchButton.setDisable(searchField.getText().length() == 0);
    }
}
