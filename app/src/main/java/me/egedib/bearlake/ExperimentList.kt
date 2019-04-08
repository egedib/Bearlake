package me.egedib.bearlake

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_experiment_list.*
import kotlin.random.Random

class ExperimentList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experiment_list)

        Eset0.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java).putExtra("Speeds",Pair(0,0)))
        }
        Eset1.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java).putExtra("Speeds",Pair(25,10)))
        }
        Eset2.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java).putExtra("Speeds",Pair(15,100)))
        }
        Eset3.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java).putExtra("Speeds",Pair(Random.nextInt(100),Random.nextInt(100))))
        }
        Eset4.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java).putExtra("Speeds",Pair(25,10)).putExtra("Pos", Pair(Random.nextInt(25,100), Random.nextInt(25,100))))
        }
    }
}
