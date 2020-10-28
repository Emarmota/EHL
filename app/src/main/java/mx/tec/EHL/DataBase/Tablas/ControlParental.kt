package mx.tec.EHL.DataBase.Tablas

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "ControlParental")
data class ControlParental(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="idControlParental")
    val idControlParental:Int,
    @ColumnInfo(name="usuario")
    val usuario:String,
    @ColumnInfo(name="contrasena")
    val contrasena: String


) {
}