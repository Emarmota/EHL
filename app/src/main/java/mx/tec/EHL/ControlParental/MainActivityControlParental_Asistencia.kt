package mx.tec.EHL.ControlParental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.Adapter.ControlParentalAdapter
import mx.tec.EHL.R

class MainActivityControlParental_Asistencia : AppCompatActivity() {
    lateinit var activityAdapter: ControlParentalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_control_parental__asistencia)


        activityAdapter = ControlParentalAdapter(this,null,object: ControlParentalAdapter.OnAdapterListener{},R.layout.adapter_activity_controlparental_asistencias,null)
        val list_Activity_alumno = findViewById<RecyclerView>(R.id.rvPadre)
        list_Activity_alumno.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = activityAdapter
        }

        val btnback=findViewById<ImageView>(R.id.btn_backcpa)
        btnback.setOnClickListener{
            val intent= Intent(this@MainActivityControlParental_Asistencia, MainActivityControlParental::class.java)
            startActivity(intent)
        }

    }

}