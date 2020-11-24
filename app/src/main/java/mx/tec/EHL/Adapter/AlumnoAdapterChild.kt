package mx.tec.EHL.Adapter
import android.content.Context
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.R
import java.lang.Exception
import java.util.ArrayList

class AlumnoAdapterChild (val context: Context, val elementos:  ArrayList<ArrayList<String>>?, var listener: AlumnoAdapter.OnAdapterListener, var layoutInflater: Int) : RecyclerView.Adapter<AlumnoAdapterChild.ActivityViewHolder>(){

    class ActivityViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        var txt_primario : TextView? = null
        var txt_secundario : TextView? = null
        init {
            txt_primario = view.findViewById(R.id.txt_primario)
            txt_secundario = view.findViewById(R.id.txt_secundario)

        }
        fun bindData(elemento: ArrayList<String>){
            txt_primario !!.text = elemento[0]
            txt_secundario !!.text = elemento[1]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(LayoutInflater.from(context).inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val elem = elementos!![position]
        holder.bindData(elem)

    }

    override fun getItemCount(): Int {
        return elementos!!.size
    }

}