package mx.tec.EHL

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*


class PopUpClass {
    //PopupWindow display method
    fun showPopupWindow(view: View) {


        //Create a View object yourself through inflater
        val inflater = view.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.activity_popup_agregaralumno, null)

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
        val test2 = popupView.findViewById<TextView>(R.id.titleText)
        test2.setText(R.string.textTitle)
        val test3 = popupView.findViewById<TextView>(R.id.textViewAñadirAlumno)
        test3.setText(R.string.textViewAñadirAlumno)
        val test4 = popupView.findViewById<EditText>(R.id.editTextTextPersonName2)
        val test5 = popupView.findViewById<TextView>(R.id.textViewAñadirApPaterno)
        test5.setText(R.string.textViewAñadirApPaterno)
        val test6 = popupView.findViewById<EditText>(R.id.editTextTextPersonName3)
        val test7 = popupView.findViewById<TextView>(R.id.textViewAñadirApMaterno)
        test7.setText(R.string.textViewAñadirApMaterno)
        val test8 = popupView.findViewById<EditText>(R.id.editTextTextPersonName4)
        val test9 = popupView.findViewById<TextView>(R.id.textViewAñadirGrupoAlumno)
        test9.setText(R.string.textViewAñadirGrupoAlumno)
        val test10 = popupView.findViewById<EditText>(R.id.editTextTextPersonName5)
        val buttonEdit =
            popupView.findViewById<Button>(R.id.messageButton)
        buttonEdit.setOnClickListener { //As an example, display the message
            Toast.makeText(view.context, "El alumno " + test4.text.toString() +" ha sido agregado", Toast.LENGTH_SHORT)
                .show()
        }
        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener { v, event -> //Close the window when clicked
            popupWindow.dismiss()
            true
        }
    }
}