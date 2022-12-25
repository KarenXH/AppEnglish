package education.HHK.tuvungtienganh;

import static education.HHK.Interface.IAPIService.*;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import education.HHK.Interface.IAPIService;
import education.HHK.model.API.TranslateTuVung;
import education.HHK.model.API.TuVung;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TuDien extends AppCompatActivity {
    private ImageButton btnBackMain;
    private TextView txtTenChuDe;
    private Button btnDich;
    private EditText edtEN;
    private String pronouce,linkVoice,enChar,textEN,dinhNghia,example;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tudien);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtTenChuDe = findViewById(R.id.txtTenChuDe);
        txtTenChuDe.setText("Từ điển");
        btnBackMain = findViewById(R.id.btnBackDsChuDe);
        btnDich = findViewById(R.id.btnDich);
        edtEN = findViewById(R.id.edtEN);
    }

    private void addEvents() {
        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(TuDien.this, MainActivity.class);
                startActivity(intent2);
            }
        });
        btnDich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallAPIGetTranslater();
                CallAPIGetTuDien();
            }
        });

    }

    private void CallAPIGetTranslater(){
        IAPIService requestInterface = new Retrofit.Builder()
                .baseUrl(Trans_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(IAPIService.class);

        new CompositeDisposable().add(requestInterface.GetTrans(edtEN.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(TranslateTuVung translateTuVung) {
        enChar = translateTuVung.getSentences().get(0).getFields().getVi();
    }

    private void handleError(Throwable throwable) {
        Toast.makeText(TuDien.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
    }

    private void CallAPIGetTuDien(){
        IAPIService requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(IAPIService.class);

        new CompositeDisposable().add(requestInterface.GetTuDien(edtEN.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError) );
    }

    private void handleResponse(ArrayList<TuVung> searchTuVung) {
        if(linkVoice == null)
        {
            for (int i = 0; i <= 5; i++) {
                linkVoice = searchTuVung.get(0).getPhonetics().get(i).getAudio();
                if(linkVoice.length() > 10)
                {
                    if(linkVoice.contains("http") == true)
                    {
                        break;
                    }
                    else
                    {
                        linkVoice = "https"+linkVoice;
                    }
                }

            }
        }
        pronouce = searchTuVung.get(0).getPhonetics().get(0).getText();
        textEN = searchTuVung.get(0).getWord();
        dinhNghia = searchTuVung.get(0).getMeanings().get(0).getDefinitions().get(0).getDefinition();
        example = searchTuVung.get(0).getMeanings().get(0).getDefinitions().get(0).getExample();
        Intent intent = new Intent(TuDien.this,dich.class);
        intent.putExtra("textEN",textEN);
        intent.putExtra("enChar",enChar);
        intent.putExtra("linkVoice",linkVoice);
        intent.putExtra("pronouce",pronouce);
        intent.putExtra("dinhNghia",dinhNghia);
        intent.putExtra("example",example);
        startActivity(intent);
    }
}
