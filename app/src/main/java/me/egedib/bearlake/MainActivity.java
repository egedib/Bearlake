package me.egedib.bearlake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ViewListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView nevem = findViewById(R.id.nevem);
        nevem.setText("Egedi Balázs László - 2019 - leadás");


        SeekBar humanSpeed = findViewById(R.id.humanSpeed);
        humanSpeed.setProgress(1);
        humanSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Speeds.getInstance().setHumanSpeed(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar bearSpeed = findViewById(R.id.bearSpeed);
        bearSpeed.setProgress(1);
        bearSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Speeds.getInstance().setBearSpeed(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

         Bundle bundle = getIntent().getExtras();
         Pair<Integer,Integer> speeds = (Pair)bundle.get("Speeds");
         //TODO
        if (bundle.get("Pos") != null){
            //TODO
        }

        ((BearLakeView)findViewById(R.id.bear_lake_view)).setListener(this);
    }

    @Override
    public void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
