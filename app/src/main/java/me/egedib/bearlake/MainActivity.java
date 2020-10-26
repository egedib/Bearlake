package me.egedib.bearlake;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ViewListener{

    final String SpeedHuman = "SpeedHuman";
    final String SpeedBear = "SpeedBear";
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
         humanSpeed.setProgress(bundle.getInt(SpeedHuman));
         bearSpeed.setProgress(bundle.getInt(SpeedBear));

        if (bundle.get("PosX") != null){
            Speeds.getInstance().setBearX((int)bundle.get("PosX"));
            Speeds.getInstance().setBearY((int)bundle.get("PosY"));
        } else{
            Speeds.getInstance().setBearX(0);
            Speeds.getInstance().setBearY(0);
        }

        ((BearLakeView)findViewById(R.id.bear_lake_view)).setListener(this);
    }

    @Override
    public void showMessage(final String message) {
        //finish();
        startActivity(new Intent(getApplicationContext(), EvaluationActivity.class));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
