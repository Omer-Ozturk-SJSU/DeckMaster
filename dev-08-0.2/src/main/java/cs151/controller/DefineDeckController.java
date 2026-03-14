package cs151.controller;

import cs151.application.Main;
import cs151.model.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DefineDeckController {

    @FXML
    private TextField deckNameField;

    @FXML
    private TextArea descriptionArea;

    public void handleSave(ActionEvent event) {
        String name = deckNameField.getText();
        String description = descriptionArea.getText();

        Deck deck = new Deck(name, description);

        System.out.println("Deck created:");
        System.out.println("Name: " + deck.getName());
        System.out.println("Description: " + deck.getDescription());
    }

    public void handleBack(ActionEvent event) throws IOException {
        Main.loadScene("/fxml/home-view.fxml", "DeckMaster");
    }
}