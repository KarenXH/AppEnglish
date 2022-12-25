package education.myprojects.tuvungtienganh;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import education.myprojects.adapter.CateAdapter;
import education.myprojects.data.DBTuVung;
import education.myprojects.model.ChuDe;
import education.myprojects.tuvungtienganh.R;

public class danhsachchude extends AppCompatActivity {

    private ImageButton btnBackMain;

    private ListView lvChuDe;
    private ArrayList<ChuDe> dsCate;
    private CateAdapter cateAdapter;

    private Dialog dialog;
    private View popupInputDialogView = null;
    private EditText txtChuDe, txtMoTa;

    DBTuVung dbTuVung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachchude);
        dbTuVung = new DBTuVung(danhsachchude.this);

        addControls();
        addEvents();
    }

    private void addControls() {
        lvChuDe = findViewById(R.id.lvChuDe);
        dsCate = new ArrayList<ChuDe>();
        cateAdapter = new CateAdapter(
                danhsachchude.this,
                R.layout.item_chu_de,
                dsCate);
        lvChuDe.setAdapter(cateAdapter);
        btnBackMain = findViewById(R.id.btnBackMain);
    }

    private void addEvents() {
        // Nút quay lại màn hình chính
        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(danhsachchude.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void hienThiDanhSachChuDe() {
        dsCate.clear();
        dsCate.addAll(dbTuVung.getAllChuDe());
        cateAdapter.notifyDataSetChanged();
    }

    //Tạo Option menu cho danh sách chủ đề
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.danh_sach_chu_de, menu);
        MenuItem mnuSearch = menu.findItem(R.id.mnuTimKiemChuDe);
        SearchView searchView = (SearchView) mnuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Chức năng lọc của adapter, dùng để tìm kiếm
                cateAdapter.filter(newText.trim());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    //Tạo sự kiện từng menuOption
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mnuChuDe)
        {
            //TODO
            goiManHinhThemChuDe();
        }
        else if(item.getItemId() == R.id.mnuTimTu)
        {
            //TODO: gọi màn hình tìm từ
            Intent intent = new Intent(danhsachchude.this, toanbotu.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void goiManHinhThemChuDe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm Chủ Đề Mới");
        builder.setCancelable(false);

        addControlsDialog();
        builder.setView(popupInputDialogView);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(danhsachchude.this, "Không thêm nữa!!", Toast.LENGTH_SHORT).show();
            dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ChuDe chuDeadd = new ChuDe(txtChuDe.getText().toString(), txtMoTa.getText().toString());
                dbTuVung.addChuDe(chuDeadd);
                hienThiDanhSachChuDe();
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addControlsDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(danhsachchude.this);
        popupInputDialogView = layoutInflater.inflate(R.layout.dialog_them_chu_de, null);

        txtChuDe = (EditText) popupInputDialogView.findViewById(R.id.txtChuDe);
        txtMoTa = (EditText) popupInputDialogView.findViewById(R.id.txtMoTa);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hienThiDanhSachChuDe();
    }
}
