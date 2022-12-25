package education.myprojects.model;

import java.io.Serializable;

public class ChuDe implements Serializable {
    private int id;
    private String ten;
    private String mota;

    public ChuDe() {
    }
    public ChuDe(String ten, String mota) {
        this.ten = ten;
        this.mota = mota;
    }

    public ChuDe(int id, String ten, String mota) {
        this.id = id;
        this.ten = ten;
        this.mota = mota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    @Override
    public String toString() {
        return this.ten ;
    }
}
