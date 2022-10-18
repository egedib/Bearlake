package me.egedib.bearlake;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        ArrayList<CoordPairs> history = null;

        String COORDPAIRSARRAY = "CoordPairsArray";
        if ((bundle != null ? bundle.get(COORDPAIRSARRAY) : null) != null){
            history = (ArrayList<CoordPairs>)bundle.get(COORDPAIRSARRAY);
        }

        if (history == null){
            //TODO: error?
            this.finish();
        }


    }
}
