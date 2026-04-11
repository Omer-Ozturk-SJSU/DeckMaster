package cs151.controller;

import cs151.application.Main;
import cs151.model.Deck;
import cs151.repository.DeckRepository;
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

public class ListDecksController {

    @FXML
    private TableView<Deck> tableView;

    @FXML
    private TableColumn<Deck, String> nameColumn;

    @FXML
    private TableColumn<Deck, String> descriptionColumn;

    private DeckRepository repository = new DeckRepository();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        List<Deck> decks = repository.loadAll();

        // SORT A-Z CASE INSENSITIVE (IMPORTANT)
        decks.sort(Comparator.comparing(d -> d.getName().toLowerCase()));

        ObservableList<Deck> data = FXCollections.observableArrayList(decks);
        tableView.setItems(data);
    }
    public void handleBack(ActionEvent event) throws IOException {
        Main.loadScene("/fxml/home-view.fxml", "DeckMaster");
    }
}