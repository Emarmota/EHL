package mx.tec.EHL.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.Profesor.activity_main_maestro_boleta
import mx.tec.EHL.R
import java.util.*
import kotlin.collections.ArrayList


class ProfesorAdapterChild(val context: Context, val elementos: ArrayList<ArrayList<String>>?, var listener: ProfesorAdapterChild.OnAdapterListener, var layoutInflater: Int) : RecyclerView.Adapter<ProfesorAdapterChild.ActivityViewHolder>(){
    val listaAlumnosFalta = arrayListOf<Int>()
    val listaAlumnosGrupos = arrayListOf<Int>()
    val listaAlumnosFaltaCheckBox = arrayListOf<CheckBox>()
    class ActivityViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        var button1 : RadioButton? = null
        var button2 : RadioButton? = null
        var button3 : ImageButton? = null
        var button4 : CheckBox? = null
        var txt_primario : TextView? = null
        var txt_secundario : TextView? = null
        var idAlumno : Int? = null
        var idGrupo : Int? = null

        init {
            try{ txt_primario = view.findViewById(R.id.txt_primario) }
            catch (e: Exception){ }
            try{ txt_secundario = view.findViewById(R.id.txt_secundario) }
            catch (e: Exception){ }
            try{ button1 = view.findViewById(R.id.radioButton) }
            catch (e: Exception){ }
            try{ button2 = view.findViewById(R.id.radioButton2) }
            catch (e: Exception){ }
            try{ button3 = view.findViewById(R.id.imageButton) }
            catch (e: Exception){ }
            try{ button4 = view.findViewById(R.id.checkBox1) }
            catch (e: Exception){ }



        }
        fun bindData(elemento: ArrayList<String>){
            if(txt_primario != null)  txt_primario !!.text = elemento!![0]
            if(txt_secundario != null) txt_secundario !!.text = elemento!![1]
            else{
                idAlumno = elemento!![1].toInt()
            }
            if(elemento.size >= 3){
                idGrupo = elemento!![2].toInt()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(LayoutInflater.from(context).inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val elem = elementos!![position]
        holder.bindData(elem)
        if(holder.button1 != null){
            holder.button1!!.setOnClickListener {
            }
            holder.button1!!.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                println("BOTON 1")
            })

        }
        if(holder.button2 != null){
            holder.button2!!.setOnClickListener { println("BOTON 2") }
        }
        if(holder.button3 != null){
            holder.button3!!.setOnClickListener {
                val intent = Intent(context, activity_main_maestro_boleta::class.java)
                context.startActivity(intent) }
        }
        if(holder.button4 != null) {
            holder.button4!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    listaAlumnosFalta.add(holder.idAlumno!!.toInt())
                    listaAlumnosFaltaCheckBox.add( holder.button4!!)
                    listaAlumnosGrupos.add(holder.idGrupo!!.toInt())
                    alumnosFalta.ListaAlumnosFalta(listaAlumnosFalta, listaAlumnosGrupos,listaAlumnosFaltaCheckBox)

                }else{
                    listaAlumnosFalta.remove(holder.idAlumno!!.toInt())
                    listaAlumnosFaltaCheckBox.remove(holder.button4!!)
                    listaAlumnosGrupos.remove(holder.idGrupo!!.toInt())

                    alumnosFalta.ListaAlumnosFalta(listaAlumnosFalta,listaAlumnosGrupos,listaAlumnosFaltaCheckBox)

                }
            }
        }

    }

    override fun getItemCount(): Int {
        return elementos!!.size
    }
    var alumnosFalta: OnAdapterListener
    init {
        alumnosFalta = context as OnAdapterListener
    }
    interface OnAdapterListener {
        fun ListaAlumnosFalta(listaAlumnosFalta : ArrayList<Int>, listaGrupos : ArrayList<Int>, checkBox : ArrayList<CheckBox>)
    }

}