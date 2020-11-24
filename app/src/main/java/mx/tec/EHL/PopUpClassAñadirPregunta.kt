package mx.tec.EHL

import android.app.Activity
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
import org.json.JSONArray
import java.net.URLEncoder


class PopUpClassAñadirPregunta(context: Context) {
    val context = context

    //PopupWindow display method
    fun showPopupWindow(view: View, nombreActividad :String?) {


        //Create a View object yourself through inflater
        val inflater = view.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.activity_popup_anadirpreguntaquiz, null)

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
        val pregunta = popupView.findViewById<EditText>(R.id.txt_pregunta)
        val respcorrect = popupView.findViewById<EditText>(R.id.txt_respuestacorrecta)
        val resp1 = popupView.findViewById<EditText>(R.id.txt_resp1)

        val buttonAñadir = popupView.findViewById<Button>(R.id.ButtonAñadir)
        buttonAñadir.setOnClickListener { //As an example, display the message
            AgregarPregunta(nombreActividad!!,pregunta.text.toString(),respcorrect.text.toString(),resp1.text.toString())
            Toast.makeText(view.context, "Se ha agregado la nueva pregunta al CQuiz", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            val activity = context as Activity
            activity.recreate()
        }

    }


    fun AgregarPregunta(nombreActividad : String, pregunta : String, respuestaCorrecta : String, respuesta : String){
        var queue = Volley.newRequestQueue(context)
        var uri = "http://"+context.getString(R.string.ip_connection)+"/api/maestroAgregarPregunta/"+nombreActividad+"/"+ URLEncoder.encode( pregunta, "utf-8")+"/"+ URLEncoder.encode( respuestaCorrecta, "utf-8")+"/"+ URLEncoder.encode( respuesta, "utf-8")
        val listener = Response.Listener<JSONArray> {
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





}