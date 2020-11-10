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
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import mx.tec.EHL.PopUpClassCambiarContraseñaAlumno
import mx.tec.EHL.PopUpClassContacto
import mx.tec.EHL.R

class MainActivity_Alumno_Ajustes : AppCompatActivity() {
    private val STOAGE_PERMISSION_CODE : Int =1000
    val storage = Firebase.storage("gs://my-project-d35b1.appspot.com")
    val storageRef = storage.reference
    val FILE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_alumno_ajustes)

        val popupButton3 =
            findViewById<Button>(R.id.btn_contacto)
        popupButton3.setOnClickListener { v ->
            val popUpClass = PopUpClassContacto()
            popUpClass.showPopupWindow(v)
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

        val popupButton4 =
            findViewById<Button>(R.id.btn_cambiarContrasena)
        popupButton4.setOnClickListener { v ->
            val popUpClass = PopUpClassCambiarContraseñaAlumno()
            popUpClass.showPopupWindow(v)
        }

        val imageView4=findViewById<ImageView>(R.id.btn_back_maa)
        imageView4.setOnClickListener{
            val intent= Intent(this@MainActivity_Alumno_Ajustes,MainActivity_Alumno::class.java)
            startActivity(intent)
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

}