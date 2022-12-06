
package education.nhom11.tuvungtienganh;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import education.nhom11.adapter.slideAdapter;
import education.nhom11.data.DBTuVung;
import education.nhom11.model.ChuDe;
import education.nhom11.model.TuVung;

public class SlideTuVung extends AppCompatActivity {


    private ChuDe chuDe;
    private TextView txtTenChuDe;
    private ImageButton btnbackDanhSachTuVung;

    private ViewPager viewPage;
    private ArrayList<TuVung> dsTuVung;
    private slideAdapter slideAdapter;
    private ProgressBar progressBarPercent;
    private int progress = 10;

    private DBTuVung dbTuVung;

    private int sli = 0;
    private int precent = 10 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silde_tu_vung);
        Intent intent_old = getIntent();
        chuDe = (ChuDe) intent_old.getSerializableExtra("ChuDe");

        dbTuVung = new DBTuVung(this);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtTenChuDe = findViewById(R.id.txtTenChuDe);
        viewPage = findViewById(R.id.viewPage);
        dsTuVung = new ArrayList<>();
        slideAdapter = new slideAdapter(
                SlideTuVung.this,
                dsTuVung
        );
        viewPage.setAdapter(slideAdapter);
        txtTenChuDe.setText(chuDe.getTen());
        btnbackDanhSachTuVung = findViewById(R.id.btnBackDsChuDe);
        progressBarPercent = findViewById(R.id.progressBarPercent);
    }

    private void addEvents() {
        dsTuVung.clear();
        if(chuDe.getTen() == null)
        {
            dsTuVung.addAll(dbTuVung.getAllTuVung());
        }
        else{
            dsTuVung.addAll(dbTuVung.getListTuVungByChuDe(chuDe.getId()));
        }
        slideAdapter.notifyDataSetChanged();
        progressBarPercent.setProgress(precent);
        progressBarPercent.setMax(precent * dsTuVung.size());
        btnbackDanhSachTuVung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chuDe.getTen() == null)
                {
                    Intent intent = new Intent(SlideTuVung.this, toanbotu.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(SlideTuVung.this, danhsachtu.class);
                    intent.putExtra("ChuDe", chuDe);
                    startActivity(intent);
                }
            }
        });

        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if(sli < i)
                {
                    progress += precent;
                    progressBarPercent.setProgress(progress);
                }
                else if (sli > i)
                {
                    progress -= precent;
                    progressBarPercent.setProgress(progress);
                }
                sli = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
}
