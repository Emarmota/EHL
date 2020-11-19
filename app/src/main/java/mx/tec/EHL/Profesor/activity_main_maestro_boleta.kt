package mx.tec.EHL.Profesor

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
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.Adapter.ProfesorAdapterChild
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NullPointerException

class activity_main_maestro_boleta : AppCompatActivity() {
    lateinit var activityAdapter: ProfesorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_boleta)
        val idAlumno = intent.getIntExtra("idAlumno",-1)
        val idGrupo = intent.getIntExtra("idGrupo",-1)

        val btnback=findViewById<ImageView>(R.id.btn_backmmb)
        btnback.setOnClickListener{
            val intent= Intent(this@activity_main_maestro_boleta, activity_main_maestro::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }


        var queue = Volley.newRequestQueue(this)
        val uri = "http://"+getString(R.string.ip_connection)+"/api/maestroAlumnosBoleta/"+idAlumno+"/"+idGrupo
        val listener = Response.Listener<JSONArray> { response ->
            //var lista = Array(response.length(),{ arrayListOf<String>( ) })
            val lista : ArrayList<ArrayList<String>>
            lista = arrayListOf( arrayListOf())
            var elemento : JSONObject
            for(i in 0 until response.length()){
                elemento = response.getJSONObject(i)
                val textNombre = findViewById<TextView>(R.id.textNombre)
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
            activityAdapter = ProfesorAdapter(this,lista,object: ProfesorAdapter.OnAdapterListener{},R.layout.adapter_activity_maestro_boleta_calificaciones,null)

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