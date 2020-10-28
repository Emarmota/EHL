package mx.tec.EHL.DataBase

import androidx.room.*
import mx.tec.EHL.DataBase.Tablas.Alumno
import mx.tec.EHL.DataBase.Tablas.ControlParental
import mx.tec.EHL.DataBase.Tablas.Maestro

@Dao
interface SchoolDao {
    @Query("SELECT * FROM Maestro")
    fun ObtenerMaestro(): List<Maestro>?
    @Query("SELECT * FROM Alumno")
    fun ObtenerAlumno(): List<Alumno>?
    @Query("SELECT * FROM ControlParental")
    fun ObtenerControlParental(): List<ControlParental>?

    @Insert
    fun RegistrarMaestro(maestro: Maestro)
    @Insert
    fun RegistrarAlumno(alumno: Alumno)
    @Insert
    fun RegistrarControlParental(controlParental: ControlParental)


    @Update
    fun actualizarPersonas(id: Maestro)
    @Delete
    fun eliminarPersonas(id: Maestro)
}