package education.nhom11.tuvungtienganh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import education.nhom11.adapter.TuVungAdapter;
import education.nhom11.data.DBTuVung;
import education.nhom11.model.ChuDe;
import education.nhom11.model.TuVung;

public class toanbotu extends AppCompatActivity {

    private ListView lvTatCaTuVung;
    private TuVungAdapter tuVungAdapter;
    private ArrayList<TuVung> dsTuVung;
    private ImageButton btnBackDsChuDe;
    private TextView txtTenChuDe;
    private DBTuVung dbTuVung;

    private View popupInputDialogView = null;
    private EditText txtTuVung = null;
    private EditText txtNghia = null;
    private EditText txtPhatAm = null;
    private Spinner spLoaiTu = null;
    private String []arrLoaiTu;
    private ArrayAdapter<String> loaiTuAdapter;

    private ArrayList<TuVung> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toanbotu);

        dbTuVung = new DBTuVung(this);

        addControls();
        addEvents();
    }

    private void addControls() {
        lvTatCaTuVung = findViewById(R.id.lvTatCaTuVung);
        dsTuVung = new ArrayList<>();
//        tuVungAdapter = new TuVungAdapter(
//                toanbotu.this,
//                R.layout.item_tu_vung,
//                dsTuVung);
//        lvTatCaTuVung.setAdapter(tuVungAdapter);
        btnBackDsChuDe = findViewById(R.id.btnBackDsChuDe);
        txtTenChuDe = findViewById(R.id.txtTenChuDe);
    }

    private void addEvents() {
        txtTenChuDe.setText("Danh sách từ vựng");
        btnBackDsChuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyQuaylaiDanhSachChuDe();
            }
        });
        hienThiTatCaTuVung();
    }

    private void hienThiTatCaTuVung() {
        dsTuVung.clear();
        dsTuVung.addAll(dbTuVung.getAllTuVung());
        tuVungAdapter = new TuVungAdapter(
                toanbotu.this,
                R.layout.item_tu_vung,
                dsTuVung);
        lvTatCaTuVung.setAdapter(tuVungAdapter);
    }

    private void xuLyQuaylaiDanhSachChuDe() {
        Intent intent = new Intent(toanbotu.this, danhsachchude.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.timtu, menu);
        MenuItem mnuSearch = menu.findItem(R.id.mnuTimKiemTuVung);
        SearchView searchView = (SearchView) mnuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase(Locale.getDefault());
                tuVungAdapter.filter(newText.trim());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mnuThemTu)
        {
            //TODO
            xuLyThemMoiTuVung();
        }
        else if(item.getItemId() == R.id.mnuLuyenTap){
            //TODO
            xuLyLuyenTap();
        }
        else if(item.getItemId() == R.id.mnuSlide){
            //TODO
            suLyHienThiTheoSlide();
        }
        return super.onOptionsItemSelected(item);
    }

    private void xuLyLuyenTap() {
        Intent intent = new Intent(toanbotu.this, LuyenTap.class);
        ChuDe chuDe = new ChuDe();
        intent.putExtra("ChuDe", chuDe);
        startActivity(intent);
    }

    private void suLyHienThiTheoSlide() {
        Intent intent = new Intent(toanbotu.this, SlideTuVung.class);
        ChuDe chuDe = new ChuDe();
        intent.putExtra("ChuDe",chuDe);
        startActivity(intent);
    }

    private void addControlsDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(toanbotu.this);
        popupInputDialogView = layoutInflater.inflate(R.layout.dialog_them_tu, null);

        txtTuVung = (EditText) popupInputDialogView.findViewById(R.id.txtTuVung);
        txtNghia = (EditText) popupInputDialogView.findViewById(R.id.txtNghia);
        txtPhatAm = (EditText) popupInputDialogView.findViewById(R.id.txtPhatAm);

        spLoaiTu = (Spinner) popupInputDialogView.findViewById(R.id.spLoaiTu);

        arrLoaiTu = getResources().getStringArray(R.array.arrLoaiTu);
        loaiTuAdapter = new ArrayAdapter<String>(
                toanbotu.this,
                android.R.layout.simple_spinner_item,
                arrLoaiTu);
        loaiTuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaiTu.setAdapter(loaiTuAdapter);
    }

    private void xuLyThemMoiTuVung() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm Từ Mới");
        builder.setCancelable(false);

        addControlsDialog();
        builder.setView(popupInputDialogView);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO: thực hiện thêm mới 1 từ vựng
                xyLyThemTuMoi();
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void xyLyThemTuMoi() {
        TuVung tuVung = new TuVung();
        tuVung.setTuvung(txtTuVung.getText().toString());
        tuVung.setNghia(txtNghia.getText().toString());
        tuVung.setLoaitu(spLoaiTu.getSelectedItem().toString());
        tuVung.setPhatam(txtPhatAm.getText().toString());
        dbTuVung.addTuVung(tuVung);
        hienThiTatCaTuVung();
    }



}


