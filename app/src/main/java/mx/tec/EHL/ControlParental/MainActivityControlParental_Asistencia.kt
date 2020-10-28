package mx.tec.EHL.ControlParental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.Adapter.ControlParentalAdapter
import mx.tec.EHL.R

class MainActivityControlParental_Asistencia : AppCompatActivity() {
    lateinit var activityAdapter: ControlParentalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_control_parental__asistencia)


        activityAdapter = ControlParentalAdapter(this,object: ControlParentalAdapter.OnAdapterListener{},R.layout.adapter_activity_controlparental_asistencias,null)
        val list_Activity_alumno = findViewById<RecyclerView>(R.id.rvPadre)
        list_Activity_alumno.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = activityAdapter
        }

    }

}