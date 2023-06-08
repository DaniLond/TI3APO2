module com.example.titresapo {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.example.titresapo to javafx.fxml;
    exports com.example.titresapo;
}