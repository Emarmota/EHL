package mx.tec.EHL.Profesor

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.a_alertdialog_guia.*
import kotlinx.android.synthetic.main.a_alertdialog_guia.view.*
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.PopUpClassAñadirCQuiz
import mx.tec.EHL.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NullPointerException
import java.util.*

class activity_main_maestro_trabajos : AppCompatActivity() {
    lateinit var activityAdapter: ProfesorAdapter
    val sharedPref by lazy { PreferencesHelper(this) }
    //PopupWindow display method

    private val STOAGE_PERMISSION_CODE : Int =1000
    val FILE = 1
    var save = false
    private var imageData : ByteArray?=null
    val storage = Firebase.storage("gs://my-project-d35b1.appspot.com")
    val storageRef = storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_trabajos)

            val btnAddGuia = findViewById<ImageView>(R.id.btnAddGuia)
            btnAddGuia.setOnClickListener {

                val view = LayoutInflater.from(this).inflate(R.layout.a_alertdialog_guia, null)

                val builder = AlertDialog.Builder(this)
                        .setView(view)

                val dialog = builder.show()

                dialog.btnagregarguia.setOnClickListener {
                    openContent()
                }

                view.btnaceptarguia.setOnClickListener {
                    save=true
                    //dialog.dismiss()

                }
            }

            val btnAddCQuiz = findViewById<ImageView>(R.id.btnAddCQuiz)
            btnAddCQuiz.setOnClickListener {v ->
                val a = PopUpClassAñadirCQuiz(this)
                a.showPopupWindow(v)
            }

            val btnback = findViewById<ImageView>(R.id.btn_backmmt)
            btnback.setOnClickListener {
                val intent =
                    Intent(this@activity_main_maestro_trabajos, activity_main_maestro::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }

            var queue1 = Volley.newRequestQueue(this)
            val uri1 =
                    "http://" + getString(R.string.ip_connection) + "/api/maestroTrabajosGuias/" + sharedPref.getInt(
                            Constant.PREF_ID
                    )
            val listener1 = Response.Listener<JSONArray> { response ->
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
                                        elemento.getString("nombreActividad"),
                                        "N/A"
                                )
                        )
                    }
                }
                activityAdapter = ProfesorAdapter(this,lista,object: ProfesorAdapter.OnAdapterListener{},R.layout.adapter_activity_maestro_trabajos,null)
                val rvPadre = findViewById<RecyclerView>(R.id.rvPadre)
                rvPadre.apply {
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = activityAdapter
                }

            }
            val error1 = Response.ErrorListener { error ->
                try {
                    Log.e("MENSAJE_ERROR", error.message!!)
                } catch (e: NullPointerException) {
                }

            }
            val request1 = JsonArrayRequest(Request.Method.GET, uri1, null, listener1, error1)
            queue1.add(request1)


            var queue = Volley.newRequestQueue(this)
            val uri =
                "http://" + getString(R.string.ip_connection) + "/api/maestroTrabajosCQuiz/" + sharedPref.getInt(
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
                                elemento.getString("nombreActividad"),
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


    fun openContent(){
        var intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, FILE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == FILE){
            try{
                var fileUri = data?.data!!
                uploadFile(fileUri)
            }
            catch (e : NullPointerException){
                Toast.makeText(this,"No selecciono ningun archivo",Toast.LENGTH_SHORT).show()
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun uploadFile(fileUri: Uri){
        var metaCursor = this.contentResolver.query(fileUri, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME), null, null, null)!!
        metaCursor.moveToFirst()
        var fileName = metaCursor.getString(0)
        metaCursor.close()

        var storageRef = FirebaseStorage.getInstance().reference.child("files").child(fileName)
        storageRef.putFile(fileUri).addOnSuccessListener {
            Toast.makeText(this, "Se ha subido el archivo correctamente", Toast.LENGTH_LONG).show()
            save=false
            nombrartexto(fileName)
        }
    }

    fun nombrartexto(nombre: String)
    {
        txtarchivo.text= nombre
    }
}
