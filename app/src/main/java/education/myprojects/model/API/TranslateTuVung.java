package education.myprojects.model.API;

import java.util.ArrayList;

public class TranslateTuVung {

    private ArrayList<Sentences> sentences;


    public TranslateTuVung(ArrayList<Sentences> sentences) {
        this.sentences = sentences;
    }

    public ArrayList<Sentences> getSentences() {
        return sentences;
    }

    public void setSentences(ArrayList<Sentences> sentences) {
        this.sentences = sentences;
    }
}
