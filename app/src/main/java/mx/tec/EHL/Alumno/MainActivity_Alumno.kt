package mx.tec.EHL.Alumno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import mx.tec.EHL.ControlParental.MainActivityControlParental
import mx.tec.EHL.MainActivity
import mx.tec.EHL.R

class MainActivity_Alumno : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_alumno)

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

    }
}