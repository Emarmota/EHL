package mx.tec.EHL.Profesor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import mx.tec.EHL.ControlParental.MainActivityControlParental_Asistencia
import mx.tec.EHL.ControlParental.MainActivityControlParental_NiveldeConocimiento
import mx.tec.EHL.R

class activity_main_maestro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro)

        val btn_alumnos=findViewById<Button>(R.id.btn_alumnos)
        btn_alumnos.setOnClickListener{
            val intent= Intent(this@activity_main_maestro, activity_main_maestro_alumnos::class.java)
            startActivity(intent)
        }

        val btn_trabajos=findViewById<Button>(R.id.btn_trabajos)
        btn_trabajos.setOnClickListener{
            val intent= Intent(this@activity_main_maestro, activity_main_maestro_trabajos::class.java)
            startActivity(intent)
        }

        val btn_ajustes=findViewById<Button>(R.id.btn_ajustes)
        btn_ajustes.setOnClickListener{
            val intent= Intent(this@activity_main_maestro, activity_main_maestro_ajustes::class.java)
            startActivity(intent)
        }
    }
}