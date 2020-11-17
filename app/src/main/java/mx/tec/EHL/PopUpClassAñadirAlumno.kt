package mx.tec.EHL

import android.content.Context
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
import org.json.JSONArray
import org.json.JSONObject


class PopUpClassAñadirAlumno(context: Context) {
    val context = context
    val sharedPref by lazy { PreferencesHelper(context) }
    var GrupoAlumno = ""

    //PopupWindow display method
    fun showPopupWindow(view: View) {
        //Create a View object yourself through inflater
        val inflater = view.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.activity_popup_agregaralumno, null)




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
        val NombreAlumno = popupView.findViewById<EditText>(R.id.txt_NombreAlum)
        val UsuarioAlumno = popupView.findViewById<EditText>(R.id.txt_usuarioalum)
        val ContraseñaAlumno = popupView.findViewById<EditText>(R.id.txt_contraseñaalum)
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





        val buttonEdit = popupView.findViewById<Button>(R.id.agregarButton)
        buttonEdit.setOnClickListener { //As an example, display the message
            Toast.makeText(
                    view.context,
                    "El alumno " + NombreAlumno.text.toString() + " ha sido agregado",
                    Toast.LENGTH_SHORT
            )
                    .show()
            popupWindow.dismiss()
        }


    }
    private fun AgregarAlumno(){
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
        if (grupoSpinner != null) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, lista as List)
            grupoSpinner.adapter = adapter

            grupoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
                override fun onItemSelected( parent: AdapterView<*>?, view: View, position: Int, id: Long)
                {
                    GrupoAlumno = parent!!.getItemAtPosition(position) as String
                }
            }
        }
    }




}