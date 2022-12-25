package education.HHK.tuvungtienganh;

import android.app.AlertDialog;
import android.app.Dialog;
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

import education.HHK.adapter.TuVungAdapter;
import education.HHK.data.DBTuVung;
import education.HHK.model.ChuDe;
import education.HHK.model.TuVung;

public class danhsachtu extends AppCompatActivity {

    private Dialog dialog;
    private View popupInputDialogView = null;
    private EditText txtTuVung = null;
    private EditText txtNghia = null;
    private EditText txtPhatAm = null;
    private Spinner spLoaiTu = null;

    private String []arrLoaiTu;
    private ArrayAdapter<String> loaiTuAdapter;

    private ChuDe chuDe;
    private ImageButton btnBackDsChuDe;
    private TextView txtTenChuDe;

    private ListView lvTuVung;
    private ArrayList<TuVung> dsTuVung;
    private TuVungAdapter tuVungAdapter;

    private DBTuVung dbTuVung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtu);

        Intent intent_old = getIntent();
        chuDe = (ChuDe) intent_old.getSerializableExtra("ChuDe");

        dbTuVung = new DBTuVung(this);
        addControls();
        addEvents();
    }

    private void addEvents() {

        //nút quay lại danh sách chủ đề
        btnBackDsChuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(danhsachtu.this, danhsachchude.class);
                startActivity(intent);
            }
        });
    }

    private void hienThiToanBoTuVung()
    {
        dsTuVung.clear();
        dsTuVung.addAll(dbTuVung.getListTuVungByChuDe(chuDe.getId()));
//        tuVungAdapter.notifyDataSetChanged();
        tuVungAdapter = new TuVungAdapter(
                danhsachtu.this,
                R.layout.item_tu_vung,
                dsTuVung);
        lvTuVung.setAdapter(tuVungAdapter);
    }

    private void addControls() {
        txtTenChuDe = findViewById(R.id.txtTenChuDe);
        txtTenChuDe.setText(chuDe.getTen());
        lvTuVung = findViewById(R.id.lvTuVung);
        dsTuVung = new ArrayList<>();
//        tuVungAdapter = new TuVungAdapter(
//                danhsachtu.this,
//                R.layout.item_tu_vung,
//                dsTuVung);
//        lvTuVung.setAdapter(tuVungAdapter);
        btnBackDsChuDe = findViewById(R.id.btnBackDsChuDe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_danh_sach_tu, menu);
        MenuItem mnuSearch = menu.findItem(R.id.mnuTimKiemTuVung);
        SearchView searchView = (SearchView) mnuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO:
                //Chức năng lọc của adapter, dùng để tìm kiếm
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
            //Hiển thị diallog thêm từ mới vào chủ đề
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
        Intent intent = new Intent(danhsachtu.this, LuyenTap.class);
        intent.putExtra("ChuDe", chuDe);
        startActivity(intent);
    }

    private void suLyHienThiTheoSlide() {
        Intent intent = new Intent(danhsachtu.this, SlideTuVung.class);
        intent.putExtra("ChuDe",chuDe);
        startActivity(intent);
    }

    private void addControlsDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(danhsachtu.this);
        popupInputDialogView = layoutInflater.inflate(R.layout.dialog_them_tu, null);

        txtTuVung = (EditText) popupInputDialogView.findViewById(R.id.txtTuVung);
        txtNghia = (EditText) popupInputDialogView.findViewById(R.id.txtNghia);
        txtPhatAm = (EditText) popupInputDialogView.findViewById(R.id.txtPhatAm);

        spLoaiTu = (Spinner) popupInputDialogView.findViewById(R.id.spLoaiTu);

        arrLoaiTu = getResources().getStringArray(R.array.arrLoaiTu);
        loaiTuAdapter = new ArrayAdapter<String>(
                danhsachtu.this,
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
        System.out.println("***********tuVungtuVung***********" + tuVung);
        dbTuVung.addTuVung(tuVung);
        int id = dbTuVung.getLastTuVung();
        System.out.println("***********tuVungtuVung***********" + id);
        dbTuVung.addTuVungChuDe(id, chuDe.getId());
        hienThiToanBoTuVung();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hienThiToanBoTuVung();
    }
}
