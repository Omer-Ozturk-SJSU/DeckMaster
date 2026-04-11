package cs151.controller;

import cs151.application.Main;
import cs151.model.Deck;
import cs151.model.Flashcard;
import cs151.repository.DeckRepository;
import cs151.repository.FlashcardRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DefineFlashcardController {

    private final DeckRepository deckRepository = new DeckRepository();
    private final FlashcardRepository flashcardRepository = new FlashcardRepository();

    @FXML
    private ComboBox<String> deckComboBox;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TextArea frontTextArea;

    @FXML
    private TextArea backTextArea;

    @FXML
    public void initialize() {
        List<Deck> decks = deckRepository.loadAll();
        List<String> deckNames = decks.stream()
                .map(Deck::getName)
                .collect(Collectors.toList());

        deckComboBox.setItems(FXCollections.observableArrayList(deckNames));

        statusComboBox.setItems(FXCollections.observableArrayList(
                "new", "learning", "mastered"
        ));
        statusComboBox.setValue("new");
    }

    @FXML
    public void handleSave(ActionEvent event) {
        String deckName = deckComboBox.getValue();
        String frontText = frontTextArea.getText().trim();
        String backText = backTextArea.getText().trim();
        String status = statusComboBox.getValue();

        if (deckName == null || frontText.isEmpty() || backText.isEmpty() || status == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        if (flashcardRepository.existsInDeck(deckName, frontText)) {
            showAlert("Error", "Within the same deck, Front Text must be unique.");
            return;
        }

        long now = System.currentTimeMillis();

        Flashcard flashcard = new Flashcard(
                deckName,
                frontText,
                backText,
                now,
                status,
                0
        );

        flashcardRepository.add(flashcard);

        showAlert("Success", "Flashcard saved successfully.");

        deckComboBox.setValue(null);
        statusComboBox.setValue("new");
        frontTextArea.clear();
        backTextArea.clear();
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Main.loadScene("/fxml/home-view.fxml", "Home");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}