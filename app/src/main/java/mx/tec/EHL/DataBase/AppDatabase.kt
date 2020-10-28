package mx.tec.EHL.DataBase

import android.content.Context
import androidx.room.*
import mx.tec.EHL.DataBase.Tablas.Alumno
import mx.tec.EHL.DataBase.Tablas.ControlParental
import mx.tec.EHL.DataBase.Tablas.Maestro

@Database(entities=[Maestro::class, Alumno::class,ControlParental::class],version=1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun schoolDao(): SchoolDao
    companion object {
        @Volatile private var instance : AppDataBase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "model.db"
        ).build()
    }

}