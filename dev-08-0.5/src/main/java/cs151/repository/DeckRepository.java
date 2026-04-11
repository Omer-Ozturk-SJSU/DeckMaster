package cs151.repository;

import cs151.model.Deck;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DeckRepository {

    private static final String FILE_PATH = "decks.json";
    private final Gson gson = new Gson();

    public List<Deck> loadAll() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Deck>>() {}.getType();
            List<Deck> decks = gson.fromJson(reader, listType);
            return decks != null ? decks : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void saveAll(List<Deck> decks) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(decks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existsByName(String inputName) {
        String trimmedInput = inputName.trim();

        for (Deck deck : loadAll()) {
            if (deck.getName() != null &&
                    deck.getName().trim().equalsIgnoreCase(trimmedInput)) {
                return true;
            }
        }
        return false;
    }

    public boolean addDeck(Deck deck) {
        List<Deck> decks = loadAll();
        decks.add(deck);
        saveAll(decks);
        return true;
    }
}