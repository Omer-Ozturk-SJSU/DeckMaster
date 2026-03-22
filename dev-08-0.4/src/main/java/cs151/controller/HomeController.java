package cs151.controller;

import cs151.application.Main;
import javafx.event.ActionEvent;

import java.io.IOException;

public class HomeController {

    public void handleDefineDeck(ActionEvent event) throws IOException {
        Main.loadScene("/fxml/define-deck-view.fxml", "Define Deck");
    }

    public void handleViewDecks(ActionEvent event) throws IOException {
        Main.loadScene("/fxml/list-decks-view.fxml", "Stored Decks");
    }
}