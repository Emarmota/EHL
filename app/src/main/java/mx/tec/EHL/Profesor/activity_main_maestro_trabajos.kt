package mx.tec.EHL.Profesor

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.Adapter.ProfesorAdapter
import mx.tec.EHL.R
import java.util.*

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

        val btnback=findViewById<ImageView>(R.id.btn_backmmt)
        btnback.setOnClickListener{
            val intent= Intent(this@activity_main_maestro_trabajos, activity_main_maestro::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val radioButtonAsignar = findViewById<ImageView>(R.id.radioButtonAsignar)

        radioButtonAsignar.setOnClickListener{
            val builder = AlertDialog.Builder(this@activity_main_maestro_trabajos)
            // String array for alert dialog multi choice items
            val activitiesArray = arrayOf("Grupo 1", "Grupo 2", "Grupo 3", "Grupo 4", "Grupo 5", "Grupo 6")
            // Boolean array for initial selected items
            val booleanActivities = booleanArrayOf(false,
                false,
                false,
                false,
                false,
                false
            )

            val activitiesList = Arrays.asList(*activitiesArray)
            //setTitle
            builder.setTitle("Seleccionar grupo: ")
            //set multichoice
            builder.setMultiChoiceItems(activitiesArray, booleanActivities) { dialog, which, isChecked ->
                // Update the current focused item's checked status
                booleanActivities[which] = isChecked
                // Get the current focused item
                val currentItem = activitiesList[which]
                // Notify the current action
                Toast.makeText(
                    applicationContext,
                    currentItem + " " + isChecked,
                    Toast.LENGTH_SHORT
                ).show()
            }
            // Set the neutral/cancel button click listener
            builder.setNeutralButton("Cancelar") { dialog, which ->
                // Do something when click the neutral button
            }
            builder.setPositiveButton("Aceptar") { dialog, which ->
                // Do something when click the neutral button
            }
            val dialog = builder.create()
            // Display the alert dialog on interface
            dialog.show()
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