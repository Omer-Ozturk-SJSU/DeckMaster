package cs151.controller;

import cs151.application.Main;
import cs151.model.Deck;
import cs151.repository.DeckRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DefineDeckController {

    private final DeckRepository rep = new DeckRepository();

    @FXML
    private TextField deckNameField;

    @FXML
    private TextArea descriptionArea;

    public void handleSave(ActionEvent event) {
        String name = deckNameField.getText() == null ? "" : deckNameField.getText().trim();
        String description = descriptionArea.getText() == null ? "" : descriptionArea.getText().trim();

        if (rep.loadAll().size() >= 3) {
            showAlert("Limit Reached", "Only 3 decks are allowed.");
            return;
        }

        if (name.isEmpty()) {
            showAlert("Validation Error", "Deck name is required.");
            return;
        }

        if (rep.existsByName(name)) {
            showAlert("Validation Error", "Deck name must be unique.");
            return;
        }

        Deck deck = new Deck(name, description);
        rep.addDeck(deck);

        showAlert("Success", "Deck saved successfully.");

        deckNameField.clear();
        descriptionArea.clear();
    }

    public void handleBack(ActionEvent event) throws IOException {
        Main.loadScene("/fxml/home-view.fxml", "DeckMaster");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}