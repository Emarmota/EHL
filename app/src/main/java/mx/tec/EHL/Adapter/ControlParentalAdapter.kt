package mx.tec.EHL.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.R
import java.util.*
import kotlin.collections.ArrayList

class ControlParentalAdapter (val context : Context, val elementos: Array<ArrayList<String>>?,var listener: OnAdapterListener, var layoutInflater: Int, var layoutInflaterChild:Int?) : RecyclerView.Adapter<ControlParentalAdapter.ActivityViewHolder>(){
    class ActivityViewHolder(val view: View, val child : Boolean) : RecyclerView.ViewHolder(view){
        var recyclerViewChild : RecyclerView? = null
        var txt_primario : TextView? = null
        var txt_secundario : TextView? = null
        init {
            txt_primario = view.findViewById(R.id.txt_primario)
            txt_secundario = view.findViewById(R.id.txt_secundario)
            if(child){
                recyclerViewChild = view.findViewById(R.id.rvHijo)
            }else{

            }
        }
        fun bindData(elemento: ArrayList<String>){
            txt_primario !!.text = elemento[1]
            txt_secundario !!.text = "10"


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        if(layoutInflaterChild != null){
            return ActivityViewHolder(LayoutInflater.from(parent.context).inflate(layoutInflater, parent, false),true)
        }else{
            return ActivityViewHolder(LayoutInflater.from(parent.context).inflate(layoutInflater, parent, false),false)
        }
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val elem = elementos!![position]
        holder.bindData(elem)
        if(layoutInflaterChild != null){
            val elementosChild = Array(1,{ arrayListOf<String>( ) })
            elementosChild.set(0, elem.slice(2..3) as ArrayList<String>)
            SetRecycler(elementosChild, holder.recyclerViewChild,layoutInflaterChild!!)
        }
    }

    interface OnAdapterListener {
        /*
        fun onClick(: )
        fun onUpdate(: )
        fun onDelete(: )

         */
    }

    override fun getItemCount(): Int {
        return elementos!!.size
    }


    private fun SetRecycler(elementos: Array<ArrayList<String>>, recyclerView: RecyclerView?, layoutInflaterChild:Int){
        val childRecyclerAdapter = ControlParentalAdapterChild(context, elementos, object: ControlParentalAdapter.OnAdapterListener{}, layoutInflaterChild)
        recyclerView!!.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        recyclerView!!.adapter = childRecyclerAdapter
    }
}