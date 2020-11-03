package mx.tec.EHL.Profesor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.PopUpClassAñadirPregunta
import mx.tec.EHL.R

class activity_main_maestro_creacioncquiz : AppCompatActivity() {
    lateinit var activityAdapter: ProfesorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_creacioncquiz)

        activityAdapter = ProfesorAdapter(this,object: ProfesorAdapter.OnAdapterListener{},R.layout.adapter_activity_maestro_trabajos_edicioncquiz,R.layout.adapter_activity_maestro_trabajos_edicioncquiz_respuestas)
        val rvPadre = findViewById<RecyclerView>(R.id.rvPadre)
        rvPadre.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = activityAdapter
        }

        val popupButton10 =
            findViewById<ImageView>(R.id.imageView19)
        popupButton10.setOnClickListener { v ->
            val popUpClass = PopUpClassAñadirPregunta()
            popUpClass.showPopupWindow(v)
        }
    }
}