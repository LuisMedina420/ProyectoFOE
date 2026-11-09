package com.example.proyectofoe.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UsuarioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FoeDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}
