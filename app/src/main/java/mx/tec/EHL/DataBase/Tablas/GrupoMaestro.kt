package mx.tec.EHL.DataBase.Tablas

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
@Entity(tableName = "Grupo",foreignKeys = arrayOf(
        ForeignKey(entity = Maestro::class,
                parentColumns = arrayOf("idMaestro"),
                childColumns = arrayOf("idMaestro"),
                onDelete = CASCADE),
        ForeignKey(entity = Alumno::class,
                parentColumns = arrayOf("idAlumno"),
                childColumns = arrayOf("idAlumno"),
                onDelete = CASCADE)
))

data class GrupoMaestro (
        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name="idMaestro")
        val idMaestro:Int,
        @ColumnInfo(name="idAlummno")
        val idAlummno:Int,
        @ColumnInfo(name="grupo")
        val grupo:String,
        @ColumnInfo(name="actividad")
        val actividad:String,
        @ColumnInfo(name="faltas")
        val faltas:Int,
        @ColumnInfo(name="calificacion")
        val calificacion: Double)
{

}