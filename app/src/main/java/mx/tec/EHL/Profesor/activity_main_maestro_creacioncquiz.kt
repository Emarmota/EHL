package mx.tec.EHL.Profesor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.Adapter.ProfesorAdapterChild
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.PopUpClassAñadirPregunta
import mx.tec.EHL.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NullPointerException

class activity_main_maestro_creacioncquiz : AppCompatActivity(), ProfesorAdapterChild.OnAdapterListener {
    lateinit var activityAdapter: ProfesorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_creacioncquiz)
        val nombreActividad = intent.getStringExtra("nombreActividad")



        val popupButton10 =
            findViewById<ImageView>(R.id.imageView19)
        popupButton10.setOnClickListener { v ->
            val popUpClass = PopUpClassAñadirPregunta(this)
            popUpClass.showPopupWindow(v,nombreActividad)
        }
        val btnAceptar = findViewById<Button>(R.id.button3)
        btnAceptar.setOnClickListener {
            val intent = Intent(this@activity_main_maestro_creacioncquiz, activity_main_maestro_trabajos::class.java)
            startActivity(intent)
        }



        var queue = Volley.newRequestQueue(this)
        val uri = "http://"+getString(R.string.ip_connection)+"/api/maestroVerCQuiz/"+nombreActividad
        val listener = Response.Listener<JSONArray> { response ->
            val lista : ArrayList<ArrayList<String>>
            println(response.toString())

            lista = arrayListOf(arrayListOf())
            var elemento : JSONObject
            for(i in 0 until response.length()){
                elemento = response.getJSONObject(i)
                if(i == 0){
                    lista.set(i,
                        arrayListOf(
                            elemento.getString("pregunta"),
                            "N/A",
                            elemento.getString("respuesta"),
                            "1"

                        )
                    )
                }else{
                    lista.add(i,
                        arrayListOf(
                            elemento.getString("pregunta"),
                            "N/A",
                            elemento.getString("respuesta"),
                            "2"

                        )
                    )
                }
            }
            activityAdapter = ProfesorAdapter(this,lista, object : ProfesorAdapter.OnAdapterListener {},R.layout.adapter_activity_maestro_trabajos_edicioncquiz,R.layout.adapter_activity_maestro_trabajos_edicioncquiz_respuestas)
            val rvPadre = findViewById<RecyclerView>(R.id.rvPadre)
            rvPadre.apply {
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

    override fun ListaAlumnosFalta(listaAlumnosFalta: ArrayList<Int>, listaGrupos: ArrayList<Int>, checkBox: ArrayList<CheckBox>) {

    }
}