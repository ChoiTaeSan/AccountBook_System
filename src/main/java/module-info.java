module com.example.java_fx_practice {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.java_fx_practice to javafx.fxml;
    exports com.example.java_fx_practice;
}