package mx.tec.EHL


import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.tec.EHL.Adapter.AlumnoAdapter
import mx.tec.EHL.Helper.Constant
import mx.tec.EHL.Helper.PreferencesHelper
import org.json.JSONArray
import org.json.JSONObject


class PopUpClassCambiarContraseñaAlumno(context: Context) {
    val sharedPref by lazy { PreferencesHelper(context) }
    val context = context
    //PopupWindow display method
    fun showPopupWindow(view: View) {


        //Create a View object yourself through inflater
        val inflater = view.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.activity_popup_cambiarcontrasena_alumno, null)

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
        val test21 = popupView.findViewById<TextView>(R.id.titleTextCambiarContraseñaAlumno)
        test21.setText(R.string.textTitleCambiarContraseñaAlumno)

        val test22 = popupView.findViewById<TextView>(R.id.textViewIngresarContraseñaAlumno)
        test22.setText(R.string.textViewIngresarContraseñaAlumno)

        val test23 = popupView.findViewById<EditText>(R.id.editTextTextIngresarContraseñaAlumno)

        val test24 = popupView.findViewById<TextView>(R.id.textViewIngresarNuevaContraseñaAlumno)
        test24.setText(R.string.textViewIngresarNuevaContraseñaAlumno)

        val test25 = popupView.findViewById<EditText>(R.id.editTextIngresarContraseñaAlumno)

        val test26 = popupView.findViewById<TextView>(R.id.textViewConfirmarNuevaContraseñaAlumno)
        test26.setText(R.string.textViewIngresarConfirmarNuevaContraseñaAlumno)

        val test27 = popupView.findViewById<EditText>(R.id.editTextConfirmarNuevaContraseñaAlumno)

        val buttonHecho = popupView.findViewById<Button>(R.id.ButtonHechoAlumno)
        buttonHecho.setOnClickListener { //As an example, display the message
            if(test23.text.toString() != sharedPref.getString(Constant.PREF_PASSWORD)){
                Toast.makeText(context, "La contraseña actual no es correcta",Toast.LENGTH_SHORT)

            }else if (test25.text.toString() != test27.text.toString()){
                Toast.makeText(context, "Confirmacion de contraseña incorrecta",Toast.LENGTH_SHORT)

            }else{
                var queue = Volley.newRequestQueue(context)
                val uri = "http://"+R.string.ip_connection+"/api/alumnoAjustes/"+sharedPref.getInt(Constant.PREF_ID)+"/"+test27.text.toString()
                val listener = Response.Listener<JSONArray> { response ->
                }
                val error = Response.ErrorListener { error ->
                    Log.e("MENSAJE_ERROR", error.message!!)
                }
                val request = JsonArrayRequest(Request.Method.GET,uri,null,listener, error)
                queue.add(request)


                Toast.makeText(view.context, "Su nueva contraseña es: " + test27.text.toString(), Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()
            }

        }


    }
}