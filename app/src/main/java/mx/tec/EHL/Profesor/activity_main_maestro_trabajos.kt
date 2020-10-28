package mx.tec.EHL.Profesor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.R

class activity_main_maestro_trabajos : AppCompatActivity() {
    lateinit var activityAdapter: ProfesorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_trabajos)
        val btnAddMemorama = findViewById<ImageView>(R.id.btnAddMemorama)
        btnAddMemorama.setOnClickListener {
            val intent= Intent(this@activity_main_maestro_trabajos,activity_main_maestro_creacionmemorama::class.java)
            startActivity(intent)
        }
        val btnAddCQuiz = findViewById<ImageView>(R.id.btnAddCQuiz)
        btnAddCQuiz.setOnClickListener {
            val intent= Intent(this@activity_main_maestro_trabajos,activity_main_maestro_creacioncquiz::class.java)
            startActivity(intent)
        }


        activityAdapter = ProfesorAdapter(this,object: ProfesorAdapter.OnAdapterListener{},R.layout.adapter_activity_maestro_trabajos,R.layout.adapter_activity_maestro_trabajos_actividades)
        val rvPadre = findViewById<RecyclerView>(R.id.rvPadre)
        rvPadre.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = activityAdapter
        }
        activityAdapter = ProfesorAdapter(this,object: ProfesorAdapter.OnAdapterListener{},R.layout.adapter_activity_maestro_trabajos,R.layout.adapter_activity_maestro_trabajos_actividades)
        val rvPadre2 = findViewById<RecyclerView>(R.id.rvPadre2)
        rvPadre2.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = activityAdapter
        }

    }
}