package mx.tec.EHL

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*

class PopUpClassAñadirAlumno (context: Context) {
    val context = context

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
        val NombreAlumno = popupView.findViewById<EditText>(R.id.txt_NombreAlum)
        val UsuarioAlumno = popupView.findViewById<EditText>(R.id.txt_usuarioalum)
        val ContraseñaAlumno = popupView.findViewById<EditText>(R.id.txt_contraseñaalum)
        val grupoSpinner = popupView.findViewById<Spinner>(R.id.spinner_grupo)

        val grupitos = context.resources.getStringArray(R.array.gruposlocal)

        var GrupoAlumno = ""

        if (grupoSpinner != null) {
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, grupitos)
            grupoSpinner.adapter = adapter

            grupoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    GrupoAlumno = parent!!.getItemAtPosition(position) as String
                }
            }

            val buttonEdit =
                popupView.findViewById<Button>(R.id.messageButton)
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
    }
}