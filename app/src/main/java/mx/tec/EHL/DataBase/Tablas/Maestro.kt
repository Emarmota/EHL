package mx.tec.EHL.DataBase.Tablas

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Maestro")
data class Maestro (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="idMaestro")
    val idMaestro:Int,
    @ColumnInfo(name="nombreMaestro")
    val nombreMaestro:String,
    @ColumnInfo(name="grupo")
    val grupo:String,
    @ColumnInfo(name="usuarioMaestro")
    val usuarioMaestro:String,
    @ColumnInfo(name="constrasenaMaestro")
    val constrasenaMaestro: String
){
}




















