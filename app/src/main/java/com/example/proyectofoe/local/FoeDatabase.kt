package com.example.proyectofoe.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        UsuarioEntity::class,
        ClienteEntity::class,
        ProductoEntity::class
    ],
    version = 2
)
abstract class FoeDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun clienteDao(): ClienteDao
    abstract fun productoDao(): ProductoDao
}
