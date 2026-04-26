package cs151.controller;

import cs151.application.Main;
import cs151.model.Deck;
import cs151.repository.DeckRepository;
import cs151.repository.FlashcardRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ListDecksController {

    @FXML
    private TableView<Deck> tableView;

    @FXML
    private TableColumn<Deck, String> nameColumn;

    @FXML
    private TableColumn<Deck, String> descriptionColumn;

    private final DeckRepository deckRepository = new DeckRepository();
    private final FlashcardRepository flashcardRepository = new FlashcardRepository();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tableView.setRowFactory(tv -> {
            TableRow<Deck> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Deck selectedDeck = row.getItem();

                    // do this after
                    // open edit deck page here and pass selectedDeck
                    // Example:
                    // Main.loadScene("/fxml/edit-deck-view.fxml", "Edit Deck");
                }
            });
            return row;
        });

        refreshTable();
    }

    private void refreshTable() {
        List<Deck> decks = deckRepository.loadAll();

        // Sort A-Z case-insensitive
        decks.sort(Comparator.comparing(d -> d.getName().toLowerCase()));

        ObservableList<Deck> data = FXCollections.observableArrayList(decks);
        tableView.setItems(data);
    }

    @FXML
    private void handleDeleteDeck() {
        Deck selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Please select a deck to delete.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Deck");
        alert.setHeaderText("Delete selected deck?");
        alert.setContentText("All flashcards linked to this deck will also be deleted.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteDeckAndLinkedFlashcards(selected);
            refreshTable();
        }
    }

    private void deleteDeckAndLinkedFlashcards(Deck deck) {
        // Adjust these method names if your repositories use different names.
        // The important rule is:
        // 1. delete flashcards linked to the deck
        // 2. delete the deck
        flashcardRepository.deleteByDeckName(deck.getName());
        deckRepository.deleteByName(deck.getName());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Main.loadScene("/fxml/home-view.fxml", "DeckMaster");
    }
}