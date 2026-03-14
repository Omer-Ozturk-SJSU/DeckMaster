module cs151.application {
    requires javafx.controls;
    requires javafx.fxml;

    opens cs151.controller to javafx.fxml;
    opens cs151.application to javafx.graphics;
    opens cs151.model to javafx.base;

    exports cs151.application;
}
