module com.example.pizzariacdr {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pizzariacdr to javafx.fxml;
    exports com.example.pizzariacdr;
}