package com.example.comp1406courseproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SearchController extends Application {
    //initializes a seedURL, model, and view
    private final String seedURL = "http://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html";
    private final Boolean crawl = true;
    private SearchModel model;
    private SearchView view;

    @Override
    public void start(Stage primaryStage) throws Exception {
        model = new SearchModel();
        view = new SearchView(model);

        //when the boost is clicked, set the boost and update
        view.getBoost().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.setBoost(view.getBoost().isSelected());
                view.update();
            }
        });

        //when a string is typed into the text field, update
        view.getSearchField().setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent actionEvent) {
                view.update();
            }
        });

        //when the search button is clicked, search the text in the text field and update
        view.getSearchButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.search(view.getSearchField().getText());
                view.update();
            }
        });
        //displays the current seedURL as the title, and disables the resizing of the window
        primaryStage.setTitle(seedURL + " search");
        primaryStage.setScene(new Scene(view));
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.show();

    }

    //crawl the seedURL beforehand
    public void init() throws Exception {
        if (crawl) {
            Crawler crawler = new Crawler();
            crawler.crawl(seedURL);
        }
    }
}
