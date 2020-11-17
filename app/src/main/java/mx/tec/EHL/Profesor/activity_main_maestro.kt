package mx.tec.EHL.Profesor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_maestro.*
import mx.tec.EHL.ControlParental.MainActivityControlParental_Asistencia
import mx.tec.EHL.ControlParental.MainActivityControlParental_NiveldeConocimiento
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.MainActivity
import mx.tec.EHL.R

class activity_main_maestro : AppCompatActivity() {
    private var imageData : ByteArray?=null
    val storage = Firebase.storage("gs://my-project-d35b1.appspot.com")
    val storageRef = storage.reference

    lateinit var sharedpref: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro)

        sharedpref = PreferencesHelper(this)
        loadWithGlide()

        val btn_alumnos=findViewById<Button>(R.id.btn_alumnos)
        btn_alumnos.setOnClickListener{
            val intent= Intent(this@activity_main_maestro, activity_main_maestro_alumnos::class.java)
            startActivity(intent)
        }

        val btn_trabajos=findViewById<Button>(R.id.btn_trabajos)
        btn_trabajos.setOnClickListener{
            val intent= Intent(this@activity_main_maestro, activity_main_maestro_trabajos::class.java)
            startActivity(intent)
        }

        val btn_ajustes=findViewById<Button>(R.id.btn_ajustes)
        btn_ajustes.setOnClickListener{
            val intent= Intent(this@activity_main_maestro, activity_main_maestro_ajustes::class.java)
            startActivity(intent)
        }

        val btnback=findViewById<ImageView>(R.id.btn_backmm)
        btnback.setOnClickListener{
            val intent= Intent(this@activity_main_maestro, MainActivity::class.java)
            sharedpref.clear()
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    fun loadWithGlide() {
        var uriapp:String=""
        val storageReference = storage.reference.child("carpeta/maestro1.jpg").downloadUrl.addOnSuccessListener{
            // Got the download URL for 'users/me/profile.png'
            uriapp = it.toString()
            takeUri(uriapp)
        }
    }

    fun takeUri(valor: String)
    {
        Glide.with(this).load(valor).into(img_master_menu);
    }
}