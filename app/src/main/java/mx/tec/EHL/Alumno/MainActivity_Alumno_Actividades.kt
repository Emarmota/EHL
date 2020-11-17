package mx.tec.EHL.Alumno

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
import mx.tec.EHL.Adapter.AlumnoAdapter
import mx.tec.EHL.Adapter.ControlParentalAdapter
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NullPointerException

class MainActivity_Alumno_Actividades : AppCompatActivity() {

    lateinit var activityAdapter: AlumnoAdapter
    val sharedPref by lazy { PreferencesHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_alumno_actividades)

        val imageView4=findViewById<ImageView>(R.id.btn_backcpa)
        imageView4.setOnClickListener{
            val intent= Intent(this@MainActivity_Alumno_Actividades,MainActivity_Alumno::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val textNombre = findViewById<TextView>(R.id.textNombre) //nombre del alumno

        var queue = Volley.newRequestQueue(this)
        val uri = "http://"+getString(R.string.ip_connection)+"/api/alumnoActividades/"+sharedPref.getInt(Constant.PREF_ID)
        val listener = Response.Listener<JSONArray> { response ->
            //var lista = Array(response.length(),{ arrayListOf<String>( ) })
            val lista : ArrayList<ArrayList<String>>
            lista = arrayListOf( arrayListOf())
            var elemento : JSONObject
            for(i in 0 until response.length()){
                elemento = response.getJSONObject(i)
                textNombre.text =  elemento.getString("nombreCompleto")
                if(i == 0){
                    lista.set(i,
                            arrayListOf(
                                    elemento.getString("nombreActividad"),
                                    elemento.getString("calificacion")
                            )
                    )
                }else{
                    lista.add(i,
                            arrayListOf(
                                    elemento.getString("nombreActividad"),
                                    elemento.getString("calificacion")
                            )
                    )
                }
            }
            activityAdapter = AlumnoAdapter(this,lista,object: AlumnoAdapter.OnAdapterListener{},R.layout.adapter_activity_alumno,null)

            val list_Activity_alumno = findViewById<RecyclerView>(R.id.rvPadre)
            list_Activity_alumno.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = activityAdapter
            }
        }
        val error = Response.ErrorListener { error ->
            try {
                Log.e("MENSAJE_ERROR", error.message!!)
            }
            catch (e: NullPointerException){}
        }
        val request = JsonArrayRequest(Request.Method.GET,uri,null,listener, error)
        queue.add(request)


    }


}

