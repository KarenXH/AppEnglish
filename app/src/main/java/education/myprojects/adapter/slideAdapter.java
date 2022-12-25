package education.myprojects.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import education.myprojects.model.TuVung;
import education.myprojects.tuvungtienganh.R;

public class slideAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<TuVung> dsTuVung;

    private TextView txtTuVung;
    private TextView txtPhatAm;
    private TextView txtLoaiTu;
    private TextView txtNghia;

    public slideAdapter(Context context, ArrayList<TuVung> dsTuVung) {
        this.context = context;
        this.dsTuVung = dsTuVung;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dsTuVung.size();
    }

    @Override
    public boolean isViewFromObject(View view,Object o) {
        return (view.equals(o));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //Chuyển file xml thành view
        View slide = inflater.inflate(R.layout.item_slide , container, false);
        //Ánh xạ control view
        LinearLayout linearLayout = slide.findViewById(R.id.slideLinearLayout);
        txtTuVung = slide.findViewById(R.id.txtTuVung);
        txtLoaiTu = slide.findViewById(R.id.txtLoaiTu);
        txtPhatAm = slide.findViewById(R.id.txtPhatAm);
        txtNghia = slide.findViewById(R.id.txtNghia);
        //đổ dữ liệu
        TuVung tuVung = (TuVung) dsTuVung.get(position);
        txtTuVung.setText(tuVung.getTuvung());
        txtLoaiTu.setText(tuVung.getLoaitu());
        txtPhatAm.setText(tuVung.getPhatam());
        txtNghia.setText(tuVung.getNghia());
        //thêm vào container
        container.addView(slide);
        return slide;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
