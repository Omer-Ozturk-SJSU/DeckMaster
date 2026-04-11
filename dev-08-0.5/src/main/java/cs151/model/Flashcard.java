package cs151.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Flashcard {
    private String deckName;
    private String frontText;
    private String backText;
    private long createdAt;
    private String status;
    private long lastReviewAt;

    public Flashcard() {
        // Required for Gson
    }

    public Flashcard(String deckName, String frontText, String backText,
                     long createdAt, String status, long lastReviewAt) {
        this.deckName = deckName;
        this.frontText = frontText;
        this.backText = backText;
        this.createdAt = createdAt;
        this.status = status;
        this.lastReviewAt = lastReviewAt;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getFrontText() {
        return frontText;
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getLastReviewAt() {
        return lastReviewAt;
    }

    public void setLastReviewAt(long lastReviewAt) {
        this.lastReviewAt = lastReviewAt;
    }

    public String getCreatedAtDisplay() {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date(createdAt));
    }

    public String getLastReviewAtDisplay() {
        if (lastReviewAt == 0) {
            return "Never";
        }
        return new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date(lastReviewAt));
    }
}