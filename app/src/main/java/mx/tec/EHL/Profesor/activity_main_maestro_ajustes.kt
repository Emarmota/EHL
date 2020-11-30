package mx.tec.EHL.Profesor

import android.Manifest
import android.app.Activity
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
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main_maestro_ajustes.*
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.MainActivity
import mx.tec.EHL.PopUpClassCambiarContraseñaMaestro
import mx.tec.EHL.PopUpClassContacto
import mx.tec.EHL.R
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.NullPointerException
import java.net.URLEncoder

class activity_main_maestro_ajustes : AppCompatActivity() {

    private val STOAGE_PERMISSION_CODE : Int =1000
    val FILE = 1
    val sharedPref by lazy { PreferencesHelper(this) }

    private var imageData : ByteArray?=null
    val storage = Firebase.storage("gs://my-project-d35b1.appspot.com")
    val storageRef = storage.reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_ajustes)

        FirebaseApp.initializeApp(this)

        btn_cambiarimagen.setOnClickListener {
            launchGallery()
        }

        val btn_ayuda = findViewById<Button>(R.id.btn_ayuda)
        btn_ayuda.setOnClickListener{
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

        val popupButton =
            findViewById<Button>(R.id.btn_cambiarContrasena)
        popupButton.setOnClickListener { v ->
            val popUpClass = PopUpClassCambiarContraseñaMaestro(this)
            popUpClass.showPopupWindow(v)
        }

        val popupButton2 =
            findViewById<Button>(R.id.btn_contacto)
        popupButton2.setOnClickListener { v ->
            val popUpClass = PopUpClassContacto()
            popUpClass.showPopupWindow(v)
        }

        val btnback=findViewById<ImageView>(R.id.btn_backmma)
        btnback.setOnClickListener{
            val intent= Intent(this@activity_main_maestro_ajustes, activity_main_maestro::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            val activity = activity_main_maestro::class.java as Activity
            activity.recreate()
        }
    }

    private fun launchGallery() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 99)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 99) {
            val uri = data?.data
            if (uri != null) {
                img_master.setImageURI(uri)
                createImageData(uri)
                saveImageData()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
    }

    private fun saveImageData(){
        Toast.makeText(this, "imagen guardada", Toast.LENGTH_LONG).show()
        var pathString = "maestro/maestro"+sharedPref.getInt(Constant.PREF_ID)+".jpg"
        var uploadTask = storageRef.child(pathString).putBytes(imageData!!)
        uploadTask.addOnSuccessListener { taskSnapshot ->

            var queue = Volley.newRequestQueue(this)
            val uri = "http://"+getString(R.string.ip_connection)+"/api/actualizarMaestroFotoAvatar/"+sharedPref.getInt(Constant.PREF_ID)+"/"+URLEncoder.encode(pathString,"utf-8")
            val listener = Response.Listener<JSONArray> { response ->
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
        uploadTask.addOnFailureListener{
            Log.i("valio", it.message!!)
        }
        val ref = storageRef.child("carpeta/maestro1.jpg")
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.i("todo chido", task.result.toString())
            } else {
                // Handle failures
                // ...
            }
        }
    }

    fun startdowloadFile(){
        val storageRef = storage.reference
        var uri = ""
        storageRef.child("files/DocumentoDeAyuda.pdf").downloadUrl.addOnSuccessListener {
            uri = it.toString()
            downloadFile(uri) }
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
        request.setTitle("Ayuda EHL")
        request.setDescription("The file is downloading...")

        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}")


        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        Toast.makeText(this, "Archivo descargado", Toast.LENGTH_LONG).show()

    }
    //FIN
}