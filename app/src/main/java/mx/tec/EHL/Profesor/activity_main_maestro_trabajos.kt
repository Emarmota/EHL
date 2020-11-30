package mx.tec.EHL.Profesor

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import mx.tec.EHL.Adapter.ProfesorAdapterChild
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.PopUpClassAñadirCQuiz
import mx.tec.EHL.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NullPointerException
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList

class activity_main_maestro_trabajos : AppCompatActivity(), ProfesorAdapterChild.OnAdapterListener {
    lateinit var activityAdapter: ProfesorAdapter
    val sharedPref by lazy { PreferencesHelper(this) }
    //PopupWindow display method

    private val STOAGE_PERMISSION_CODE : Int =1000
    val FILE = 1
    var save = false
    private var imageData : ByteArray?=null
    val storage = Firebase.storage("gs://my-project-d35b1.appspot.com")
    val storageRef = storage.reference
    var nombreGuia : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_trabajos)

            val btnAddGuia = findViewById<ImageView>(R.id.btnAddGuia)
            btnAddGuia.setOnClickListener {
                val view = LayoutInflater.from(this).inflate(R.layout.a_alertdialog_guia, null)
                val grupoSpinner = view.findViewById<Spinner>(R.id.spinner_grupoguia)
                var queue = Volley.newRequestQueue(this)
                val uri = "http://"+getString(R.string.ip_connection)+"/api/alumnosGruposMaestro/"+sharedPref.getInt(Constant.PREF_ID)
                val listener = Response.Listener<JSONArray> { response ->
                    val lista : List<String>
                    lista = arrayListOf()
                    var elemento : JSONObject
                    for(i in 0 until response.length()){
                        elemento = response.getJSONObject(i)
                        lista.add(i,
                                elemento.getString("nombreGrupo")
                        )
                    }
                    lista.add("Añadir grupo")
                    SetSpinnerValues(view, grupoSpinner, lista)
                }
                val error = Response.ErrorListener { error ->
                    try {
                        Log.e("MENSAJE_ERROR", error.message!!)
                    }
                    catch (e: NullPointerException){}
                }
                val request = JsonArrayRequest(Request.Method.GET, uri, null, listener, error)
                queue.add(request)


                val builder = AlertDialog.Builder(this)
                        .setView(view)

                val dialog = builder.show()
                nombreGuia = view.findViewById(R.id.txt_NombreGuia)
                dialog.btnagregarguia.setOnClickListener {
                    openContent()
                }

                view.btnaceptarguia.setOnClickListener {
                    uploadFile(fileUri!!)

                    dialog.dismiss()

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
                                        elemento.getString("id")
                                )
                        )
                    } else {
                        lista.add(
                                i,
                                arrayListOf(
                                        elemento.getString("nombreActividad"),
                                        elemento.getString("id")
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
                rvPadre

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
                                elemento.getString("id")

                            )
                        )
                    } else {
                        lista.add(
                            i,
                            arrayListOf(
                                elemento.getString("nombreActividad"),
                                elemento.getString("id")

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
    var fileUri : Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == FILE){
            try{
                fileUri = data?.data!!
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
            val activity = this@activity_main_maestro_trabajos as Activity
            activity.recreate()
            save=false
            var queue = Volley.newRequestQueue(this)
            var uri = ""
            if(anadirConGrupo){
                grupoAlumno = textoEmergente!!.text.toString()
                uri = "http://"+getString(R.string.ip_connection)+"/api/maestroAgregarGuiaConGrupo/"+sharedPref.getInt(Constant.PREF_ID)+"/"+ URLEncoder.encode( grupoAlumno, "utf-8")+"/"+ URLEncoder.encode( nombreGuia!!.text.toString(), "utf-8")+"/"+URLEncoder.encode( fileName, "utf-8")
            }
            else{
                uri = "http://"+getString(R.string.ip_connection)+"/api/maestroAgregarGuiaSinGrupo/"+URLEncoder.encode( grupoAlumno, "utf-8")+"/"+ URLEncoder.encode( nombreGuia!!.text.toString(), "utf-8")+"/"+URLEncoder.encode( fileName, "utf-8")
            }

            val listener = Response.Listener<JSONArray> { response ->
            }
            val error = Response.ErrorListener { error ->
                try {
                    Log.e("MENSAJE_ERROR", error.message!!)
                }
                catch (e: NullPointerException){}
            }
            val request = JsonArrayRequest(Request.Method.GET, uri, null, listener, error)
            queue.add(request)

        }
    }



    var grupoAlumno = ""
    var textoEmergente : EditText? = null
    var anadirConGrupo = false
    private fun SetSpinnerValues(popupView : View, grupoSpinner: Spinner, lista : List<String>){
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista )
        grupoSpinner.adapter = adapter

        grupoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long)
            {
                val linearLayout = popupView.findViewById<LinearLayout>(R.id.container)

                if(parent!!.getItemAtPosition(position).toString() == "Añadir grupo" ){
                    // Create TextVieew
                    val textView = TextView(view.context)

                    textView.setText("Nombre del grupo nuevo")
                    textView.setTextColor(Color.parseColor("#000000"))
                    textView.textSize = 18f
                    // Create EditText
                    textoEmergente = EditText(view.context)
                    val filterArray = arrayOfNulls<InputFilter>(1)
                    filterArray[0] = InputFilter.LengthFilter(16)
                    textoEmergente!!.setFilters(filterArray)

                    textoEmergente!!.layoutParams = ViewGroup.LayoutParams(800,100)
                    textoEmergente!!.setPadding(20, 20, 20, 20)
                    textoEmergente!!.setTextColor(Color.parseColor("#000000"))
                    // Add EditText and textView to LinearLayout
                    linearLayout?.addView(textView)
                    linearLayout?.addView(textoEmergente)
                    grupoAlumno = textoEmergente!!.text.toString()
                    anadirConGrupo = true
                }else{
                    linearLayout?.removeAllViews()
                    grupoAlumno = parent!!.getItemAtPosition(position).toString()
                    println(grupoAlumno)
                    anadirConGrupo = false
                }
            }
        }
    }

    override fun ListaAlumnosFalta(listaAlumnosFalta: ArrayList<Int>, listaGrupos: ArrayList<Int>, checkBox: ArrayList<CheckBox>) {

    }


}
