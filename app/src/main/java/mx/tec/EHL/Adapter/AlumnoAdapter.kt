package mx.tec.EHL.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.R
import java.lang.Exception
import java.lang.NullPointerException

class AlumnoAdapter (val context : Context, var elementos: ArrayList<ArrayList<String>>?, var listener: OnAdapterListener, var layoutInflater: Int, var layoutInflaterChild:Int?) : RecyclerView.Adapter<AlumnoAdapter.ActivityViewHolder>(){
    var elementosChild : ArrayList<ArrayList<String>>? = null
    var cantChild : ArrayList<Int>? = null
    var start = false

    class ActivityViewHolder(val view: View, val child : Boolean) : RecyclerView.ViewHolder(view){
        var recyclerViewChild : RecyclerView? = null
        var txt_primario : TextView? = null
        var txt_secundario : TextView? = null
        var button1 : ImageView? = null
        var basura : String? = null
        init {
            txt_primario = view.findViewById(R.id.txt_primario)
            txt_secundario = view.findViewById(R.id.txt_secundario)
            try { button1 = view.findViewById(R.id.button1) } catch (e : Exception){}
            if(child){
                recyclerViewChild = view.findViewById(R.id.rvHijo)
            }else{

            }
        }
        fun bindData(elemento: ArrayList<String>?){
            if(txt_primario != null)  txt_primario !!.text = elemento!![0]
            if(txt_secundario != null) txt_secundario !!.text = elemento!![1]
            else{
                basura = elemento!![1]
            }
            if(elemento.size > 2){
                basura = elemento!![2]
            }
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
        if(holder.button1 != null){
            holder.button1!!.setOnClickListener {
                onClick.OnClick( holder.button1!!, holder.txt_primario!!.text.toString(), holder.basura!! )
            }
        }

        if(layoutInflaterChild != null ) {
            var auxMatrix : ArrayList<ArrayList<String>>?
            auxMatrix  = arrayListOf(arrayListOf())
            var inicio = 0
            var final  = 0
            if(position == 0){
                inicio = 0
                final = (cantChild!![position])-1
                //println(inicio.toString()+ "   "+ final+ "   "+(cantChild!![position]))

            }else{
                for(i  in 0..(position-1)){
                    inicio += cantChild!![i]
                }
                for(i  in 0..position){
                    final += cantChild!![i]
                }
                final -= 1
            }
            for( i in inicio..final  ){
                if(i == inicio){
                    auxMatrix!![0] = elementosChild!![i]
                }else{
                    auxMatrix!!.add(elementosChild!![i])
                }
            }
            //println("MATRIZ AUX SIZE"+((auxMatrix!!.size)-1))
            for(i in 0..auxMatrix!!.size-1){
                println("MATRIZ AUX : "+auxMatrix!![i])
            }
            SetRecycler(auxMatrix!!,holder.recyclerViewChild, layoutInflaterChild!!)
        }
    }
    var onClick: AlumnoAdapter.OnAdapterListener
    init {
        onClick = context as AlumnoAdapter.OnAdapterListener
    }
    interface OnAdapterListener {
        fun OnClick(button:ImageView, nameActivity: String, tipo : String)
    }

    override fun getItemCount(): Int {
        if(start == false){
            println(elementos!!.toString() + " <<---- ELEMENTOSS")
            if(elementos?.get(0)!!.isNullOrEmpty() == false)
                DataOrder()
            else
                return 0
        }
        try {
            return elementos!!.size
        }
        catch (e: NullPointerException){
            return 0
        }
    }

    private fun DataOrder(){
        if(layoutInflaterChild != null ){
            var newElementos = arrayListOf( arrayListOf<String>())
            elementosChild = arrayListOf(arrayListOf())
            cantChild = arrayListOf()
            var countNew = 0
            var childCount = 0
            var cantChildAux = 0
            var cantChildRepet = 0
            for(i in 0..elementos!!.size-1){
                if(i == 0){
                    newElementos.set(countNew++,elementos!![i])
                    elementosChild!!.set(childCount++, elementos!![i].slice(2..elementos!![i].size-1) as ArrayList<String>)
                    cantChildAux++
                }else{
                    if(i != 0 && elementos!![i-1][0] != elementos!![i][0] ){
                        newElementos.add(countNew++,elementos!![i])
                        elementosChild!!.add(childCount++, elementos!![i].slice(2..elementos!![i].size-1) as ArrayList<String>)
                        cantChild!!.add(cantChildRepet, cantChildAux)
                        cantChildAux = 1
                        cantChildRepet++
                    }else{
                        elementosChild!!.add(childCount++, elementos!![i].slice(2..elementos!![i].size-1) as ArrayList<String>)
                        cantChildAux++
                    }
                }
            }
            cantChild!!.add(cantChildRepet, cantChildAux)
            elementos = newElementos
            start = true

        }
    }

    private fun SetRecycler(elementos: ArrayList<ArrayList<String>>, recyclerView: RecyclerView?, layoutInflaterChild:Int){
        val childRecyclerAdapter = AlumnoAdapterChild(context,elementos, object: AlumnoAdapter.OnAdapterListener{
            override fun OnClick(button: ImageView, nameActivity: String, tipo: String) {
            }
        }, layoutInflaterChild)
        recyclerView!!.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        recyclerView!!.adapter = childRecyclerAdapter
    }
}