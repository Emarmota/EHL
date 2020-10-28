package mx.tec.EHL.Profesor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.R

class activity_main_maestro_alumnos : AppCompatActivity() {
    lateinit var activityAdapter: ProfesorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_alumnos)



        activityAdapter = ProfesorAdapter(this,object: ProfesorAdapter.OnAdapterListener{},R.layout.adapter_activity_maestro_alumnos,R.layout.adapter_activity_maestro_alumnos_asistencia)
        val list_Activity_alumno = findViewById<RecyclerView>(R.id.rvPadre)
        list_Activity_alumno.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = activityAdapter
        }




    }


}