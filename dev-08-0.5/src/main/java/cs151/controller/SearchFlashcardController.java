package cs151.controller;

import cs151.application.Main;
import cs151.model.Flashcard;
import cs151.repository.FlashcardRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

public class SearchFlashcardController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Flashcard> tableView;

    @FXML
    private TableColumn<Flashcard, String> deckColumn;

    @FXML
    private TableColumn<Flashcard, String> frontColumn;

    @FXML
    private TableColumn<Flashcard, String> backColumn;

    @FXML
    private TableColumn<Flashcard, String> statusColumn;

    @FXML
    private TableColumn<Flashcard, String> createdAtColumn;

    @FXML
    private TableColumn<Flashcard, String> lastReviewAtColumn;

    private final FlashcardRepository flashcardRepository = new FlashcardRepository();

    @FXML
    public void initialize() {
        deckColumn.setCellValueFactory(new PropertyValueFactory<>("deckName"));
        frontColumn.setCellValueFactory(new PropertyValueFactory<>("frontText"));
        backColumn.setCellValueFactory(new PropertyValueFactory<>("backText"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAtDisplay"));
        lastReviewAtColumn.setCellValueFactory(new PropertyValueFactory<>("lastReviewAtDisplay"));

        loadAllFlashcards();
    }

    private void loadAllFlashcards() {
        List<Flashcard> flashcards = flashcardRepository.loadAll();
        ObservableList<Flashcard> data = FXCollections.observableArrayList(flashcards);
        tableView.setItems(data);
    }

    @FXML
    public void handleSearch(ActionEvent event) {
        String query = searchField.getText().trim().toLowerCase();
        List<Flashcard> allFlashcards = flashcardRepository.loadAll();

        List<Flashcard> filteredFlashcards;

        if (query.isEmpty()) {
            filteredFlashcards = allFlashcards;
        } else {
            filteredFlashcards = allFlashcards.stream()
                    .filter(flashcard ->
                            safeContains(flashcard.getDeckName(), query) ||
                                    safeContains(flashcard.getFrontText(), query) ||
                                    safeContains(flashcard.getBackText(), query) ||
                                    safeContains(flashcard.getStatus(), query)
                    )
                    .collect(Collectors.toList());
        }

        tableView.setItems(FXCollections.observableArrayList(filteredFlashcards));
    }

    private boolean safeContains(String value, String query) {
        return value != null && value.toLowerCase().contains(query);
    }

    @FXML
    public void handleDelete(ActionEvent event) {
        Flashcard selectedFlashcard = tableView.getSelectionModel().getSelectedItem();

        if (selectedFlashcard == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a flashcard to delete.");
            alert.showAndWait();
            return;
        }

        List<Flashcard> allFlashcards = flashcardRepository.loadAll();

        allFlashcards.removeIf(flashcard ->
                Objects.equals(flashcard.getDeckName(), selectedFlashcard.getDeckName()) &&
                        Objects.equals(flashcard.getFrontText(), selectedFlashcard.getFrontText()) &&
                        Objects.equals(flashcard.getBackText(), selectedFlashcard.getBackText()) &&
                        Objects.equals(flashcard.getStatus(), selectedFlashcard.getStatus()) &&
                        flashcard.getCreatedAt() == selectedFlashcard.getCreatedAt() &&
                        flashcard.getLastReviewAt() == selectedFlashcard.getLastReviewAt()
        );

        flashcardRepository.saveAll(allFlashcards);

        handleSearch(null);
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Main.loadScene("/fxml/home-view.fxml", "Home");
    }
}