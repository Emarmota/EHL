package mx.tec.EHL.DataBase.Tablas

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alumno")
data class Alumno(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="idAlumno")
    val idAlumno:Int,
    @ColumnInfo(name="nombreAlumno")
    val nombreAlumno:String,
    @ColumnInfo(name="nivel")
    val nivel:String,
    @ColumnInfo(name="asistencia")
    val asistencia:Int,
    @ColumnInfo(name="usuario")
    val usuario: String,
    @ColumnInfo(name="contrasena")
    val contrasena: String



) {
}