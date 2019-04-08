package me.egedib.bearlake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private final String COORDPAIRSARRAY = "CoordPairsArray";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        ArrayList<CoordPairs> history = null;

        if ((bundle != null ? bundle.get(COORDPAIRSARRAY) : null) != null){
            history = (ArrayList<CoordPairs>)bundle.get(COORDPAIRSARRAY);
        }

        if (history == null){
            //TODO: error?
            this.finish();
        }


    }
}
