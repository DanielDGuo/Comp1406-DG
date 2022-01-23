module com.example.comp1406courseproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.comp1406courseproject to javafx.fxml;
    exports com.example.comp1406courseproject;
}