package mx.tec.EHL

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        val listencontact = findViewById<TextView>(R.id.txtContact)

        var queue = Volley.newRequestQueue(this)

        listencontact.setOnClickListener {
            val view = LayoutInflater.from(this).inflate(R.layout.activity_popup_contacto, null)

            val builder = AlertDialog.Builder(this)
                    .setView(view)

            val dialog = builder.show()
        }


        val buttonIngresar=findViewById<Button>(R.id.buttonIngresar)
        buttonIngresar.setOnClickListener{
            println(getString(R.string.ip_connection))
            val uri = "http://"+getString(R.string.ip_connection)+"/api/inicioSesion/"+editTextTextPersonName.text.toString()+"/"+editTextTextPassword.text.toString()
            //val uri = "http://+getString(R.string.ip_connection)+/api/inicioSesion/c/c"
            val listener = Response.Listener<JSONArray> {response ->
                Log.e("MENSAJE_EXITO",response.toString())
                try{

                    InicioSesion(response.getJSONObject(0).getString("perfil"),response.getJSONObject(0).getInt("id"))
                }
                catch (e : Exception){
                    Toast.makeText(this,"No existe el usuario",Toast.LENGTH_SHORT).show()
                }
            }
            val error = Response.ErrorListener {error ->
//                Log.e("MENSAJE_ERROR",error.message!!)
            }
            val request = JsonArrayRequest(Request.Method.GET, uri,null, listener,error)
            queue.add(request)

        }
    }


    fun InicioSesion(tipoUsuario : String, id : Int){
        if(tipoUsuario == "Maestro"){
            saveSession(id, editTextTextPassword?.text.toString())
            val intent= Intent(this@MainActivity,activity_main_maestro::class.java)
            startActivity(intent)
        }
        else if(tipoUsuario == "Alumno"){
            saveSession(id, editTextTextPassword?.text.toString())
            val intent= Intent(this@MainActivity,MainActivity_Alumno::class.java)
            startActivity(intent)
        }
        else if(tipoUsuario == "ControlParental"){
            saveSession(id, editTextTextPassword?.text.toString())
            val intent= Intent(this@MainActivity,MainActivityControlParental::class.java)
            startActivity(intent)
        }
    }


    private fun saveSession(id: Int,pass: String){
        sharedPref.put(Constant.PREF_ID, id)
        sharedPref.put(Constant.PREF_PASSWORD, pass)
    }
}