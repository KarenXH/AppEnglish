package education.myprojects.model.API;

import java.util.ArrayList;

public class SearchTuVung{
    ArrayList<TuVung> tuVung;

    public SearchTuVung(ArrayList<TuVung> tuVung) {
        this.tuVung = tuVung;
    }

    public ArrayList<TuVung> getTuVung() {
        return tuVung;
    }

    public void setTuVung(ArrayList<TuVung> tuVung) {
        this.tuVung = tuVung;
    }
}
