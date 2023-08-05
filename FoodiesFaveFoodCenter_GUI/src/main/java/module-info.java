module com.example.foodcentersystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.foodcentersystem to javafx.fxml;
    exports com.example.foodcentersystem;
}