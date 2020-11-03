package mx.tec.EHL.Profesor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main_maestro_ajustes.*
import mx.tec.EHL.MainActivity
import mx.tec.EHL.PopUpClassCambiarContraseñaMaestro
import mx.tec.EHL.PopUpClassContacto
import mx.tec.EHL.R
import java.io.IOException

class activity_main_maestro_ajustes : AppCompatActivity() {

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

        val popupButton =
            findViewById<Button>(R.id.btn_cambiarContrasena)
        popupButton.setOnClickListener { v ->
            val popUpClass = PopUpClassCambiarContraseñaMaestro()
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
            startActivity(intent)
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
        var uploadTask = storageRef.child("carpeta/maestro1.jpg").putBytes(imageData!!)
        uploadTask.addOnSuccessListener { taskSnapshot ->

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
    //FIN
}