package education.myprojects.model;

import java.io.Serializable;

public class TuVung implements Serializable {
    private int id;
    private String tuvung;
    private String nghia;
    private String phatam;
    private String loaitu;


    public String getLoaitu() {
        return loaitu;
    }

    public void setLoaitu(String loaitu) {
        this.loaitu = loaitu;
    }

    public TuVung() {
    }

    public TuVung(int id, String tuvung, String nghia, String phatam) {
        this.id = id;
        this.tuvung = tuvung;
        this.nghia = nghia;
        this.phatam = phatam;
    }
    public TuVung(int id, String tuvung, String nghia, String phatam, String loaitu) {
        this.id = id;
        this.tuvung = tuvung;
        this.nghia = nghia;
        this.phatam = phatam;
        this.loaitu = loaitu;
    }
    public TuVung(String tuvung, String nghia, String phatam, String loaitu) {
        this.id = id;
        this.tuvung = tuvung;
        this.nghia = nghia;
        this.phatam = phatam;
        this.loaitu = loaitu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTuvung() {
        return tuvung;
    }

    public void setTuvung(String tuvung) {
        this.tuvung = tuvung;
    }

    public String getNghia() {
        return nghia;
    }

    public void setNghia(String nghia) {
        this.nghia = nghia;
    }

    public String getPhatam() {
        return phatam;
    }

    public void setPhatam(String phatam) {
        this.phatam = phatam;
    }

    @Override
    public String toString() {
        return this.tuvung + " : " + this.nghia;
    }
}
