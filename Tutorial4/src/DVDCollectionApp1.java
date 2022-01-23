import javafx.collections.FXCollections;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.input.*;



public class DVDCollectionApp1 extends Application {
    private DVDCollection model;

    public DVDCollectionApp1() {
        model = DVDCollection.example1();
    }

    public void start(Stage primaryStage) {
        Pane aPane = new Pane();

        // Create the view
        DVDCollectionAppView1 view = new DVDCollectionAppView1();
        aPane.getChildren().add(view);

        primaryStage.setTitle("My DVD Collection");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();
        view.update(model, 0);

        //prompts the user to add a DVD
        view.getButtonPane().getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                String title = javax.swing.JOptionPane.showInputDialog("Please enter the title of the DVD: ");
                int year = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Please enter the year the DVD was produced: "));
                int length = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Please enter the duration of the DVD in minutes: "));
                model.add(new DVD(title, year, length));
                view.update(model, 0);
            }
        });
        //removes the selected DVD
        view.getButtonPane().getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.remove(view.getTitleList().getSelectionModel().getSelectedItem());
                view.update(model, 0);
            }
        });

        view.getTitleList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update(model, view.getTitleList().getSelectionModel().getSelectedIndex());
            }
        });

        view.getYearList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update(model, view.getYearList().getSelectionModel().getSelectedIndex());
            }
        });

        view.getLengthList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update(model, view.getLengthList().getSelectionModel().getSelectedIndex());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}