package education.nhom11.model.API;

import java.util.ArrayList;

public class Meanings {
    private String partOfSpeech;
    private ArrayList<Definitions> definitions;

    public Meanings(String partOfSpeech, ArrayList<Definitions> definitions) {
        this.partOfSpeech = partOfSpeech;
        this.definitions = definitions;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public ArrayList<Definitions> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<Definitions> definitions) {
        this.definitions = definitions;
    }
}
