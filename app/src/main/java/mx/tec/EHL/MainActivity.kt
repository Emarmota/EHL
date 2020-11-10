package mx.tec.EHL

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.tec.EHL.Alumno.MainActivity_Alumno
import mx.tec.EHL.ControlParental.MainActivityControlParental
import mx.tec.EHL.DataBase.AppDataBase
import mx.tec.EHL.DataBase.Tablas.Alumno
import mx.tec.EHL.DataBase.Tablas.ControlParental
import mx.tec.EHL.DataBase.Tablas.Maestro
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.Profesor.activity_main_maestro
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    val db by lazy { AppDataBase(this) }
    val sharedPref by lazy { PreferencesHelper(this) }
    var editTextTextPersonName : EditText? = null
    var editTextTextPassword : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName)
        editTextTextPassword = findViewById(R.id.editTextTextPassword)


        val editTextTextPersonName = findViewById<EditText>(R.id.editTextTextPersonName)
        val editTextTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        var queue = Volley.newRequestQueue(this)

        val buttonIngresar=findViewById<Button>(R.id.buttonIngresar)
        buttonIngresar.setOnClickListener{
            val uri = "http://192.168.50.22:3000/api/inicioSesion/"+editTextTextPersonName.text.toString()+"/"+editTextTextPassword.text.toString()
            //val uri = "http://192.168.50.22:3000/api/inicioSesion/c/c"
            val listener = Response.Listener<JSONArray> {response ->
                Log.e("MENSAJE_EXITO",response.toString())
                InicioSesion(response.getJSONObject(0).getString("perfil"))

            }
            val error = Response.ErrorListener {error ->
                Log.e("MENSAJE_ERROR",error.message!!)
            }
            val request = JsonArrayRequest(Request.Method.GET, uri, listener,error)
            queue.add(request)

        }



    }


    fun InicioSesion(tipoUsuario : String){
        if(tipoUsuario == "Maestro"){
            saveSession(editTextTextPersonName!!.text.toString(), editTextTextPassword!!.text.toString())
            val intent= Intent(this@MainActivity,activity_main_maestro::class.java)
            startActivity(intent)
        }
        else if(tipoUsuario == "Alumno"){
            saveSession(editTextTextPersonName!!.text.toString(), editTextTextPassword!!.text.toString())
            val intent= Intent(this@MainActivity,MainActivity_Alumno::class.java)
            startActivity(intent)
        }
        else if(tipoUsuario == "ControlParental"){
            saveSession(editTextTextPersonName!!.text.toString(), editTextTextPassword!!.text.toString())
            val intent= Intent(this@MainActivity,MainActivityControlParental::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"No existe el usuario",Toast.LENGTH_SHORT).show()
        }
    }
    private fun saveSession(username: String, pass: String){
        sharedPref.put(Constant.PREF_USERNAME, username)
        sharedPref.put(Constant.PREF_PASSWORD, pass)
    }
}