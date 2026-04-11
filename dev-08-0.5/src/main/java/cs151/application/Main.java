package cs151.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        loadScene("/fxml/home-view.fxml", "DeckMaster");
    }

    public static void loadScene(String fxmlPath, String title) throws IOException {
        System.out.println(Main.class.getResource("/fxml/home-view.fxml"));

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load(), 920, 620);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}