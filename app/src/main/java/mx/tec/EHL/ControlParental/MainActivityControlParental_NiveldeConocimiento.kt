package mx.tec.EHL.ControlParental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.tec.EHL.Adapter.ControlParentalAdapter
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.R
import mx.tec.EHL.Profesor.activity_main_maestro
import org.json.JSONArray
import org.json.JSONObject

class MainActivityControlParental_NiveldeConocimiento : AppCompatActivity() {
    lateinit var activityAdapter: ControlParentalAdapter
    val sharedPref by lazy { PreferencesHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_control_parental__nivelde_conocimiento)


        val btnback = findViewById<ImageView>(R.id.btn_backcpa)
        btnback.setOnClickListener {
            val intent = Intent(this@MainActivityControlParental_NiveldeConocimiento, MainActivityControlParental::class.java)
            startActivity(intent)
        }
        val textNombre = findViewById<TextView>(R.id.textNombre) //nombre del alumno
        val textNivel = findViewById<TextView>(R.id.textNivel) //nombre del alumno


        var queue = Volley.newRequestQueue(this)
        val uri = "http://192.168.50.22:3000/api/controlParentalNivelConocimiento/"+sharedPref.getString(Constant.PREF_USERNAME)+"/"+sharedPref.getString(Constant.PREF_USERNAME)
        val listener = Response.Listener<JSONArray> { response ->
            //var lista = Array(response.length(),{ arrayListOf<String>( ) })
            val lista : ArrayList<ArrayList<String>>
            lista = arrayListOf(arrayListOf())
            var elemento : JSONObject
            for(i in 0 until response.length()){
                elemento = response.getJSONObject(i)
                textNombre.text =  elemento.getString("nombreCompleto")
                textNivel.text =  elemento.getString("nivelConocimiento")
            }
        }
        val error = Response.ErrorListener { error ->
            Log.e("MENSAJE_ERROR", error.message!!)
        }
        val request = JsonArrayRequest(Request.Method.GET, uri, null, listener, error)
        queue.add(request)







    }


}