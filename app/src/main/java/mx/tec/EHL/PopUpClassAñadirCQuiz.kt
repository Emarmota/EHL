package mx.tec.EHL

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.InputFilter
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.Helper.PreferencesHelper
import mx.tec.EHL.Profesor.activity_main_maestro_creacioncquiz
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder


class PopUpClassAñadirCQuiz(context: Context) {
    val context = context
    val sharedPref by lazy { PreferencesHelper(context) }
    var grupoAlumno = ""
    var nombreAlumno :EditText? = null
    var usuarioAlumno :EditText? = null
    var contraseñaAlumno :EditText? = null
    var textoEmergente : EditText? = null

    var anadirConGrupo = false
    //PopupWindow display method
    fun showPopupWindow(view: View) {
        //Create a View object yourself through inflater
        val inflater = view.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.activity_popup_agregarcquiz, null)


        var grupitos = context.resources.getStringArray(R.array.gruposlocal)

        //Specify the length and width through constants
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        //Make Inactive Items Outside Of PopupWindow
        val focusable = true

        //Create a window with our parameters
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        //Initialize the elements of our window, install the handler
        nombreAlumno = popupView.findViewById(R.id.txt_NombreAlum)
        val grupoSpinner = popupView.findViewById<Spinner>(R.id.spinner_grupo)

        var queue = Volley.newRequestQueue(context)
        val uri = "http://"+context.getString(R.string.ip_connection)+"/api/alumnosGruposMaestro/"+sharedPref.getInt(Constant.PREF_ID)
        val listener = Response.Listener<JSONArray> { response ->
            val lista : List<String>
            lista = arrayListOf()
            var elemento : JSONObject
            for(i in 0 until response.length()){
                elemento = response.getJSONObject(i)
                lista.add(i,
                        elemento.getString("nombreGrupo")
                )
            }
            lista.add("Añadir grupo")
            SetSpinnerValues(popupView, grupoSpinner, lista)
        }
        val error = Response.ErrorListener { error ->
            try {
                Log.e("MENSAJE_ERROR", error.message!!)
            }
            catch (e: NullPointerException){}
        }
        val request = JsonArrayRequest(Request.Method.GET, uri, null, listener, error)
        queue.add(request)

        try {
            val continuarButton = popupView.findViewById<Button>(R.id.continuarButton)
            continuarButton.setOnClickListener { //As an example, display the message
                Toast.makeText(view.context, "El alumno " + nombreAlumno!!.text.toString() + " ha sido agregado", Toast.LENGTH_SHORT).show()
                AgregarCQuiz(context)
                popupWindow.dismiss()

                val intent = Intent(context, activity_main_maestro_creacioncquiz::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivity(intent)
            }
        }
        catch (e: java.lang.NullPointerException){}
    }

    private fun AgregarCQuiz(context : Context){
        var queue = Volley.newRequestQueue(context)
        var uri = ""
        if(anadirConGrupo){
            grupoAlumno = textoEmergente!!.text.toString()
            uri = "http://"+context.getString(R.string.ip_connection)+"/api/maestroAgregarAlumnoConGrupo/"+sharedPref.getInt(Constant.PREF_ID)+"/"+URLEncoder.encode( nombreAlumno!!.text.toString(), "utf-8")+"/"+usuarioAlumno!!.text.toString()+"/"+contraseñaAlumno!!.text.toString()+"/"+URLEncoder.encode(grupoAlumno, "utf-8")+"/"+(usuarioAlumno!!.text.toString()+"_cp")+"/"+(usuarioAlumno!!.text.toString()+"_cp")
        }
        else{
            uri = "http://"+context.getString(R.string.ip_connection)+"/api/maestroAgregarAlumnoSinGrupo/"+URLEncoder.encode( nombreAlumno!!.text.toString(), "utf-8")+"/"+usuarioAlumno!!.text.toString()+"/"+contraseñaAlumno!!.text.toString()+"/"+URLEncoder.encode(grupoAlumno, "utf-8")+"/"+(usuarioAlumno!!.text.toString()+"_cp")+"/"+(usuarioAlumno!!.text.toString()+"_cp")
        }

        val listener = Response.Listener<JSONArray> { response ->
        }
        val error = Response.ErrorListener { error ->
            try {
                Log.e("MENSAJE_ERROR", error.message!!)
            }
            catch (e: NullPointerException){}
        }
        val request = JsonArrayRequest(Request.Method.GET, uri, null, listener, error)
        queue.add(request)
    }

    private fun SetSpinnerValues( popupView : View, grupoSpinner: Spinner,  lista : List<String>){
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, lista )
            grupoSpinner.adapter = adapter

            grupoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
                override fun onItemSelected( parent: AdapterView<*>?, view: View, position: Int, id: Long)
                {
                    val linearLayout = popupView.findViewById<LinearLayout>(R.id.container)

                    if(parent!!.getItemAtPosition(position).toString() == "Añadir grupo" ){
                        // Create TextVieew
                        val textView = TextView(context)

                        textView.setText("Nombre del grupo nuevo")
                        textView.setTextColor(Color.parseColor("#000000"))
                        textView.textSize = 18f
                        // Create EditText
                        textoEmergente = EditText(context)
                        val filterArray = arrayOfNulls<InputFilter>(1)
                        filterArray[0] = InputFilter.LengthFilter(16)
                        textoEmergente!!.setFilters(filterArray)

                        textoEmergente!!.layoutParams = LinearLayout.LayoutParams(800,100)
                        textoEmergente!!.setPadding(20, 20, 20, 20)
                        textoEmergente!!.setTextColor(Color.parseColor("#000000"))
                        // Add EditText and textView to LinearLayout
                        linearLayout?.addView(textView)
                        linearLayout?.addView(textoEmergente)
                        grupoAlumno = textoEmergente!!.text.toString()
                        anadirConGrupo = true
                    }else{
                        linearLayout?.removeAllViews()
                        grupoAlumno = parent!!.getItemAtPosition(position).toString()
                        println(grupoAlumno)
                        anadirConGrupo = false
                    }
                }
            }
    }
}