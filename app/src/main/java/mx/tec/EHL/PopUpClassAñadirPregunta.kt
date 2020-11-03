package mx.tec.EHL

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*


class PopUpClassAñadirPregunta {
    //PopupWindow display method
    fun showPopupWindow(view: View) {


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
        val test30 = popupView.findViewById<TextView>(R.id.titleTextAñadirPregunta)
        test30.setText(R.string.textTitleAñadirPregunta)
        val test31 = popupView.findViewById<TextView>(R.id.textViewIngresaNuevaPregunta)
        test31.setText(R.string.textViewIngresaNuevaPregunta)
        val test32 = popupView.findViewById<TextView>(R.id.textViewIngresaRespuesta)
        test32.setText(R.string.textViewIngresaRespuesta)
        val test33 = popupView.findViewById<EditText>(R.id.editTextTextIngresarNuevaPregunta)
        val test34 = popupView.findViewById<EditText>(R.id.editTextIngresaReespuesta)


        val buttonAñadir =
            popupView.findViewById<Button>(R.id.ButtonAñadir)
        buttonAñadir.setOnClickListener { //As an example, display the message
            Toast.makeText(view.context, "Se ha agregado la nueva pregunta al CQuiz", Toast.LENGTH_SHORT)
                .show()
        }


        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener { v, event -> //Close the window when clicked
            popupWindow.dismiss()
            true
        }
    }
}