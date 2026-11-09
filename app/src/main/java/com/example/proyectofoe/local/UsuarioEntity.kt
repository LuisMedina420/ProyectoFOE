package com.example.proyectofoe.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuario: String,
    val contrasena: String,
    val nombre: String,
    val iniciales: String,
    val rol: String,
    val subtituloRol: String,
    val ruta: String,
    val vehiculo: String,
    val placa: String,
    val empresa: String
)
