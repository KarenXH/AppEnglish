package education.nhom11.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import education.nhom11.data.DBTuVung;
import education.nhom11.model.ChuDe;
import education.nhom11.model.TuVung;
import education.nhom11.tuvungtienganh.R;

public class TuVungAdapter extends ArrayAdapter<TuVung> implements Filterable {

    Activity context;
    int resource;
    List<TuVung> objects;

    
    private View popupInputDialogView = null;
    private Spinner spChuDe;
    private ArrayList<ChuDe> dsChuDe;
    private ArrayAdapter<ChuDe> chuDeAdapter;
    private String dialog_title;

    private EditText txtTuVung = null;
    private EditText txtNghia = null;
    private EditText txtPhatAm = null;
    private Spinner spLoaiTu = null;
    private String []arrLoaiTu;
    private ArrayAdapter<String> loaiTuAdapter;

    private List<TuVung> tuVungListFiltered;

    private DBTuVung dbTuVung;

    public TuVungAdapter(Activity context, int resource, List<TuVung> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        tuVungListFiltered = new ArrayList<>();
        tuVungListFiltered.addAll(objects);
        dbTuVung = new DBTuVung(this.context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        final TextView txtTuVung = row.findViewById(R.id.txtTuVung);
        TextView txtLoaiTu = row.findViewById(R.id.txtLoaiTu);
        TextView txtNghia = row.findViewById(R.id.txtNghiaCuaTu);
        TextView txtPhatAm = row.findViewById(R.id.txtphatAm);

        ImageButton btnThemTuVaoChuDe = row.findViewById(R.id.btnThemTuVaoChuDe);
        ImageButton btnChinhSuaTu = row.findViewById(R.id.btnChinhSuaTu);
        ImageButton btnXoaTu = row.findViewById(R.id.btnXoaTu);

        TuVung tuVung = this.objects.get(position);
        txtTuVung.setText(tuVung.getTuvung());
        txtLoaiTu.setText(tuVung.getLoaitu()+"");
        txtNghia.setText(tuVung.getNghia() + "");
        txtPhatAm.setText(tuVung.getPhatam() + "");

        btnThemTuVaoChuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            dialog_title = "Th??m T??? V??o Ch??? ?????";
            xuLyTaoDialog(1, position);
            }
        });
        btnChinhSuaTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            dialog_title = "Ch???nh s???a th??ng tin t??? v???ng";
            xuLyTaoDialog(2, position);
            }
        });
        btnXoaTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            dialog_title = "X??c nh???n x??a";
            xuLyTaoDialog(3, position);
            }
        });

        return row;
    }

    private void xuLyTaoDialog(int n, final int pos) {
        final int cate = n ;
        //TODO --hh
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle(dialog_title);
        builder.setCancelable(false);
        if (cate==1)
        {
            //add controls th??m t??? v??o ch??? ?????
            addControlsDialog(pos);
            builder.setView(popupInputDialogView);
        }
        else if (cate==2)
        {
            //add controls ch???nh s???a th??ng tin t??? v???ng
            addControlsDialog2(pos);
            builder.setView(popupInputDialogView);
        }
        else
        {
            builder.setMessage("B???n ch???n ch???n mu???n x??a t??? v???ng?");
        }
        builder.setPositiveButton("H???y", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("Th???c Hi???n", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO: th???c hi???n h??nh ?????ng
                xuLyHanhDong(cate, pos);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void xuLyHanhDong(int n, int pos) {
        if(n == 1)
        {
            thucHienThemTuVaoChuDe(pos);
        }
        else if(n == 2)
        {
            thucHienCapNhatThongTinTuVung(pos);
        }
        else
        {
            thucHienXoaTu(pos);
        }
    }

    private void thucHienXoaTu(int pos) {
        //TODO -- x??a t???
        TuVung tuVung = this.objects.get(pos);
        dbTuVung.deleteTuVung(tuVung);
    }

    private void thucHienCapNhatThongTinTuVung(int pos) {
        //TODO -- update t???
        TuVung tuVung = this.objects.get(pos);
        tuVung.setTuvung(txtTuVung.getText().toString());
        tuVung.setNghia(txtNghia.getText().toString());
        tuVung.setPhatam(txtPhatAm.getText().toString());
        tuVung.setLoaitu(spLoaiTu.getSelectedItem().toString());
        dbTuVung.UpdateTuVung(tuVung);
    }

    private void thucHienThemTuVaoChuDe(int pos) {
        //TODO -- Th??m t??? v??o ch??? ????? kh??c
        int tuvung_id = this.objects.get(pos).getId();
        ChuDe chuDe = (ChuDe) spChuDe.getSelectedItem();
        dbTuVung.addTuVungChuDe(tuvung_id, chuDe.getId());
    }

    private void addControlsDialog(int pos) {
        //TODO : get danh s??ch ch??? ????? trong database
        //dialog th???c hi???n th??m t??? v???ng v??o ch??? ?????
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        popupInputDialogView = layoutInflater.inflate(R.layout.dialog_them_tu_vao_chu_de, null);
        spChuDe = (Spinner) popupInputDialogView.findViewById(R.id.spChuDe);
        dsChuDe = new ArrayList<>();
        chuDeAdapter = new ArrayAdapter<ChuDe>(
                this.context,
                android.R.layout.simple_spinner_item,
                dsChuDe);
        chuDeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spChuDe.setAdapter(chuDeAdapter);
        dsChuDe.addAll(dbTuVung.getAllChuDe());
        chuDeAdapter.notifyDataSetChanged();
    }

    private void addControlsDialog2(int pos) {
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

        TuVung tuVung = this.objects.get(pos);
        txtTuVung.setText(tuVung.getTuvung());
        txtNghia.setText(tuVung.getNghia());
        txtPhatAm.setText(tuVung.getPhatam());

        int spinnerPosition = loaiTuAdapter.getPosition(tuVung.getLoaitu());
        spLoaiTu.setSelection(spinnerPosition);
    }

    //TODO: t??m ki???m t??? v???ng
    public void filter(String text){
        text = text.toLowerCase(Locale.getDefault());
        if (tuVungListFiltered.size() == 0)
        {
            tuVungListFiltered.addAll(this.objects);
        }
        this.objects.clear();
        if(text.length() == 0 )
        {
            this.objects.addAll(tuVungListFiltered);
        }
        else
        {
            this.objects.clear();
            for(TuVung tuvung: tuVungListFiltered)
            {
                if(tuvung.getTuvung().toLowerCase(Locale.getDefault()).contains(text))
                {
                    this.objects.add(tuvung);
                }
            }
        }
        notifyDataSetChanged();
    }




}

