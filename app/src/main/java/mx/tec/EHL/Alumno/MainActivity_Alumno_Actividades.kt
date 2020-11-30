package mx.tec.EHL.Alumno

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import mx.tec.EHL.Adapter.AlumnoAdapter
import mx.tec.EHL.Adapter.ControlParentalAdapter
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.NullPointerException

class MainActivity_Alumno_Actividades : AppCompatActivity(), AlumnoAdapter.OnAdapterListener {
    val STOAGE_PERMISSION_CODE : Int =1000
    val storage = Firebase.storage("gs://my-project-d35b1.appspot.com")
    val storageRef = storage.reference
    val FILE = 1
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
        val uri = "http://"+getString(R.string.ip_connection)+"/api/alumnoActividadesCQuiz/"+sharedPref.getInt(Constant.PREF_ID)
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
                            elemento.getString("calificacion"),
                            elemento.getString("tipo")
                        )
                    )
                }else{
                    lista.add(i,
                        arrayListOf(
                            elemento.getString("nombreActividad"),
                            elemento.getString("calificacion"),
                            elemento.getString("tipo")
                        )
                    )
                }
            }
            activityAdapter = AlumnoAdapter(this,lista,object: AlumnoAdapter.OnAdapterListener{
                override fun OnClick(button: ImageView, nameActivity: String, tipo: String) {

                }
            },R.layout.adapter_activity_alumno,null)

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

        var queue2 = Volley.newRequestQueue(this)
        val uri2= "http://"+getString(R.string.ip_connection)+"/api/alumnoActividadesGuias/"+sharedPref.getInt(Constant.PREF_ID)
        val listener2 = Response.Listener<JSONArray> { response ->
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
                            elemento.getString("calificacion"),
                            elemento.getString("tipo")
                        )
                    )
                }else{
                    lista.add(i,
                        arrayListOf(
                            elemento.getString("nombreActividad"),
                            elemento.getString("calificacion"),
                            elemento.getString("tipo")
                        )
                    )
                }
            }
            activityAdapter = AlumnoAdapter(this,lista,object: AlumnoAdapter.OnAdapterListener{
                override fun OnClick(button: ImageView, nameActivity: String, tipo: String) {
                }
            },R.layout.adapter_activity_alumno,null)

            val list_Activity_alumno = findViewById<RecyclerView>(R.id.rvPadre2)
            list_Activity_alumno.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = activityAdapter
            }
        }
        val error2 = Response.ErrorListener { error ->
            try {
                Log.e("MENSAJE_ERROR", error.message!!)
            }
            catch (e: NullPointerException){}
        }
        val request2 = JsonArrayRequest(Request.Method.GET,uri2,null,listener2, error2)
        queue.add(request2)



    }
    var urlGuia = ""
    override fun OnClick(button: ImageView, nameActivity: String, tipo: String) {
        button.setOnClickListener{
            if(tipo == "CQuiz"){
                println(tipo)
                val intent= Intent(this@MainActivity_Alumno_Actividades, MainActivity_Alumno_CQuiz::class.java)
                intent.putExtra("nameActivity", nameActivity)
                startActivity(intent)
            }
            else{
                println(tipo)
                urlGuia = tipo
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STOAGE_PERMISSION_CODE)
                    }
                    else{
                        startdowloadFile()
                    }
                }
                else{
                    startdowloadFile()
                }
            }
        }
    }
    fun startdowloadFile(){
        val storageRef = storage.reference
        var uri = ""
        storageRef.child("files/"+urlGuia).downloadUrl.addOnSuccessListener {
            uri = it.toString()
            downloadFile(uri)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            STOAGE_PERMISSION_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    startdowloadFile()
                }
                else{
                    Toast.makeText(this, "No hay permisos", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun downloadFile(url: String){
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes((DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE))
        request.setTitle(urlGuia)
        request.setDescription("The file is downloading...")

        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}")


        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        Toast.makeText(this, "Archivo descargado", Toast.LENGTH_LONG).show()

    }


}

