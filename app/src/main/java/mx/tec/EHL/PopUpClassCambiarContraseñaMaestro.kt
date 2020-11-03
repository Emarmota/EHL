package mx.tec.EHL

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*


class PopUpClassCambiarContraseñaMaestro {
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
            Toast.makeText(view.context, "Su nueva contraseña es: " + test17.text.toString(), Toast.LENGTH_SHORT)
                .show()
        }


        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener { v, event -> //Close the window when clicked
            popupWindow.dismiss()
            true
        }
    }
}