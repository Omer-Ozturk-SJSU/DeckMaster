package cs151.controller;

import cs151.application.Main;
import cs151.model.Flashcard;
import cs151.repository.FlashcardRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ListFlashcardsController {

    private final FlashcardRepository repository = new FlashcardRepository();

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

    @FXML
    public void initialize() {
        deckColumn.setCellValueFactory(new PropertyValueFactory<>("deckName"));
        frontColumn.setCellValueFactory(new PropertyValueFactory<>("frontText"));
        backColumn.setCellValueFactory(new PropertyValueFactory<>("backText"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAtDisplay"));
        lastReviewAtColumn.setCellValueFactory(new PropertyValueFactory<>("lastReviewAtDisplay"));

        List<Flashcard> flashcards = repository.loadAll();
        flashcards.sort(Comparator.comparingLong(Flashcard::getCreatedAt).reversed());

        ObservableList<Flashcard> data = FXCollections.observableArrayList(flashcards);
        tableView.setItems(data);

        tableView.setOnMouseClicked(event -> {
            Flashcard selected = tableView.getSelectionModel().getSelectedItem();

            if (selected != null) {
                selected.setLastReviewAt(System.currentTimeMillis());

                repository.saveAll(tableView.getItems());

                tableView.refresh();
            }
        });
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Main.loadScene("/fxml/home-view.fxml", "Home");
    }
}