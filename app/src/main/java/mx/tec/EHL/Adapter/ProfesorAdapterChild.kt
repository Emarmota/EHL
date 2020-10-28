package mx.tec.EHL.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.R
import java.lang.Exception

class ProfesorAdapterChild (val context: Context, var listener: ProfesorAdapter.OnAdapterListener, var layoutInflater: Int) : RecyclerView.Adapter<ProfesorAdapterChild.ActivityViewHolder>(){

    class ActivityViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        var button1 : RadioButton? = null
        var button2 : RadioButton? = null

        init {
            try{ button1 = view.findViewById<RadioButton>(R.id.radioButton) }
            catch(e: Exception){ }
            try{ button2 = view.findViewById<RadioButton>(R.id.radioButton2) }
            catch(e: Exception){ }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(LayoutInflater.from(context).inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        if(holder.button1 != null){
            holder.button1!!.setOnClickListener { println("BOTON 1") }
        }
        if(holder.button2 != null){
            holder.button2!!.setOnClickListener { println("BOTON 2") }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

}