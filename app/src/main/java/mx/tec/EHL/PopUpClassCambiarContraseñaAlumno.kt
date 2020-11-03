package mx.tec.EHL


import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*


class PopUpClassCambiarContraseñaAlumno {
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

        val buttonHecho =
            popupView.findViewById<Button>(R.id.ButtonHechoAlumno)
        buttonHecho.setOnClickListener { //As an example, display the message
            Toast.makeText(view.context, "Su nueva contraseña es: " + test27.text.toString(), Toast.LENGTH_SHORT)
                .show()
        }


        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener { v, event -> //Close the window when clicked
            popupWindow.dismiss()
            true
        }
    }
}