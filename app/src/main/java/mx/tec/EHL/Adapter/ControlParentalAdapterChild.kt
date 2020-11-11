package mx.tec.EHL.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.R
import java.lang.Exception
import java.util.ArrayList

class ControlParentalAdapterChild(val context: Context, val elementos:  ArrayList<String>, var listener: ControlParentalAdapter.OnAdapterListener, var layoutInflater: Int) : RecyclerView.Adapter<ControlParentalAdapterChild.ActivityViewHolder>(){

    class ActivityViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        var button1 : RadioButton? = null
        var button2 : RadioButton? = null
        var txt_primario : TextView? = null
        var txt_secundario : TextView? = null
        init {
            txt_primario = view.findViewById(R.id.txt_primario)
            txt_secundario = view.findViewById(R.id.txt_secundario)

            try{ button1 = view.findViewById<RadioButton>(R.id.radioButton) }
            catch(e: Exception){ }
            try{ button2 = view.findViewById<RadioButton>(R.id.radioButton2) }
            catch(e: Exception){ }
        }
        fun bindData(elemento: ArrayList<String>){
            println(elemento)
            txt_primario !!.text = elemento[0]
            txt_secundario !!.text = elemento[1]


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(LayoutInflater.from(context).inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bindData(elementos)
        if(holder.button1 != null){
            holder.button1!!.setOnClickListener { println("BOTON 1") }
        }
        if(holder.button2 != null){
            holder.button2!!.setOnClickListener { println("BOTON 2") }
        }
    }

    override fun getItemCount(): Int {
        return elementos!!.size
    }

}