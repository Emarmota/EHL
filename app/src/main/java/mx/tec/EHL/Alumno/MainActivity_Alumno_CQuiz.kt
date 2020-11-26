package mx.tec.EHL.Alumno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.tec.EHL.Adapter.AlumnoAdapter
import mx.tec.EHL.Adapter.AlumnoAdapterChild
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.DataBase.Tablas.Alumno
import mx.tec.EHL.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NullPointerException
import java.net.URLEncoder

class MainActivity_Alumno_CQuiz : AppCompatActivity(),  AlumnoAdapter.OnAdapterListener, AlumnoAdapterChild.OnAdapterListener {
    lateinit var activityAdapter: AlumnoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_alumno_c_quiz)

        val nameActivity = intent.getStringExtra("nameActivity");
        var queue = Volley.newRequestQueue(this)
        val uri = "http://"+getString(R.string.ip_connection)+"/api/alumnoActividadResolver/"+ URLEncoder.encode( nameActivity, "utf-8")
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
                            elemento.getString("id")

                        )
                    )
                }else{
                    lista.add(i,
                        arrayListOf(
                            elemento.getString("pregunta"),
                            "N/A",
                            elemento.getString("respuesta"),
                            elemento.getString("id")

                        )
                    )
                }
            }
            activityAdapter = AlumnoAdapter(this,lista,object: AlumnoAdapter.OnAdapterListener{
                override fun OnClick(button: ImageView, nameActivity: String) {
                }
            },R.layout.adapter_activity_alumno_cquiz_title,R.layout.adapter_activity_alumno_cquiz)

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

    override fun OnClick(button: ImageView, nameActivity: String) {

    }

    override fun RespuestaSeleccionada(position: Int, listaCheckBox: ArrayList<CheckBox>, seleccionCheckBox: ArrayList<CheckBox>) {
        println("LISTA TOTAL DE BOTONES"+listaCheckBox.size)
        println("POSITION"+position)
        println("BOTON SELECCIONADO"+seleccionCheckBox.toString())
        for(i in 0..listaCheckBox.size-1){
            println("NOMBRE DE BOTONES"+listaCheckBox[i].toString())
            if(seleccionCheckBox[0].toString()  == listaCheckBox[i].toString() && position == 0){
                listaCheckBox[i+1].isChecked = false
            }
            else if(seleccionCheckBox[0].toString()  == listaCheckBox[i].toString() && position == 1){
                listaCheckBox[i-1].isChecked = false
            }
        }


    }
}