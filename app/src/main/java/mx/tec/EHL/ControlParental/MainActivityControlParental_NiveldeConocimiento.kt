package mx.tec.EHL.ControlParental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import mx.tec.EHL.Adapter.ControlParentalAdapter
import mx.tec.EHL.R
import mx.tec.EHL.Profesor.activity_main_maestro

class MainActivityControlParental_NiveldeConocimiento : AppCompatActivity() {
    lateinit var activityAdapter: ControlParentalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_control_parental__nivelde_conocimiento)


        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        imageView4.setOnClickListener {
            val intent = Intent(this@MainActivityControlParental_NiveldeConocimiento, activity_main_maestro::class.java)
            startActivity(intent)
        }
    }


}