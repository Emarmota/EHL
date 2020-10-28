package mx.tec.EHL.Alumno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.Adapter.AlumnoAdapter
import mx.tec.EHL.R

class MainActivity_Alumno_Actividades : AppCompatActivity() {

    lateinit var activityAdapter: AlumnoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_alumno_actividades)

        val imageView4=findViewById<ImageView>(R.id.imageView4)
        imageView4.setOnClickListener{
            val intent= Intent(this@MainActivity_Alumno_Actividades,MainActivity_Alumno::class.java)
            startActivity(intent)
        }

        activityAdapter = AlumnoAdapter(this,object: AlumnoAdapter.OnAdapterListener{},R.layout.adapter_activity_alumno,null)
        setupRecyclerView()
    }

    private fun setupRecyclerView () {
        val list_Activity_alumno = findViewById<RecyclerView>(R.id.rvPadre)
        list_Activity_alumno.apply {
           layoutManager = LinearLayoutManager(applicationContext)
           adapter = activityAdapter
        }
    }

}

