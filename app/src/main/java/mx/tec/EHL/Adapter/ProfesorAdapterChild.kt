package mx.tec.EHL.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.MainActivity
import mx.tec.EHL.Profesor.activity_main_maestro_boleta
import mx.tec.EHL.R
import java.lang.Exception
import java.util.ArrayList

class ProfesorAdapterChild (val context: Context, val elementos:  ArrayList<ArrayList<String>>?, var listener: ProfesorAdapter.OnAdapterListener, var layoutInflater: Int) : RecyclerView.Adapter<ProfesorAdapterChild.ActivityViewHolder>(){

    class ActivityViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        var button1 : RadioButton? = null
        var button2 : RadioButton? = null
        var button3 : ImageButton? = null
        var txt_primario : TextView? = null
        var txt_secundario : TextView? = null
        init {
            try{ txt_primario = view.findViewById(R.id.txt_primario) }
            catch(e: Exception){ }
            try{ txt_secundario = view.findViewById(R.id.txt_secundario) }
            catch(e: Exception){ }
            try{ button1 = view.findViewById<RadioButton>(R.id.radioButton) }
            catch(e: Exception){ }
            try{ button2 = view.findViewById<RadioButton>(R.id.radioButton2) }
            catch(e: Exception){ }
            try{ button3 = view.findViewById<ImageButton>(R.id.imageButton) }
            catch(e: Exception){ }
        }
        fun bindData(elemento: ArrayList<String>){
            if(txt_primario != null)  txt_primario !!.text = elemento!![0]
            if(txt_secundario != null) txt_secundario !!.text = elemento!![1]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(LayoutInflater.from(context).inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val elem = elementos!![position]
        holder.bindData(elem)
        if(holder.button1 != null){
            holder.button1!!.setOnClickListener { println("BOTON 1") }
        }
        if(holder.button2 != null){
            holder.button2!!.setOnClickListener { println("BOTON 2") }
        }
        if(holder.button3 != null){
            holder.button3!!.setOnClickListener {
                val intent = Intent(context, activity_main_maestro_boleta::class.java)
                context.startActivity(intent) }
        }
    }

    override fun getItemCount(): Int {
        return elementos!!.size
    }

}