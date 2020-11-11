package mx.tec.EHL.ControlParental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import mx.tec.EHL.MainActivity
import mx.tec.EHL.R

class MainActivityControlParental : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_control_parental)
        val btn_alumnos=findViewById<Button>(R.id.btn_alumnos)
        btn_alumnos.setOnClickListener{
            val intent= Intent(this@MainActivityControlParental, MainActivityControlParental_Calificaciones::class.java)
            startActivity(intent)
        }

        val btn_trabajos=findViewById<Button>(R.id.btn_trabajos)
        btn_trabajos.setOnClickListener{
            val intent= Intent(this@MainActivityControlParental, MainActivityControlParental_Asistencia::class.java)
            startActivity(intent)
        }

        val btn_ajustes=findViewById<Button>(R.id.btn_ajustes)
        btn_ajustes.setOnClickListener{
            val intent= Intent(this@MainActivityControlParental, MainActivityControlParental_NiveldeConocimiento::class.java)
            startActivity(intent)
        }

        val btnback=findViewById<ImageView>(R.id.btn_backcp)
        btnback.setOnClickListener{
            val intent= Intent(this@MainActivityControlParental, MainActivity::class.java)
            startActivity(intent)
        }
    }
}