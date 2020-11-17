package mx.tec.EHL.Profesor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import mx.tec.EHL.R

class activity_main_maestro_boleta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maestro_boleta)

        val btnback=findViewById<ImageView>(R.id.btn_backmmb)
        btnback.setOnClickListener{
            val intent= Intent(this@activity_main_maestro_boleta, activity_main_maestro::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

}