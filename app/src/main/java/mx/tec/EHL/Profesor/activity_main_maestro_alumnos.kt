package mx.tec.EHL.Profesor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.PopUpClassAñadirAlumno
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

        val popupButton =
            findViewById<ImageView>(R.id.imageView8)
        popupButton.setOnClickListener { v ->
            val popUpClass = PopUpClassAñadirAlumno(this)
            popUpClass.showPopupWindow(v)
        }

        val btnback=findViewById<ImageView>(R.id.btn_backmmalum)
        btnback.setOnClickListener{
            val intent= Intent(this@activity_main_maestro_alumnos, activity_main_maestro::class.java)
            startActivity(intent)
        }
    }
}