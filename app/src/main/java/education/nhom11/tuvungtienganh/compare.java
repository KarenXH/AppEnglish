package education.nhom11.tuvungtienganh;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

public class compare extends AppCompatActivity {
    private ImageButton btnBackMain;
    private TextView txtTenChuDe;
    private Button start, stop, play;
    private MediaRecorder myAudioRecorder;
    private String outputFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        addControls();
        addEvents();

    }

    private void addControls() {
        txtTenChuDe = findViewById(R.id.txtTenChuDe);
        txtTenChuDe.setText("Phát âm");
        btnBackMain = findViewById(R.id.btnBackDsChuDe);
        start = findViewById(R.id.btnStart);
        stop = findViewById(R.id.btnStop);
        play = findViewById(R.id.btnStartPlaying);
        stop.setEnabled(false);
        play.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
    }

    private void addEvents() {
        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(compare.this, TuDien.class);
                startActivity(intent2);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 try {
                     myAudioRecorder.prepare();
                     myAudioRecorder.start();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    start.setEnabled(false);
                    stop.setEnabled(true);
                    Toast.makeText(compare.this, "Recording started", Toast.LENGTH_SHORT).show();
                }

        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
                start.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio Recorder successfully", Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}