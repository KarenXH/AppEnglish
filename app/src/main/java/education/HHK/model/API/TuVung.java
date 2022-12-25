package education.HHK.model.API;

import java.io.Serializable;
import java.util.ArrayList;

public class TuVung implements Serializable{
    private String word;
    private ArrayList<Phonetics> phonetics;
    private ArrayList<Meanings> meanings;

    public TuVung(String word, ArrayList<Phonetics> phonetics, ArrayList<Meanings> meanings) {
        this.word = word;
        this.phonetics = phonetics;
        this.meanings = meanings;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<Phonetics> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(ArrayList<Phonetics> phonetics) {
        this.phonetics = phonetics;
    }

    public ArrayList<Meanings> getMeanings() {
        return meanings;
    }

    public void setMeanings(ArrayList<Meanings> meanings) {
        this.meanings = meanings;
    }

}
