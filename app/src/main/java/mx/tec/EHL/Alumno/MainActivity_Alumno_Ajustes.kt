package mx.tec.EHL.Alumno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import mx.tec.EHL.PopUpClassCambiarContraseñaAlumno
import mx.tec.EHL.PopUpClassContacto
import mx.tec.EHL.R

class MainActivity_Alumno_Ajustes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_alumno_ajustes)

        val popupButton3 =
            findViewById<Button>(R.id.btn_contacto)
        popupButton3.setOnClickListener { v ->
            val popUpClass = PopUpClassContacto()
            popUpClass.showPopupWindow(v)
        }

        val popupButton4 =
            findViewById<Button>(R.id.btn_cambiarContrasena)
        popupButton4.setOnClickListener { v ->
            val popUpClass = PopUpClassCambiarContraseñaAlumno()
            popUpClass.showPopupWindow(v)
        }

        val imageView4=findViewById<ImageView>(R.id.btn_back_maa)
        imageView4.setOnClickListener{
            val intent= Intent(this@MainActivity_Alumno_Ajustes,MainActivity_Alumno::class.java)
            startActivity(intent)
        }
    }
}