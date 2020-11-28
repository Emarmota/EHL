package mx.tec.EHL.Adapter
import android.content.Context
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.tec.EHL.R
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.ArrayList

class AlumnoAdapterChild (val context: Context, val elementos:  ArrayList<ArrayList<String>>?, var listener: AlumnoAdapter.OnAdapterListener, var layoutInflater: Int) : RecyclerView.Adapter<AlumnoAdapterChild.ActivityViewHolder>(){

    class ActivityViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        var txt_primario : TextView? = null
        var txt_secundario : TextView? = null
        var idPregunta : String? = null
        var button1 : CheckBox? = null
        companion object{
            val listaCheckBox = arrayListOf<CheckBox>()
        }
        val seleccionCheckBox = arrayListOf<CheckBox>()

        init {
            txt_primario = view.findViewById(R.id.txt_primario)
            txt_secundario = view.findViewById(R.id.txt_secundario)
            try{ button1 = view.findViewById(R.id.checkBox1) }
            catch (e: Exception){ }
        }
        fun bindData(elemento: ArrayList<String>){
            if(txt_primario != null)  txt_primario !!.text = elemento!![0]
            if(txt_secundario != null) txt_secundario !!.text = elemento!![1]
            else{
                idPregunta = elemento!![1]
            }
            if(button1 != null) {
                println("SE PUEDE AÑADIR A LA LISTA")
                listaCheckBox.add(button1!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(LayoutInflater.from(context).inflate(layoutInflater,parent,false))
    }




    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val elem = elementos!![position]
        holder.bindData(elem)
        if(holder.button1 != null) {
            holder.seleccionCheckBox.add(holder.button1!!)

            holder.button1!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    holder.seleccionCheckBox.set(0,holder.button1!!)
                    //holder.seleccionRespuesta.add(JSONObject().put("respuesta"+position.toString(),holder.txt_primario!!.text.toString()))
                    respuestaSeleccion.RespuestaSeleccionada(position, ActivityViewHolder.listaCheckBox, holder.seleccionCheckBox)
                }else{
                    holder.seleccionCheckBox.set(0,holder.button1!!)
                    //holder.seleccionRespuesta.remove(JSONObject().get("respuesta"+position.toString()))
                    respuestaSeleccion.RespuestaSeleccionada(position,ActivityViewHolder.listaCheckBox, holder.seleccionCheckBox)

                }
            }
        }

    }

    

    override fun getItemCount(): Int {
        return elementos!!.size
    }

    var respuestaSeleccion: OnAdapterListener
    init {
        respuestaSeleccion = context as OnAdapterListener
    }
    interface OnAdapterListener {
        fun RespuestaSeleccionada(position : Int, listaCheckBox: ArrayList<CheckBox>, seleccionCheckBox:  ArrayList<CheckBox>)
    }








}