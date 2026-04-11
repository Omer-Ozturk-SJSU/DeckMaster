module cs151.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens cs151.controller to javafx.fxml;
    opens cs151.application to javafx.graphics;
    opens cs151.model to javafx.base, com.google.gson;

    exports cs151.application;
}