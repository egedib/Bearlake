package me.egedib.bearlake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class EvaluationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        ArrayList<Double> distance = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>();

        for (CoordPairs pair: Speeds.getInstance().getCoordPairs() ) {
            distance.add(Math.hypot(Math.abs((double)pair.getBearX() - (double)pair.getHumanX()), Math.abs((double)pair.getHumanY() - (double)pair.getBearY())));
        }

        LineChart lineChart = findViewById(R.id.chart);

        /*for (int i = 0; i < distance.size(); i++){
            numbers.add(i+1);
        }*/

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < distance.size(); i++){
            entries.add(new Entry(i+1, distance.get(i).floatValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Tavolsag");
        dataSet.setColor(R.color.colorPrimary);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
        //finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(getApplicationContext(), ExperimentList.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(setIntent);
    }
}
