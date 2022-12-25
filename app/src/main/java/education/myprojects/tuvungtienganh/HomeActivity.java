package education.myprojects.tuvungtienganh;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import education.myprojects.tuvungtienganh.R;

public class HomeActivity extends AppCompatActivity {

    int SPLASH_TIME_OUT = 1000;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        },SPLASH_TIME_OUT);
    }
}