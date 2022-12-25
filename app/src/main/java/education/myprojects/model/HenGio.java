package education.myprojects.model;

import java.io.Serializable;

public class HenGio implements Serializable {
    private int id;
    private String thoiGian;
    private boolean coHieuLuc;

    public HenGio() {
    }
    public HenGio( String thoiGian, boolean coHieuLuc) {
        this.thoiGian = thoiGian;
        this.coHieuLuc = coHieuLuc;
    }

    public HenGio(int id, String thoiGian, boolean coHieuLuc) {
        this.id = id;
        this.thoiGian = thoiGian;
        this.coHieuLuc = coHieuLuc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public boolean isCoHieuLuc() {
        return coHieuLuc;
    }

    public void setCoHieuLuc(boolean coHieuLuc) {
        this.coHieuLuc = coHieuLuc;
    }

    @Override
    public String toString() {
        return this.thoiGian;
    }
}
