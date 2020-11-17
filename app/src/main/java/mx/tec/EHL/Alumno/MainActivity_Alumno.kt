package mx.tec.EHL.Alumno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.MainActivity
import mx.tec.EHL.R

class MainActivity_Alumno : AppCompatActivity() {
    lateinit var sharedpref: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_alumno)

        sharedpref = PreferencesHelper(this)

        val btn_Actividades=findViewById<Button>(R.id.btn_Actividades)
        btn_Actividades.setOnClickListener{
            val intent= Intent(this@MainActivity_Alumno,MainActivity_Alumno_Actividades::class.java)
            startActivity(intent)
        }
        val btn_Grupos=findViewById<Button>(R.id.btn_Grupos)
        btn_Grupos.setOnClickListener{
            val intent= Intent(this@MainActivity_Alumno, MainActivity_Alumno_Grupos::class.java)
            startActivity(intent)
        }
        val btn_Ajustes=findViewById<Button>(R.id.btn_Ajustes)
        btn_Ajustes.setOnClickListener{
            val intent= Intent(this@MainActivity_Alumno, MainActivity_Alumno_Ajustes::class.java)
            startActivity(intent)
        }

        val imageView4=findViewById<ImageView>(R.id.btn_backcpa)
        imageView4.setOnClickListener{
            val intent= Intent(this@MainActivity_Alumno,MainActivity::class.java)
            sharedpref.clear()
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }
}