package education.nhom11.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import education.nhom11.data.DBTuVung;
import education.nhom11.model.ChuDe;
import education.nhom11.model.TuVung;
import education.nhom11.tuvungtienganh.danhsachtu;
import education.nhom11.tuvungtienganh.R;

public class CateAdapter extends ArrayAdapter<ChuDe> {

    private Activity context;
    private int resource;
    private List<ChuDe> objects;

    private TextView txtChuDe;
    private ImageButton btnNextDanhSachTu;

    private ImageButton btnThemTuVung;
    private ImageButton btnChinhSuaChuDe;
    private ImageButton btnXoaChuDe;

    private String dialog_title;
    private View popupInputDialogView = null;
    private EditText txtTuVung = null;
    private EditText txtNghia = null;
    private EditText txtPhatAm = null;
    private Spinner spLoaiTu = null;

    private EditText txtChuDeDialog, txtMoTaDialog;

    private String []arrLoaiTu;
    private ArrayAdapter<String> loaiTuAdapter;
    private DBTuVung dbTuVung;

    private ArrayList<ChuDe> chuDeListFiltered;


    public CateAdapter(Activity context, int resource, List<ChuDe> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        chuDeListFiltered = new ArrayList<>();
        chuDeListFiltered.addAll(objects);
        dbTuVung = new DBTuVung(this.context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        txtChuDe = row.findViewById(R.id.txtChuDe);
        btnNextDanhSachTu = row.findViewById(R.id.btnNextDanhSachTu);
        btnThemTuVung = row.findViewById(R.id.btnThemTuVung);
        btnChinhSuaChuDe = row.findViewById(R.id.btnChinhSuaChuDe);
        btnXoaChuDe = row.findViewById(R.id.btnXoaChuDe);


        final ChuDe chuDe = this.objects.get(position);
        txtChuDe.setText(chuDe.getTen());

        txtChuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGoiGiaoDienDanhSachTu(chuDe);
            }
        });
        btnNextDanhSachTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGoiGiaoDienDanhSachTu(chuDe);
            }
        });

        btnChinhSuaChuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_title = "Chỉnh sửa thông tin Chủ đề";
                xuLyTaoDialog(2, position);
            }
        });
        btnXoaChuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_title = "Xóa Chủ đề";
                xuLyTaoDialog(3, position);
            }
        });
        btnThemTuVung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_title = "Thêm Từ Mới Vào Chủ đề";
                xuLyTaoDialog(1, position);
            }
        });

        return row;
    }

    private void xuLyTaoDialog(int n, final int pos) {
        final int cate = n ;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle(dialog_title);
        builder.setCancelable(false);
        if (cate==1)
        {
            //add controls thêm từ vào chủ đề
            addControlsDialog(pos);
            builder.setView(popupInputDialogView);
        }
        else if (cate==2)
        {
            //add controls chỉnh sửa thông tin từ vựng
            addControlsDialog2(pos);
            builder.setView(popupInputDialogView);
        }
        else
        {
            builder.setMessage("Bạn chắn chắn muốn xóa chủ đề?");
        }
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("Thực Hiện", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO: thực hiện hành động
                xuLyHanhDong(cate, pos);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addControlsDialog(int pos) {
        //TODO : get danh sách chủ đề trong database
        //dialog thực hiện thêm từ vựng vào chủ đề
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        popupInputDialogView = layoutInflater.inflate(R.layout.dialog_them_tu, null);

        txtTuVung = (EditText) popupInputDialogView.findViewById(R.id.txtTuVung);
        txtNghia = (EditText) popupInputDialogView.findViewById(R.id.txtNghia);
        txtPhatAm = (EditText) popupInputDialogView.findViewById(R.id.txtPhatAm);

        spLoaiTu = (Spinner) popupInputDialogView.findViewById(R.id.spLoaiTu);

        arrLoaiTu = this.context.getResources().getStringArray(R.array.arrLoaiTu);
        loaiTuAdapter = new ArrayAdapter<String>(
                this.context,
                android.R.layout.simple_spinner_item,
                arrLoaiTu);
        loaiTuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaiTu.setAdapter(loaiTuAdapter);
    }

    private void addControlsDialog2(int pos) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        popupInputDialogView = layoutInflater.inflate(R.layout.dialog_them_chu_de, null);

        txtChuDeDialog = (EditText) popupInputDialogView.findViewById(R.id.txtChuDe);
        txtMoTaDialog = (EditText) popupInputDialogView.findViewById(R.id.txtMoTa);

        ChuDe chuDe = this.objects.get(pos);
        txtChuDeDialog.setText(chuDe.getTen());
        txtMoTaDialog.setText(chuDe.getMota());
    }

    private void xuLyHanhDong(int n, int pos) {
        //TODO
        if(n == 1)
        {
            xuLyThemTuVaoChuDe(pos);
        }
        else if(n == 2)
        {
            goiDialogChinhSuaChuDe(pos);
        }
        else
        {
            goiDialogXoaTu(pos);
        }
    }

    private void xuLyThemTuVaoChuDe(int pos) {
        ChuDe chuDe = this.objects.get(pos);
        TuVung tuVung= new TuVung(
            txtTuVung.getText().toString(),
            txtNghia.getText().toString(),
            txtPhatAm.getText().toString(),
            spLoaiTu.getSelectedItem().toString()
        );
        dbTuVung.addTuVung(tuVung);
        int id = dbTuVung.getLastTuVung();
        dbTuVung.addTuVungChuDe(id, chuDe.getId());
    }

    private void goiDialogXoaTu(int pos) {
        ChuDe chuDe = this.objects.get(pos);
        dbTuVung.deleteChuDe(chuDe);
    }

    private void goiDialogChinhSuaChuDe(int pos) {
        ChuDe chuDe = this.objects.get(pos);
        chuDe.setTen(txtChuDeDialog.getText().toString());
        chuDe.setMota(txtMoTaDialog.getText().toString());
        dbTuVung.UpdateChuDe(chuDe);
    }

    private void xuLyGoiGiaoDienDanhSachTu(ChuDe chuDe) {
        Intent intent = new Intent(this.context, danhsachtu.class);
        intent.putExtra("ChuDe", chuDe);
        this.context.startActivity(intent);
    }
    //TODO: tìm kiếm từ vựng
    public void filter(String text){
        text = text.toLowerCase(Locale.getDefault());
        if (chuDeListFiltered.size() == 0)
        {
            chuDeListFiltered.addAll(this.objects);
        }
        this.objects.clear();
        if(text.length() == 0 )
        {
            this.objects.addAll(chuDeListFiltered);
        }
        else
        {
            this.objects.clear();
            for(ChuDe chuDe: chuDeListFiltered)
            {
                if(chuDe.getTen().toLowerCase(Locale.getDefault()).contains(text))
                {
                    this.objects.add(chuDe);
                }
            }
        }
        notifyDataSetChanged();
    }

}
