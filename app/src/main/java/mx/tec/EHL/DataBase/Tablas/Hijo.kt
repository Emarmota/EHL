package mx.tec.EHL.DataBase.Tablas

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Hijo",foreignKeys = arrayOf(
    ForeignKey(entity = ControlParental::class,
        parentColumns = arrayOf("idControlParental"),
        childColumns = arrayOf("idControlParental"),
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(entity = Alumno::class,
        parentColumns = arrayOf("idAlumno"),
        childColumns = arrayOf("idAlumno"),
        onDelete = ForeignKey.CASCADE
    )
))
data class Hijo(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="idControlParental")
    val idControlParental:Int,
    @ColumnInfo(name="idAlumno")
    val idAlumno:Int

){
}