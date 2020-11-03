package mx.tec.EHL.Profesor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import mx.tec.EHL.R

class activity_main_maestro_edicioncquiz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_edicioncquiz)

        val btnback=findViewById<ImageView>(R.id.btn_backcrecquiz)
        btnback.setOnClickListener{
            val intent= Intent(this@activity_main_maestro_edicioncquiz, activity_main_maestro_trabajos::class.java)
            startActivity(intent)
        }
    }
}