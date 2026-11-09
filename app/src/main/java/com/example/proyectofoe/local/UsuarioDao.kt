package com.example.proyectofoe.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertar(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios ORDER BY id ASC")
    suspend fun obtenerTodos(): List<UsuarioEntity>

    @Query("SELECT * FROM usuarios WHERE LOWER(usuario) = LOWER(:usuario) AND contrasena = :contrasena LIMIT 1")
    suspend fun iniciarSesion(usuario: String, contrasena: String): UsuarioEntity?
}
