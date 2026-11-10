package com.example.proyectofoe.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        UsuarioEntity::class,
        ClienteEntity::class,
        ProductoEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class FoeDatabase : RoomDatabase() {

    abstract fun usuarioCLFK(): UsuarioCLFK

    abstract fun clienteCLFK(): ClienteCLFK

    abstract fun productoCLFK(): ProductoCLFK
}
