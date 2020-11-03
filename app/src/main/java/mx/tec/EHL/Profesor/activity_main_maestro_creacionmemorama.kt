package mx.tec.EHL.Profesor

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main_maestro_creacionmemorama.*
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.PopUpClassAñadirPar
import mx.tec.EHL.R

class activity_main_maestro_creacionmemorama : AppCompatActivity() {
    lateinit var activityAdapter: ProfesorAdapter

    private val REQUEST_CAMERA = 1001
    private val REQUEST_GALLERY = 1002
    var foto: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_creacionmemorama)
        abreCamara_Click()
        abreGaleria_Click()
/*
        activityAdapter = ProfesorAdapter(this,object: ProfesorAdapter.OnAdapterListener{},R.layout.adapter_activity_maestro_trabajos_crearmemorama,null)
        val rvPadre = findViewById<RecyclerView>(R.id.rvPadre)
        rvPadre.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = activityAdapter
        }
        */

        val popupButton7 =
            findViewById<ImageView>(R.id.imageView16)
        popupButton7.setOnClickListener { v ->
            val popUpClass = PopUpClassAñadirPar()
            popUpClass.showPopupWindow(v)
        }

        val btnback=findViewById<ImageView>(R.id.btn_backcreacionmemo)
        btnback.setOnClickListener{
            val intent= Intent(this@activity_main_maestro_creacionmemorama, activity_main_maestro_trabajos::class.java)
            startActivity(intent)
        }
    }
    private fun abreGaleria_Click() {
        btnAbrirGaleria.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                {
                    val permisoArchivos = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permisoArchivos, REQUEST_GALLERY)
                } else {
                    muestraGaleria()
                }
            } else {
                muestraGaleria()
            }
        }
    }

    private fun abreCamara_Click() {
        //val  textViewSacarFoto= findViewById<TextView>(R.id.textViewSacarFoto)
        btnAbrirCamara.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    val permisosCamara = arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestPermissions(permisosCamara, REQUEST_CAMERA)
                } else {
                    abreCamara()
                }
            } else {
                abreCamara()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults)
        when(requestCode){
            REQUEST_CAMERA -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    abreCamara()
                else
                    Toast.makeText(applicationContext,"No puedes acceder a la camara", Toast.LENGTH_LONG).show()
            }
            REQUEST_GALLERY -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    muestraGaleria()
                else
                    Toast.makeText(applicationContext,"No puedes acceder a tus imágenes", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA) {
            imageView28.setImageURI(foto)
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY) {
            imageView28.setImageURI(data?.data)
        }
    }

    private fun abreCamara(){
        val value= ContentValues()
        value.put(MediaStore.Images.Media.TITLE,"Nueva imagen")
        foto= contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value)
        val camaraIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT,foto)
        startActivityForResult(camaraIntent,REQUEST_CAMERA)
    }

    private fun muestraGaleria(){
        val intentGaleria= Intent(Intent.ACTION_PICK)
        intentGaleria.type="image/*"
        startActivityForResult(intentGaleria,REQUEST_GALLERY)
    }
}
