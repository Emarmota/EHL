package mx.tec.EHL

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import mx.tec.EHL.Alumno.MainActivity_Alumno
import mx.tec.EHL.ControlParental.MainActivityControlParental
import mx.tec.EHL.DataBase.AppDataBase
import mx.tec.EHL.DataBase.Tablas.Alumno
import mx.tec.EHL.DataBase.Tablas.ControlParental
import mx.tec.EHL.DataBase.Tablas.Maestro
import mx.tec.EHL.Profesor.activity_main_maestro

class MainActivity : AppCompatActivity() {
    val db by lazy { AppDataBase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editTextTextPersonName = findViewById<EditText>(R.id.editTextTextPersonName)
        val editTextTextPassword = findViewById<EditText>(R.id.editTextTextPassword)

        val buttonIngresar=findViewById<Button>(R.id.buttonIngresar)
        buttonIngresar.setOnClickListener{
            if(editTextTextPersonName.text.toString() == "m" && editTextTextPassword.text.toString() == "m"){
                val intent= Intent(this@MainActivity,activity_main_maestro::class.java)
                startActivity(intent)
            }
            else if(editTextTextPersonName.text.toString() == "a" && editTextTextPassword.text.toString() == "a"){
                val intent= Intent(this@MainActivity,MainActivity_Alumno::class.java)
                startActivity(intent)
            }
            else if(editTextTextPersonName.text.toString() == "c" && editTextTextPassword.text.toString() == "c"){
                val intent= Intent(this@MainActivity,MainActivityControlParental::class.java)
                startActivity(intent)
            }
        }
        Thread {
            val maestro = Maestro(0,"Maestro1","Grupo1","a","a")
            db.schoolDao().RegistrarMaestro(maestro)
            val alumno = Alumno(0,"alumnoA","Avanzado",4,"a","a")
            db.schoolDao().RegistrarAlumno(alumno)
            val controlParental = ControlParental(0,"Maestro1","Grupo1")
            db.schoolDao().RegistrarControlParental(controlParental)

            println(db.schoolDao().ObtenerMaestro())
            println("HOLA --------------------")
            println("HOLA --------------------")
            println("HOLA --------------------")
        }.start()


    }
}