package education.nhom11.tuvungtienganh;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class dich extends AppCompatActivity {
    String pronouce,linkVoice,enChar,textEN,dinhNghia,example;
    private TextView txtWord,txtPhienAm,txtMeaning,txtDinhNghia,txtExample;
    private ImageButton btnPlay, btnBackMain;
    private MediaPlayer mediaPlayer;
    private Button btnLuyenAm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dich);

        Intent intent = getIntent();
        pronouce= intent.getStringExtra("pronouce");
        linkVoice= intent.getStringExtra("linkVoice");
        enChar= intent.getStringExtra("enChar");
        textEN= intent.getStringExtra("textEN");
        dinhNghia = intent.getStringExtra("dinhNghia");
        example = intent.getStringExtra("example");
        addControls();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio("https:" + linkVoice);
            }
        });
        btnBackMain = findViewById(R.id.btnBackDsChuDe);
        btnLuyenAm = findViewById(R.id.btnLuyenAm);
        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(dich.this, TuDien.class);
                startActivity(intent2);
            }
        });
        btnLuyenAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(dich.this, compare.class);
                startActivity(intent2);
            }
        });
    }
    private void playAudio(String audioUrl) {
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void addControls() {
        txtWord = findViewById(R.id.txtWord);
        txtWord.setText(textEN);
        txtPhienAm = findViewById(R.id.txtPhienAm);
        txtPhienAm.setText("/" + pronouce + "/");
        txtMeaning = findViewById(R.id.txtMeaning);
        txtMeaning.setText(enChar);
        btnPlay= findViewById(R.id.btnAudio);
        txtDinhNghia = findViewById(R.id.txtDinhNghia);
        txtDinhNghia.setText(dinhNghia);
        txtExample = findViewById(R.id.txtExample);
        txtExample.setText(example);
    }
}