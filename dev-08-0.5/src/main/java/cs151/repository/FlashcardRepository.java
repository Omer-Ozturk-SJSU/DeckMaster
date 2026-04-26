package cs151.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cs151.model.Flashcard;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FlashcardRepository {
    private static final String FILE_NAME = "flashcards.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Flashcard> loadAll() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<List<Flashcard>>() {}.getType();
            List<Flashcard> flashcards = gson.fromJson(reader, listType);
            return flashcards != null ? flashcards : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void saveAll(List<Flashcard> flashcards) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(flashcards, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(Flashcard flashcard) {
        List<Flashcard> flashcards = loadAll();
        flashcards.add(flashcard);
        saveAll(flashcards);
    }

    public void deleteByDeckName(String deckName) {
        List<Flashcard> flashcards = loadAll();

        flashcards.removeIf(card ->
                card.getDeckName().equalsIgnoreCase(deckName)
        );

        saveAll(flashcards);
    }

    public boolean existsInDeck(String deckName, String frontText) {
        List<Flashcard> flashcards = loadAll();

        for (Flashcard card : flashcards) {
            if (card.getDeckName().equalsIgnoreCase(deckName)
                    && card.getFrontText().trim().equalsIgnoreCase(frontText.trim())) {
                return true;
            }
        }
        return false;
    }

    public void updateDeckName(String oldDeckName, String newDeckName) {
        List<Flashcard> flashcards = loadAll();

        for (Flashcard card : flashcards) {
            if (card.getDeckName() != null &&
                    card.getDeckName().trim().equalsIgnoreCase(oldDeckName.trim())) {
                card.setDeckName(newDeckName);
            }
        }

        saveAll(flashcards);
    }
}