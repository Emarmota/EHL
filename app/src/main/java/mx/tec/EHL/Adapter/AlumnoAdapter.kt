package mx.tec.EHL.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.R

class AlumnoAdapter (val context : Context, var listener: OnAdapterListener, var layoutInflater: Int, var layoutInflaterChild:Int?) : RecyclerView.Adapter<AlumnoAdapter.ActivityViewHolder>(){
    class ActivityViewHolder(val view: View, val child : Boolean) : RecyclerView.ViewHolder(view){
        var recyclerViewChild : RecyclerView? = null

        init {
            if(child){
                recyclerViewChild = view.findViewById(R.id.rvHijo)
            }else{

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
        if(layoutInflaterChild != null){
            SetRecycler(holder.recyclerViewChild,layoutInflaterChild!!)
        }else{

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
        return 4
    }


    private fun SetRecycler(recyclerView: RecyclerView?, layoutInflaterChild:Int){
        val childRecyclerAdapter = AlumnoAdapterChild(context, object: AlumnoAdapter.OnAdapterListener{}, layoutInflaterChild)
        recyclerView!!.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        recyclerView!!.adapter = childRecyclerAdapter
    }
}