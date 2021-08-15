module com.buildpc.buildpc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.buildpc to javafx.fxml;
    exports com.buildpc;
    exports com.buildpc.controllers;
    opens com.buildpc.controllers to javafx.fxml;
}