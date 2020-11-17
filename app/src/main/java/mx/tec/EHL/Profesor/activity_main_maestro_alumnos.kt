package mx.tec.EHL.Profesor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.tec.EHL.Adapter.ControlParentalAdapter
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.PopUpClassAñadirAlumno
import mx.tec.EHL.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NullPointerException

class activity_main_maestro_alumnos : AppCompatActivity() {
    lateinit var activityAdapter: ProfesorAdapter
    val sharedPref by lazy { PreferencesHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_alumnos)


        val popupButton =
            findViewById<ImageView>(R.id.imageView8)
        popupButton.setOnClickListener { v ->
            val popUpClass = PopUpClassAñadirAlumno(this)
            popUpClass.showPopupWindow(v)
        }

        val btnback=findViewById<ImageView>(R.id.btn_backmmalum)
        btnback.setOnClickListener{
            val intent= Intent(this@activity_main_maestro_alumnos, activity_main_maestro::class.java)
            startActivity(intent)
        }





        var queue = Volley.newRequestQueue(this)
        val uri = "http://"+getString(R.string.ip_connection)+"/api/maestroAlumnos/"+sharedPref.getInt(Constant.PREF_ID)
        val listener = Response.Listener<JSONArray> { response ->
            val lista : ArrayList<ArrayList<String>>
            lista = arrayListOf(arrayListOf())
            var elemento : JSONObject
            for(i in 0 until response.length()){
                elemento = response.getJSONObject(i)
                if(i == 0){
                    lista.set(i,
                            arrayListOf(
                                    elemento.getString("nombreGrupo"),
                                    "N/A",
                                    elemento.getString("nombreCompleto"),
                                    "N/A"
                            )
                    )
                }else{
                    lista.add(i,
                            arrayListOf(
                                    elemento.getString("nombreGrupo"),
                                    "N/A",
                                    elemento.getString("nombreCompleto"),
                                    "N/A"
                            )
                    )
                }
            }

            activityAdapter = ProfesorAdapter(
                    this,
                    lista,
                    object : ProfesorAdapter.OnAdapterListener {},
                    R.layout.adapter_activity_maestro_alumnos,
                    R.layout.adapter_activity_maestro_alumnos_asistencia
            )

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