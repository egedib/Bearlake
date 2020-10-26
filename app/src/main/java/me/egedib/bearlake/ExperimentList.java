package me.egedib.bearlake;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import java.util.Random;

public class ExperimentList extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_list);

        Button Eset0 = findViewById(R.id.Eset0);
        Button Eset1 = findViewById(R.id.Eset1);
        Button Eset2 = findViewById(R.id.Eset2);
        Button Eset3 = findViewById(R.id.Eset3);
        Button Eset4 = findViewById(R.id.Eset4);

        final Intent openWindow = new Intent(getApplicationContext(), MainActivity.class);
        final String SpeedHuman = "SpeedHuman";
        final String SpeedBear = "SpeedBear";
        Eset0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(openWindow.putExtra(SpeedHuman, 1).putExtra(SpeedBear, 1));
            }
        });

        Eset1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(openWindow.putExtra(SpeedHuman, 25).putExtra(SpeedBear, 10));
                    }
                });

        Eset2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(openWindow.putExtra(SpeedHuman, 15).putExtra(SpeedBear, 100));
                    }
                });

        Eset3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(openWindow.putExtra(SpeedHuman, new Random().nextInt(100)).putExtra(SpeedBear, new Random().nextInt(100)));
                    }
                });

        Eset4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(openWindow.putExtra(SpeedHuman, 1).putExtra(SpeedBear, 1).putExtra("PosX", new Random().nextInt(100)).putExtra("PosY", new Random().nextInt(100)));
                    }
                });
    }
}
