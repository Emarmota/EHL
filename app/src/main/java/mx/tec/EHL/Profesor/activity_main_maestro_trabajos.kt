package mx.tec.EHL.Profesor

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.PopUpClassAñadirGuias
import mx.tec.EHL.PopUpClassCambiarContraseñaMaestro
import mx.tec.EHL.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NullPointerException
import java.util.*

class activity_main_maestro_trabajos : AppCompatActivity() {
    lateinit var activityAdapter: ProfesorAdapter
    val sharedPref by lazy { PreferencesHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_trabajos)

            val popupButton =
                findViewById<ImageView>(R.id.btnAddGuia)
            popupButton.setOnClickListener { v ->
                val popUpClass = PopUpClassAñadirGuias(this)
                popUpClass.showPopupWindow(v)
            }
            val btnAddCQuiz = findViewById<ImageView>(R.id.btnAddCQuiz)
            btnAddCQuiz.setOnClickListener {
                val intent = Intent(
                    this@activity_main_maestro_trabajos,
                    activity_main_maestro_creacioncquiz::class.java
                )
                startActivity(intent)
            }

            val btnback = findViewById<ImageView>(R.id.btn_backmmt)
            btnback.setOnClickListener {
                val intent =
                    Intent(this@activity_main_maestro_trabajos, activity_main_maestro::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }


            /*

        activityAdapter = ProfesorAdapter(this,null,object: ProfesorAdapter.OnAdapterListener{},R.layout.adapter_activity_maestro_trabajos,null)
        val rvPadre = findViewById<RecyclerView>(R.id.rvPadre)
        rvPadre.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = activityAdapter
        }
         */

            var queue = Volley.newRequestQueue(this)
            val uri =
                "http://" + getString(R.string.ip_connection) + "/api/maestroTrabajos/" + sharedPref.getInt(
                    Constant.PREF_ID
                )
            val listener = Response.Listener<JSONArray> { response ->
                val lista: ArrayList<ArrayList<String>>
                lista = arrayListOf(arrayListOf())
                var elemento: JSONObject
                for (i in 0 until response.length()) {
                    elemento = response.getJSONObject(i)
                    if (i == 0) {
                        lista.set(
                            i,
                            arrayListOf(
                                elemento.getString("nombreActividad"),
                                "N/A"
                            )
                        )
                    } else {
                        lista.add(
                            i,
                            arrayListOf(
                                elemento.getString("nombreGrupo"),
                                "N/A"
                            )
                        )
                    }
                }
                activityAdapter = ProfesorAdapter(
                    this,
                    lista,
                    object : ProfesorAdapter.OnAdapterListener {},
                    R.layout.adapter_activity_maestro_trabajos,
                    null
                )
                val rvPadre2 = findViewById<RecyclerView>(R.id.rvPadre2)
                rvPadre2.apply {
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = activityAdapter
                }
            }
            val error = Response.ErrorListener { error ->
                try {
                    Log.e("MENSAJE_ERROR", error.message!!)
                } catch (e: NullPointerException) {
                }

            }
            val request = JsonArrayRequest(Request.Method.GET, uri, null, listener, error)
            queue.add(request)

        }

}
