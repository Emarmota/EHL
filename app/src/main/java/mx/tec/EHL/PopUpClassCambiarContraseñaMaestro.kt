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
import java.lang.NullPointerException


class PopUpClassCambiarContraseñaMaestro(context:Context) {
    val sharedPref by lazy { PreferencesHelper(context) }
    val context = context
    //PopupWindow display method
    fun showPopupWindow(view: View) {


        //Create a View object yourself through inflater
        val inflater = view.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.activity_popup_cambiarcontrasena_maestro, null)

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
        val test11 = popupView.findViewById<TextView>(R.id.titleTextCambiarContraseña)
        test11.setText(R.string.textTitleCambiarContraseña)

        val test12 = popupView.findViewById<TextView>(R.id.textViewIngresarContraseña)
        test12.setText(R.string.textViewIngresarContraseña)

        val test13 = popupView.findViewById<EditText>(R.id.editTextTextIngresarContraseña)

        val test14 = popupView.findViewById<TextView>(R.id.textViewIngresarNuevaContraseña)
        test14.setText(R.string.textViewIngresarNuevaContraseña)

        val test15 = popupView.findViewById<EditText>(R.id.editTextIngresarContraseña)

        val test16 = popupView.findViewById<TextView>(R.id.textViewConfirmarNuevaContraseña)
        test16.setText(R.string.textViewIngresarConfirmarNuevaContraseña)

        val test17 = popupView.findViewById<EditText>(R.id.editTextConfirmarNuevaContraseña)

        val buttonHecho =
            popupView.findViewById<Button>(R.id.ButtonHecho)
            buttonHecho.setOnClickListener { //As an example, display the message
            if(test15.text.toString() == "" || test17.text.toString() == "" || test13.text.toString() == "" ){
                Toast.makeText(context, "Completa todos los campos",Toast.LENGTH_SHORT).show()
            }else if (test15.text.toString() != test17.text.toString()){
                Toast.makeText(context, "Confirmacion de contraseña incorrecta",Toast.LENGTH_SHORT).show()
                test15.requestFocus()
            }else if(test13.text.toString() != sharedPref.getString(Constant.PREF_PASSWORD)){
                Toast.makeText(context, "La contraseña actual no es correcta",Toast.LENGTH_SHORT).show()
                test13.requestFocus()
            } else{
                var queue = Volley.newRequestQueue(context)
                val uri = "http://"+context.getString(R.string.ip_connection)+"/api/maestroAjustes/"+sharedPref.getInt(Constant.PREF_ID)+"/"+test17.text.toString()
                val listener = Response.Listener<JSONArray> { response ->
                }
                val error = Response.ErrorListener { error ->
                    try {
                        Log.e("MENSAJE_ERROR", error.message!!)
                    }
                    catch (e: NullPointerException){}
                }
                val request = JsonArrayRequest(Request.Method.GET,uri,null,listener, error)
                queue.add(request)

                sharedPref.put(Constant.PREF_PASSWORD, test17.text.toString())

                Toast.makeText(view.context, "Su nueva contraseña es: " + test17.text.toString(), Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()
            }
        }

        /*
        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener { v, event -> //Close the window when clicked
            popupWindow.dismiss()
            true
        }
         */
    }
}