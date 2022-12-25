package education.HHK.data;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import education.HHK.model.ChuDe;
import education.HHK.model.TuVung;

public class DBTuVung extends SQLiteOpenHelper{

    private static int version = 1;
    private static final String DATABASE_NAME ="dbtuvung";
    private static final String TABLE_TU_VUNG ="tuvung";
    private static final String TuVung_ID ="id";
    private static final String TuVung_tuvung ="tuvung";
    private static final String TuVung_nghia ="nghia";
    private static final String TuVung_phatam ="phatam";
    private static final String TuVung_loaitu ="loaitu";

    private static final String TABLE_CHU_DE ="chude";
    private static final String ChuDe_ID ="id";
    private static final String ChuDe_TEN ="ten";
    private static final String ChuDe_MOTA ="mota";

    private static final String TABLE_TUVUNG_CHUDE ="tuvungchude";
    private static final String TuVungChuDe_TuVung ="tuvung_id";
    private static final String TuVungChuDe_ChuDe ="chude_id";
    private static SQLiteDatabase db = null;

    private Activity context;
    public DBTuVung(Activity context) {
        super(context, DATABASE_NAME, null, version);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    //Add new a TuVung
    public void addTuVung(TuVung tuVung){
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put(TuVung_tuvung, tuVung.getTuvung());
        values.put(TuVung_nghia, tuVung.getNghia());
        values.put(TuVung_phatam, tuVung.getPhatam());
        values.put(TuVung_loaitu, tuVung.getLoaitu());
        db.insert(TABLE_TU_VUNG,null,values);
        db.close();
    }

    //get last a TuVung
    public int getLastTuVung(){
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery(
                "select * from tuvung order by id desc limit 1",null);
        TuVung tuVung = null;
        while (cursor.moveToNext())
        {
            tuVung = new TuVung(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
            );
        }
        cursor.close();
        db.close();
        return tuVung.getId();
    }

    //Add new a ChuDe
    public void addChuDe(ChuDe chuDe){
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put(ChuDe_TEN, chuDe.getTen());
        values.put(ChuDe_MOTA, chuDe.getMota() );
        db.insert(TABLE_CHU_DE,null,values);
        db.close();
        Toast.makeText(context, "add ChuDe Success!!", Toast.LENGTH_LONG).show();
    }

    //Add new a TuVungChuDe
    public void addTuVungChuDe(int tuvung_id, int chude_id){
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put(TuVungChuDe_TuVung, tuvung_id);
        values.put(TuVungChuDe_ChuDe, chude_id);
        db.insert(TABLE_TUVUNG_CHUDE,null,values);
        db.close();
    }

    //Get TuVung by Id
    public TuVung getTuVungById(int id){
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = db.query(TABLE_TU_VUNG, null, TuVung_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        TuVung tuVung = null;
        while (cursor.moveToNext())
        {
             tuVung = new TuVung(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
             );
        }
        cursor.close();
        db.close();
        return tuVung;
    }

    //Get ChuDe by Id
    public ChuDe getChuDeById(int id){
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = db.query(TABLE_CHU_DE, null, ChuDe_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        ChuDe chuDe = null;
        while (cursor.moveToNext()) // moveToNext() -> return boolean: là hàm đưa con trỏ tới dòng tiếp theo
        {
            chuDe = new ChuDe(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
            );
        }
        cursor.close();
        db.close();
        return chuDe;
    }

    //Get all ChuDe
    public ArrayList<ChuDe> getAllChuDe(){
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = db.query(TABLE_CHU_DE, null, null,
                null, null, null, null);
        ArrayList<ChuDe> dschuDe = new ArrayList<ChuDe>();
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                ChuDe chuDe = new ChuDe(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
                );
                dschuDe.add(chuDe);
            }
        }
        cursor.close();
        db.close();
        return dschuDe;
    }

    //Get all ChuDe
    public ArrayList<TuVung> getAllTuVung(){
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = db.query(TABLE_TU_VUNG, null, null,
                null, null, null, null, null);
        ArrayList<TuVung> dsTuVung = new ArrayList<TuVung>();
        while (cursor.moveToNext())
        {
            TuVung tuVung = new TuVung(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            dsTuVung.add(tuVung);
        }
        cursor.close();
        db.close();
        return dsTuVung;
    }

    //Get list TuVung by ChuDe
    public ArrayList<TuVung> getListTuVungByChuDe(int chude_id){
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery(
                "SELECT * FROM tuvung where id in (select tuvung_id from tuvungchude where chude_id = " + chude_id +" ) ",null);
        ArrayList<TuVung> dsTuVung = new ArrayList<>();
        while (cursor.moveToNext())
        {
            TuVung tuVung = new TuVung(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
            );
            dsTuVung.add(tuVung);
        }
        cursor.close();
        db.close();
        return dsTuVung;
    }

    //Delete TuVung
    public void deleteTuVung(TuVung tuVung) {
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        db.delete(TABLE_TU_VUNG, TuVung_ID + " = ?",
                new String[] { String.valueOf(tuVung.getId()) });
        db.close();
    }

    //Delete ChuDe
    public void deleteChuDe(ChuDe chuDe) {
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        int rec = db.delete(TABLE_CHU_DE, "id =?", new String[] {String.valueOf(chuDe.getId())});
        {
            Toast.makeText(context, "Updata thanh cong!!", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    //Delete TuVungChuDe by tuvung_id
    public void deleteTuVungChuDe(int tuvung_id) {
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        int rec = db.delete(TABLE_TUVUNG_CHUDE, TuVungChuDe_TuVung +" =?",
                new String[] {String.valueOf(tuvung_id)});
        {
            Toast.makeText(context, "Xóa từ vựng thành công!!", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    //Update TuVung
    public void UpdateTuVung(TuVung tuVung){
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put(TuVung_tuvung,tuVung.getTuvung());
        values.put(TuVung_nghia,tuVung.getNghia());
        values.put(TuVung_phatam,tuVung.getPhatam());
        values.put(TuVung_loaitu,tuVung.getLoaitu());
        int rec = db.update(TABLE_TU_VUNG,values,TuVung_ID +"=?",new String[] { String.valueOf(tuVung.getId())});
        if (rec > 0)
        {
            Toast.makeText(context, "Updata từ vựng thành công!!", Toast.LENGTH_LONG).show();
        }
        db.close();
    }
    //Update ChuDe
    public void UpdateChuDe(ChuDe chuDe){
        db = this.context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put(ChuDe_TEN, chuDe.getTen());
        values.put(ChuDe_MOTA,chuDe.getMota());
        int rec = db.update(
                TABLE_CHU_DE,
                values,"id =?",
                new String[] { String.valueOf(chuDe.getId())});
        if (rec > 0)
        {
            Toast.makeText(context, "Updata chủ đề thành công!!", Toast.LENGTH_LONG).show();
        }
        db.close();
    }
}
