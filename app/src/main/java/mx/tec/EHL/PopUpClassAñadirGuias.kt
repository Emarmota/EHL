package mx.tec.EHL
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class PopUpClassAñadirGuias (context: Context): AppCompatActivity() {
    //PopupWindow display method

    private val STOAGE_PERMISSION_CODE : Int =1000
    val FILE = 1

    private var imageData : ByteArray?=null
    val storage = Firebase.storage("gs://my-project-d35b1.appspot.com")
    val storageRef = storage.reference


    val context = context
    fun showPopupWindow(view: View) {
        println("--------------SHOWPOPUP--------------------------")
        //Create a View object yourself through inflater
        val inflater = view.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.activity_popup_anadirguia, null)

        //Specify the length and width through constants
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        //Make Inactive Items Outside Of PopupWindow
        val focusable = true

        //Create a window with our parameters
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        FirebaseApp.initializeApp(context)

        //Initialize the elements of our window, install the handler
        val btn_adjuntararchivo = popupView.findViewById<TextView>(R.id.textViewAdjuntarArchivo)
        btn_adjuntararchivo.setOnClickListener {
            openContent()
        }

        val btn_aceptar = popupView.findViewById<Button>(R.id.btn_aceptar)
        btn_aceptar.setOnClickListener{
            popupWindow.dismiss()
        }


    }

    fun openContent(){
        println("--------------OPENCONTENT")
        var intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, FILE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println("ONACTIVITY--------------")
        if(requestCode == FILE){
            var fileUri = data?.data!!
            uploadFile(fileUri)
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    fun uploadFile(fileUri: Uri){
        Toast.makeText(context, "llego", Toast.LENGTH_LONG).show()
        var metaCursor = contentResolver.query(fileUri, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME), null, null, null)!!
        metaCursor.moveToFirst()
        var fileName = metaCursor.getString(0)
        metaCursor.close()

        var storageRef = FirebaseStorage.getInstance().reference.child("files").child(fileName)
        storageRef.putFile(fileUri).addOnSuccessListener {
            Toast.makeText(this, "Se ha subido el archivo correctamente", Toast.LENGTH_LONG).show()
        }
    }
}