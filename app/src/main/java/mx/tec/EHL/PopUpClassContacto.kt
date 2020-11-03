package mx.tec.EHL

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*


class PopUpClassContacto {
    //PopupWindow display method
    fun showPopupWindow(view: View) {


        //Create a View object yourself through inflater
        val inflater = view.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.activity_popup_contacto, null)

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
        val test18 = popupView.findViewById<TextView>(R.id.titleTextContacto)
        test18.setText(R.string.textTitleContacto)
        val test19 = popupView.findViewById<TextView>(R.id.textViewCorreoGmail)
        test19.setText(R.string.textViewCorreoGmail)
        val test20 = popupView.findViewById<TextView>(R.id.textViewCorreoSec)
        test20.setText(R.string.textViewCorreoSec)



        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener { v, event -> //Close the window when clicked
            popupWindow.dismiss()
            true
        }
    }
}