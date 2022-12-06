package education.nhom11.tuvungtienganh;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import education.nhom11.adapter.GridAdapter;
import education.nhom11.data.DBTuVung;
import education.nhom11.model.ChuDe;
import education.nhom11.model.TuVung;

public class LuyenTap extends AppCompatActivity {

    private ImageButton btnBackDsTuVung;
    private Button btnBoQua, btnKiemTra, btnTuTiep, btnLamLai;
    private TextView txtTuVung, txtTenChuDe, txtInform, txtPhatAm;
    private EditText txtNghia;
    private Switch swNgauNhien;

    private GridView gvGoiY;
    private ArrayList<String> dsGoiY;
    private GridAdapter gridAdapter;

    private ChuDe chuDe;
    private String DATATU= null;
    private String NGHIA = null;
    private int COUNT = 0;
    private Random rand = new Random();

    private DBTuVung dbTuVung;
    private boolean flagNgayNhien = false;
    private ArrayList<TuVung> dsTuVung;
    private TuVung tuVungTemp;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luyen_tap);

        Intent intent_old = getIntent();
        chuDe = (ChuDe) intent_old.getSerializableExtra("ChuDe");

        dbTuVung = new DBTuVung(this);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnBackDsTuVung = findViewById(R.id.btnBackDsChuDe);
        btnBoQua = findViewById(R.id.btnBoQua);
        btnKiemTra = findViewById(R.id.btnKiemTra);
        btnTuTiep = findViewById(R.id.btnTuTiep);
        btnLamLai = findViewById(R.id.btnLamLai);
        swNgauNhien = findViewById(R.id.swNgauNhien);
        swNgauNhien.setChecked(false);
        txtPhatAm = findViewById(R.id.txtPhatAmL);

        btnTuTiep.setVisibility(View.GONE);
        btnLamLai.setVisibility(View.GONE);

        txtTuVung = findViewById(R.id.txtTuVung);
        txtTenChuDe = findViewById(R.id.txtTenChuDe);
        txtNghia = findViewById(R.id.txtNghia);
        txtInform = findViewById(R.id.txtInform);
        txtInform.setVisibility(View.INVISIBLE);

        dsTuVung = new ArrayList<TuVung>();
        layDanhSachTuVung();

        dsGoiY = new ArrayList<String>();
        gvGoiY = findViewById(R.id.gvGoiY);
        gridAdapter = new GridAdapter(
                LuyenTap.this,
                R.layout.item_grid_view,
                dsGoiY);
        gvGoiY.setAdapter(gridAdapter);
        txtTenChuDe.setText(chuDe.getTen());
    }

    private void layDanhSachTuVung() {
        //get ds tu vung
        dsTuVung.clear();
        if(chuDe.getTen() == null)
        {
            dsTuVung.addAll(dbTuVung.getAllTuVung());
        }
        else{
            dsTuVung.addAll(dbTuVung.getListTuVungByChuDe(chuDe.getId()) );
        }
        COUNT = dsTuVung.size();
    }

    private void addEvents() {
        taoTuVung();

        btnKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyKiemTra();
            }
        });
        btnBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGoiTuTiepTheo();
            }
        });
        btnTuTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGoiTuTiepTheo();
            }
        });

        btnBackDsTuVung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chuDe.getTen() == null)
                {
                    Intent intent = new Intent(LuyenTap.this, toanbotu.class);
                    intent.putExtra("ChuDe", chuDe);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(LuyenTap.this, danhsachtu.class);
                    intent.putExtra("ChuDe", chuDe);
                    startActivity(intent);
                }
            }
        });

        txtNghia.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Toast.makeText(LuyenTap.this, txtNghia.getText(), Toast.LENGTH_SHORT).show();
                    xuLyKiemTra();
                }
                return false;
            }
        });

        btnLamLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thucHienLaiTuVung();
            }
        });

        swNgauNhien.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(swNgauNhien.isChecked())
                {
                    //TODO" --------
                    Toast.makeText(LuyenTap.this, "Checked", Toast.LENGTH_LONG).show();
                    flagNgayNhien = true;
                }
                else {
                    flagNgayNhien = false;
                    Toast.makeText(LuyenTap.this, "false", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void thucHienLaiTuVung() {
        //TODO:
        dsGoiY.clear();
        // dữ liệu demo
        String []arr = DATATU.split("");
        Collections.addAll(dsGoiY, arr);
        dsGoiY.remove(0);
        gridAdapter.notifyDataSetChanged();
        txtNghia.setText("");
        txtNghia.requestFocus();
        txtInform.setVisibility(View.INVISIBLE);
        btnLamLai.setVisibility(View.GONE);
        btnBoQua.setVisibility(View.VISIBLE);
        btnKiemTra.setVisibility(View.VISIBLE);
        btnTuTiep.setVisibility(View.GONE);
    }

    private void taoTuVung() {
        dsGoiY.clear();
        final int n;
        if(flagNgayNhien == true)
        {
            n = rand.nextInt(COUNT);
            DATATU = dsTuVung.get(n).getTuvung();
            NGHIA = dsTuVung.get(n).getNghia();
            index = n;
        }
        else {
            if(index == -1)
            {
                n = 0;
                tuVungTemp = dsTuVung.get(n);
            }
            else
            {
                n = index + 1;
                tuVungTemp = dsTuVung.get(n);
            }
            index += 1;
            DATATU = dsTuVung.get(n).getTuvung();
            NGHIA = dsTuVung.get(n).getNghia();
        }
        txtTuVung.setText(NGHIA);
        // dữ liệu demo
        String []arr = DATATU.split("");
        Collections.addAll(dsGoiY, arr);
        dsGoiY.remove(0);
        gridAdapter.notifyDataSetChanged();
        txtPhatAm.setText(dsTuVung.get(n).getLoaitu());
        txtNghia.setText("");
        txtNghia.requestFocus();
    }

    private void xuLyGoiTuTiepTheo() {
        //TODO: gọi từ tiếp theo trong list
        taoTuVung();
        btnBoQua.setVisibility(View.VISIBLE);
        btnKiemTra.setVisibility(View.VISIBLE);
        btnTuTiep.setVisibility(View.GONE);
        btnLamLai.setVisibility(View.GONE);
        txtInform.setVisibility(View.INVISIBLE);
    }

    private void xuLyKiemTra() {
        // Nếu Đúng hiển thị thông báo, hiện nút next
        String nghia = txtNghia.getText().toString();
        if(nghia.equalsIgnoreCase(DATATU))
        {
            txtInform.setText("Chính xác!!");
            txtInform.setBackgroundColor(Color.GREEN);
            txtInform.setVisibility(View.VISIBLE);

            btnBoQua.setVisibility(View.GONE);
            btnKiemTra.setVisibility(View.GONE);
            btnTuTiep.setVisibility(View.VISIBLE);
            btnLamLai.setVisibility(View.VISIBLE);
        }
        else
        {
            txtInform.setText("Không chính xác!!");
            txtInform.setBackgroundColor(Color.RED);
            txtInform.setVisibility(View.VISIBLE);
        }
    }
}
