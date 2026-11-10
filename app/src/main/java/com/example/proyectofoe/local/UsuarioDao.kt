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

    @Query(
        "SELECT * FROM usuarios WHERE (LOWER(correo) = LOWER(:correoOUsuario) OR LOWER(usuario) = LOWER(:correoOUsuario)) AND contrasena = :contrasena LIMIT 1"
    )
    suspend fun iniciarSesion(correoOUsuario: String, contrasena: String): UsuarioEntity?

    @Query(
        "UPDATE usuarios SET contrasena = :nuevaContrasena WHERE LOWER(correo) = LOWER(:correoOUsuario) OR LOWER(usuario) = LOWER(:correoOUsuario)"
    )
    suspend fun actualizarContrasena(correoOUsuario: String, nuevaContrasena: String)
}
